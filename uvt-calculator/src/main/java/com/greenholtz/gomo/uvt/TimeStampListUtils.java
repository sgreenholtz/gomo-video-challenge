package com.greenholtz.gomo.uvt;

import java.util.List;
import java.util.stream.Stream;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

/**
 * Utilities for working with List<TimeStamp> objects
 * @author smgre
 */
public class TimeStampListUtils {
	
	public static TimeStamp getStartFromEnd(List<TimeStamp> times, int id) {
		return filterStreamById(times,id).filter(t->t.getType()==TimestampType.START).findFirst().get();
	}
	
	public static TimeStamp getEndFromStart(List<TimeStamp> times, int id) {
		return filterStreamById(times,id).filter(t->t.getType()==TimestampType.END).findFirst().get();
	}
	
	private static Stream<TimeStamp> filterStreamById(List<TimeStamp> times, int id) {
		return times.stream().filter(t->t.getId()==id);
	}
	
	public static List<TimeStamp> trimLastTwoTimestampsFromList(List<TimeStamp> allTimeStamps) {
		return allTimeStamps.subList(0, allTimeStamps.size()-2);
	}
	
	/**
	 * Wrapper for basic syntax because I hate the way this looks
	 * @param timeStamps any list of TimeStamp objects
	 * @return final timestamp in the list
	 */
	public static TimeStamp getLast(List<TimeStamp> timeStamps) {
		return timeStamps.get(timeStamps.size()-1);
	}
	
	/**
	 * Equivalent to (in other languages) using negative index. 0 is the last
	 * value, 1 is the second-to-last value, etc
	 * @param timeStamps List of TimeStamp objects
	 * @param index Int value from the end of the list to retrieve items
	 * @return TimeStamp object from the list
	 */
	public static TimeStamp getFromBack(List<TimeStamp> timeStamps, int index) {
		return timeStamps.get(timeStamps.size()-index-1);
	}

}
