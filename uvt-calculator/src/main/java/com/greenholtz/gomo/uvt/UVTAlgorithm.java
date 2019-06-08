package com.greenholtz.gomo.uvt;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class UVTAlgorithm {
	
	private static Logger logger = LoggerFactory.getLogger(UVTAlgorithm.class);
	
	public static void uniqueViewTimeCalculator(String times) {
		logger.info("Starting to calculate the Unique View Time (UVT)");
		logger.info("First, we parse out the view time segements inputted into the application.");
		List<TimeStamp> timeStamps = parseTimeSegments(times);
		
		logger.info("Next, we sort the time segments according to the timestamp in milliseconds.");
		sort(timeStamps);
		logger.debug(timeStamps.toString());
		
//		List<TimeStamp> uniqueSegments = getUniqueViewSegments(times);
//		System.out.println(calculateUniqueViewTime(uniqueSegments));
	}
	
	public static List<TimeStamp> parseTimeSegments(String times) {
		return parseTimeSegments(times.split(" "));
	}

	public static List<TimeStamp> parseTimeSegments(String[] timeArr) {
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
	
	public static List<TimeStamp> getUniqueViewSegments(List<TimeStamp> times) {
		List<TimeStamp> uniqueSegments = new ArrayList<>();
		
		// Step 2 - Look at the last end time. Find its matching start time and its position
		int finalTimeId = times.get(times.size()-1).getId();
		TimeStamp startFinalTime = times.stream().filter(t->t.getId()==finalTimeId).filter(t->t.getType()==TimestampType.START).findFirst().orElse(null);
		uniqueSegments.add(times.get(times.size()-1));
		uniqueSegments.add(startFinalTime);
		
		// Step 3 - Figure out what times are in between the start and end of part 2
		int startFinalTimePosition = times.indexOf(startFinalTime);
		List<TimeStamp> intermediaryTimestamps = times.subList(startFinalTimePosition+1, times.size()-1);
		
		//Step 4 - Which end has a start that is farthest away?
		List<TimeStamp> ends = intermediaryTimestamps.stream()
				.filter(t->t.getType()==TimestampType.END)
				.collect(Collectors.toList());
		
		TimeStamp uniqueEndFarthest = ends.stream()
				.filter(t->intermediaryTimestamps.stream()
						.noneMatch(s->s.getId()==t.getId()&&s.getType()==TimestampType.START))
				.findFirst().get();
		
		// Trim list of dups
		times = times.subList(0, times.indexOf(startFinalTime));
		System.out.println(times);
		
		// Get the start matching that end
		TimeStamp farthestTime = times.stream()
				.filter(t->t.getType()==TimestampType.START)
				.filter(t->t.getId()==uniqueEndFarthest.getId())
				.findFirst()
				.get();
		uniqueSegments.add(farthestTime);
		
		// Remove from original list
		times.remove(farthestTime);
		
		// Step 5 - Are there more left in the original list?
		if (times.size()==0) {
			System.out.println("end");
			System.out.println("Unique Segments: " + uniqueSegments);
		} else {
			System.out.println("continue");
			// maybe some recursion here?
		}
		
		return uniqueSegments;
	}
	
	/**
	 * Sorts the list of timestamp times according to milliseconds
	 * @param times
	 * @return a List of TimeStamp times sorted by milliseconds 
	 */
	public static List<TimeStamp> sort(List<TimeStamp> times) {
		Collections.sort(times);
		return times;
	}
	
	/**
	 * For display purposes only, shows the values as they have been parsed into a list
	 * @param times
	 */
	public static void parseCheck(List<TimeStamp> times) {
		StringBuilder sb = new StringBuilder();
		times.forEach(t->sb.append(t).append(" "));
		System.out.println(sb);
	}
	
	
	public static long calculateUniqueViewTime(List<TimeStamp> uniqueSegments) {
		return calculateUVTFromSegmentList(uniqueSegments, 0l);
	}
	
	private static long calculateUVTFromSegmentList(List<TimeStamp> uniqueSegments, long runningTotal) {
		runningTotal += uniqueSegments.get(0).getTimeMilis() - uniqueSegments.get(1).getTimeMilis();
		uniqueSegments.remove(uniqueSegments.get(0));
		if (uniqueSegments.size()>1) {
			runningTotal = calculateUVTFromSegmentList(uniqueSegments, runningTotal);
		}
		return runningTotal;
	}

}