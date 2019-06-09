package com.greenholtz.gomo.uvt;

import java.util.ArrayList;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

public abstract class AbstractTest {

	protected ArrayList<TimeStamp> getTimeStampList_OneIteration() {
		ArrayList<TimeStamp> times = new ArrayList<>();
		times.add(new TimeStamp(0, TimestampType.START, 12l));
		times.add(new TimeStamp(3, TimestampType.START, 15l));
		times.add(new TimeStamp(2, TimestampType.START, 20l));
		times.add(new TimeStamp(1, TimestampType.START, 25l));
		times.add(new TimeStamp(3, TimestampType.END, 30l));
		times.add(new TimeStamp(1, TimestampType.END, 38l));
		times.add(new TimeStamp(0, TimestampType.END, 94l));
		times.add(new TimeStamp(2, TimestampType.END, 107l));
		return times;
	}
	
	protected String getTimeStampListString_OneIteration() {
		return "12 94 25 38 20 107 15 30";
	}
	
	protected String getTimeStampListString_TwoIterations() {
		return "31 94 25 38 80 107 15 30";
	}
	
	protected ArrayList<TimeStamp> getTimeStampList_TwoIterations() {
		ArrayList<TimeStamp> times = new ArrayList<>();
		
		times.add(new TimeStamp(3, TimestampType.START, 15l));
		times.add(new TimeStamp(1, TimestampType.START, 25l));
		times.add(new TimeStamp(3, TimestampType.END, 30l));
		times.add(new TimeStamp(0, TimestampType.START, 31l));
		times.add(new TimeStamp(1, TimestampType.END, 38l));
		times.add(new TimeStamp(2, TimestampType.START, 80l));
		times.add(new TimeStamp(0, TimestampType.END, 94l));
		times.add(new TimeStamp(2, TimestampType.END, 107l));
		return times;
	}
}
