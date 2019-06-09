package com.greenholtz.gomo.uvt;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;
public class UVTAlgorithm {
	
	private static Logger logger = LoggerFactory.getLogger(UVTAlgorithm.class);
	
	public static long uniqueViewTimeCalculator(String allViewTimestamps) {
		logger.info("Starting to calculate the Unique View Time (UVT)");
		logger.info("First, we parse out the view time segements inputted into the application.");
		List<TimeStamp> timeStamps = parseTimeSegments(allViewTimestamps);
		logger.info("Parsed!");
		
		logger.info("Next, we sort the time segments according to the timestamp in milliseconds.");
		sort(timeStamps);
		logger.debug(timeStamps.toString());
		logger.info("Sorted!");
		
		logger.info("Next, we figure out which time segments are overlapping and which are unique.");
		List<TimeStamp> uniqueSegments = getUniqueViewSegments(timeStamps);
		logger.info("These are the unique segments of view time: " + uniqueSegments);
		
		logger.info("Finally, we find the view time between each unique segment and add it up.");
		long uniqueViewTime = calculateUniqueViewTime(uniqueSegments);
		logger.info("Here is the calculated UVT: " + uniqueViewTime);
		return uniqueViewTime;
	}
	
	/**
	 * Creates a list of TimeStamp objects from a string of allViewTimestamps, delimited by
	 * a space
	 * @param allViewTimestamps String
	 * @return A list of TimeStamp objects
	 */
	static List<TimeStamp> parseTimeSegments(String allViewTimestamps) {
		return parseTimeSegments(allViewTimestamps.split(" "));
	}

	/**
	 * Creates a list of TimeStamp objects from an array of allViewTimestamps
	 * @param timeArr array of strings containing allViewTimestamps
	 * @return A list of TimeStamp objects
	 */
	static List<TimeStamp> parseTimeSegments(String[] timeArr) {
		List<TimeStamp> allViewTimestamps = new ArrayList<>();
		int id = 0;
		for (int i=0; i<timeArr.length; i++) {
			Long time = Long.parseLong(timeArr[i]);
			if (i % 2 == 0) {
				allViewTimestamps.add(new TimeStamp(id, TimestampType.START, time));
			} else {
				allViewTimestamps.add(new TimeStamp(id, TimestampType.END, time));
				id++;
			}
		}
		return allViewTimestamps;
	}
	
	static List<TimeStamp> getFinalUniqueSegment(List<TimeStamp> allViewTimestamps) {
		List<TimeStamp> segments = new ArrayList<>();
		int finalTimeId = allViewTimestamps.get(allViewTimestamps.size()-1).getId();
		TimeStamp startFinalTime = TimeStampListUtils.getStartFromEnd(allViewTimestamps, finalTimeId);
		segments.add(TimeStampListUtils.getLast(allViewTimestamps));
		segments.add(startFinalTime);
		return segments;
	}
	
	static List<TimeStamp> getTimestampsBetweenLastAddedUniqueSegment(List<TimeStamp> allViewTimestamps, List<TimeStamp> segments) {
		int startIndex = allViewTimestamps.indexOf(TimeStampListUtils.getLast(segments))+1;
		int endIndex = allViewTimestamps.indexOf(TimeStampListUtils.getFromBack(segments, 1));
		return allViewTimestamps.subList(startIndex, endIndex);
	}
	
	static TimeStamp getStartTimeClosestToBeginningFromIntermediaryTimes(List<TimeStamp> intermediaryTimestamps,
			List<TimeStamp> allViewTimestamps) {
		
		List<TimeStamp> endingTimeStampsWithStartsNotInIntermediaryList = intermediaryTimestamps.stream()
				.filter(t->t.getType()==TimestampType.END)
				.filter(t->intermediaryTimestamps.stream()
						.noneMatch(s->s.getId()==t.getId()&&s.getType()==TimestampType.START))
				.collect(Collectors.toList());
		
		return allViewTimestamps.stream()
				.filter(t->t.getType()==TimestampType.START)
				.filter(t->endingTimeStampsWithStartsNotInIntermediaryList.stream()
						.anyMatch(end->end.getId()==t.getId()))
				.findFirst()
				.get();
	}
	
	static List<TimeStamp> getUniqueViewSegments(List<TimeStamp> allViewTimestamps) {
		List<TimeStamp> uniqueSegments = new ArrayList<>();
		uniqueSegments.addAll(getFinalUniqueSegment(allViewTimestamps));
		
		while (allViewTimestamps.size()>1) {
			List<TimeStamp> intermediaryTimestamps = getTimestampsBetweenLastAddedUniqueSegment(allViewTimestamps, uniqueSegments);
			if (intermediaryTimestamps.size()==0) {
				allViewTimestamps = TimeStampListUtils.trimLastTwoTimestampsFromList(allViewTimestamps);
				uniqueSegments.addAll(getFinalUniqueSegment(allViewTimestamps));
			} else {
				TimeStamp farthestTime = getStartTimeClosestToBeginningFromIntermediaryTimes(intermediaryTimestamps, allViewTimestamps);
				uniqueSegments.add(farthestTime);
				allViewTimestamps = trimReviewedValuesFromTimeStampList(allViewTimestamps, uniqueSegments);
			}
		}
		return uniqueSegments;
	}
	
	/**
	 * Trims the values from the list of all view times that have been examined
	 * and reviewed by the algorithm
	 * @param allViewTimestamps
	 * @param uniqueSegments
	 * @return trimmed time view list
	 */
	static List<TimeStamp> trimReviewedValuesFromTimeStampList(List<TimeStamp> allViewTimestamps, List<TimeStamp> uniqueSegments) {
		if (uniqueSegments.get(uniqueSegments.size()-1).equals(allViewTimestamps.get(0))) {
			allViewTimestamps.clear();
			return allViewTimestamps;
		}
		return allViewTimestamps.subList(0, allViewTimestamps.indexOf(uniqueSegments.get(uniqueSegments.size()-2))+1);
	}
	
	/**
	 * Sorts the list of timestamp allViewTimestamps according to milliseconds
	 * @param allViewTimestamps
	 * @return a List of TimeStamp allViewTimestamps sorted by milliseconds 
	 */
	static List<TimeStamp> sort(List<TimeStamp> allViewTimestamps) {
		Collections.sort(allViewTimestamps);
		return allViewTimestamps;
	}
	
	
	static long calculateUniqueViewTime(List<TimeStamp> uniqueSegments) {
		return calculateUVTFromSegmentList(uniqueSegments, 0l);
	}
	
	/**
	 * Uses recursion to calculate the total unique view time by finding the
	 * difference between the different start and end allViewTimestamps. Sorted with last timestamp
	 * first.
	 * @param uniqueSegments List of TimeStamp object representing start and end
	 * times of unique views
	 * @param runningTotal 
	 * @return Long of total unique view time
	 */
	static long calculateUVTFromSegmentList(List<TimeStamp> uniqueSegments, long runningTotal) {
		runningTotal += uniqueSegments.get(0).getTimeMilis() - uniqueSegments.get(1).getTimeMilis();
		uniqueSegments.remove(uniqueSegments.get(0));
		if (uniqueSegments.size()>1) {
			runningTotal = calculateUVTFromSegmentList(uniqueSegments, runningTotal);
		}
		return runningTotal;
	}

}