package com.greenholtz.gomo.uvt;
import java.util.*;
public class UVTAlgorithm {

	public static void main(String[] args) {
		List<TimeStamp> times = new ArrayList<>();
		int index = 0;
		for (int i=0; i<args.length; i++) {
			Long time = Long.parseLong(args[i]);
			if (i % 2 == 0) {
				times.add(new TimeStamp(index, TimestampType.START, time));
			} else {
				times.add(new TimeStamp(index, TimestampType.END, time));
				index++;
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
		// Step 1 - Sort
		sort(times);
		System.out.println(times);
		
		// Step 2 - Look at the last end time. Find its matching start time and its position
		int finalIndex = times.get(times.size()-1).getIndex();
		TimeStamp startFinalTime = times.stream().filter(t->t.getIndex()==finalIndex).filter(t->t.getType()==TimestampType.START).findFirst().orElse(null);
		System.out.println(startFinalTime);
		
		// Step 3 - Figure out what times are in between the start and end of part 2
		int position = times.indexOf(startFinalTime);
		List<TimeStamp> intermediaryTimestamps = times.subList(position+1, times.size()-1);
		System.out.println(intermediaryTimestamps);
		
		//Step 4 - Which in between is the farthest away?
		TimeStamp farthestAway = intermediaryTimestamps.stream().min(Comparator.comparing(TimeStamp::getIndex)).get();
		System.out.println(farthestAway);
		
		//Step 5 - Have we reached the beginning of the list?\
		if (times.indexOf(farthestAway) == 0) {
			System.out.println("calculate");
		} else {
			System.out.println("continue");
		}
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