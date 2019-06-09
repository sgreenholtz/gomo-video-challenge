package com.greenholtz.gomo.uvt;

import java.util.List;
import java.util.stream.Stream;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

/**
 * Utilities for working with TimeStamp objects
 * @author smgre
 */
public class TimeStampUtils {
	
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

}
