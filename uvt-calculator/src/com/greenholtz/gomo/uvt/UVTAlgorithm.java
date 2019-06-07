package com.greenholtz.gomo.uvt;
import java.util.*;
import java.util.stream.Collectors;
public class UVTAlgorithm {

	public static void main(String[] args) {
		List<TimeStamp> times = new ArrayList<>();
		int id = 0;
		for (int i=0; i<args.length; i++) {
			Long time = Long.parseLong(args[i]);
			if (i % 2 == 0) {
				times.add(new TimeStamp(id, TimestampType.START, time));
			} else {
				times.add(new TimeStamp(id, TimestampType.END, time));
				id++;
			}
		}
		algorithm(times);
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
	
	public static void algorithm(List<TimeStamp> times) {
		List<TimeStamp> uniqueSegments = new ArrayList<>();
		
		// Step 1 - Sort
		sort(times);
		System.out.println(times);
		
		// Step 2 - Look at the last end time. Find its matching start time and its position
		int finalTimeId = times.get(times.size()-1).getId();
		TimeStamp startFinalTime = times.stream().filter(t->t.getId()==finalTimeId).filter(t->t.getType()==TimestampType.START).findFirst().orElse(null);
		uniqueSegments.add(times.get(times.size()-1));
		uniqueSegments.add(startFinalTime);
		
		// Step 3 - Figure out what times are in between the start and end of part 2
		int startFinalTimePosition = times.indexOf(startFinalTime);
		List<TimeStamp> intermediaryTimestamps = times.subList(startFinalTimePosition+1, times.size()-1);
		System.out.println(intermediaryTimestamps);
		
		//Step 4 - Which end has a start that is farthest away?
		List<TimeStamp> ends = intermediaryTimestamps.stream()
				.filter(t->t.getType()==TimestampType.END)
				.collect(Collectors.toList());
		
		TimeStamp uniqueEndFarthest = ends.stream()
				.filter(t->intermediaryTimestamps.stream()
						.noneMatch(s->s.getId()==t.getId()&&s.getType()==TimestampType.START))
				.findFirst().get();
		uniqueSegments.add(uniqueEndFarthest);
		
		
		
//		List<TimeStamp> starts = intermediaryTimestamps.stream().filter(t->t.getType()==TimestampType.START).collect(Collectors.toList());
//		List<TimeStamp> ends = intermediaryTimestamps.stream().filter(t->t.getType()==TimestampType.END).collect(Collectors.toList());

//		System.out.println("Starts Before: " + starts);
//		System.out.println("Ends: " + ends);
//		List<TimeStamp> unmatchedStarts = new ArrayList<>();
//		for (TimeStamp start : starts) {
//			if (ends.stream().noneMatch(e->e.getIndex()==start.getIndex())) {
//				unmatchedStarts.add(start);
//			}
//		}
//		System.out.println("After: " + unmatchedStarts);
		
//		TimeStamp farthestAway = intermediaryTimestamps.stream().min(Comparator.comparing(TimeStamp::getIndex)).get();
//		System.out.println(farthestAway);
//		
//		//Step 5 - Have we reached the beginning of the list?
//		if (times.indexOf(farthestAway) == 0) {
//			System.out.println("calculate");
//		} else {
//			System.out.println("continue");
//		}
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

}