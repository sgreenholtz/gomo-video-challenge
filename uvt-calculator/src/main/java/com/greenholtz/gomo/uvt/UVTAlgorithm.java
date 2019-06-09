package com.greenholtz.gomo.uvt;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;
public class UVTAlgorithm {
	
	private static Logger logger = LoggerFactory.getLogger(UVTAlgorithm.class);
	
	public static void uniqueViewTimeCalculator(String times) {
		logger.info("Starting to calculate the Unique View Time (UVT)");
		logger.info("First, we parse out the view time segements inputted into the application.");
		List<TimeStamp> timeStamps = parseTimeSegments(times);
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
	}
	
	/**
	 * Creates a list of TimeStamp objects from a string of times, delimited by
	 * a space
	 * @param times String
	 * @return A list of TimeStamp objects
	 */
	static List<TimeStamp> parseTimeSegments(String times) {
		return parseTimeSegments(times.split(" "));
	}

	/**
	 * Creates a list of TimeStamp objects from an array of times
	 * @param timeArr array of strings containing times
	 * @return A list of TimeStamp objects
	 */
	static List<TimeStamp> parseTimeSegments(String[] timeArr) {
		List<TimeStamp> times = new ArrayList<>();
		int id = 0;
		for (int i=0; i<timeArr.length; i++) {
			Long time = Long.parseLong(timeArr[i]);
			if (i % 2 == 0) {
				times.add(new TimeStamp(id, TimestampType.START, time));
			} else {
				times.add(new TimeStamp(id, TimestampType.END, time));
				id++;
			}
		}
		return times;
	}
	
	static List<TimeStamp> getFinalUniqueSegment(List<TimeStamp> times) {
		List<TimeStamp> segments = new ArrayList<>();
		int finalTimeId = times.get(times.size()-1).getId();
		TimeStamp startFinalTime = TimeStampUtils.getStartFromEnd(times, finalTimeId);
		segments.add(times.get(times.size()-1));
		segments.add(startFinalTime);
		return segments;
	}
	
	static List<TimeStamp> getTimestampsBetweenLastAddedUniqueSegment(List<TimeStamp> times, List<TimeStamp> segments) {
		int startIndex = times.indexOf(segments.get(segments.size()-2));
		int endIndex = times.indexOf(segments.get(segments.size()-1));
		return times.subList(startIndex, endIndex);
	}
	
	static TimeStamp getStartTimeClosestToBeginningFromIntermediaryTimes(List<TimeStamp> intermediaryTimestamps,
			List<TimeStamp> times) {
		
		List<TimeStamp> endingTimeStampsWithStartsNotInIntermediaryList = intermediaryTimestamps.stream()
				.filter(t->t.getType()==TimestampType.END)
				.filter(t->intermediaryTimestamps.stream()
						.noneMatch(s->s.getId()==t.getId()&&s.getType()==TimestampType.START))
				.collect(Collectors.toList());
		
		return times.stream()
				.filter(t->t.getType()==TimestampType.START)
				.map(t->endingTimeStampsWithStartsNotInIntermediaryList.stream()
						.filter(end->end.getId()==t.getId())
						.findFirst()
						.get())
				.findFirst()
				.get();
	}
	
	static List<TimeStamp> getUniqueViewSegments(List<TimeStamp> times) {
		List<TimeStamp> uniqueSegments = new ArrayList<>();
		uniqueSegments.addAll(getFinalUniqueSegment(times));
		List<TimeStamp> intermediaryTimestamps = getTimestampsBetweenLastAddedUniqueSegment(times, uniqueSegments);
		TimeStamp farthestTime = getStartTimeClosestToBeginningFromIntermediaryTimes(intermediaryTimestamps, times);
		uniqueSegments.add(farthestTime);
		times = times.subList(0, times.indexOf(farthestTime));
		
		// Step 5 - Are there more left in the original list?
		if (times.size()==0) {
			System.out.println("end");
		} else {
			System.out.println("continue");
			// maybe some recursion here?
		}
		
		// Put the times back in time order
		Collections.reverse(uniqueSegments);
		
		return uniqueSegments;
	}
	
	/**
	 * Sorts the list of timestamp times according to milliseconds
	 * @param times
	 * @return a List of TimeStamp times sorted by milliseconds 
	 */
	static List<TimeStamp> sort(List<TimeStamp> times) {
		Collections.sort(times);
		return times;
	}
	
	
	static long calculateUniqueViewTime(List<TimeStamp> uniqueSegments) {
		return calculateUVTFromSegmentList(uniqueSegments, 0l);
	}
	
	/**
	 * Uses recursion to calculate the total unique view time by finding the
	 * difference between the different start and end times. Sorted with last timestamp
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