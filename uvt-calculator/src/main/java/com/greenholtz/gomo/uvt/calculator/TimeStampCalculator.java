package com.greenholtz.gomo.uvt.calculator;

import java.util.List;

import com.greenholtz.gomo.uvt.entities.TimeStamp;

/**
 * Contains methods to separate different cases of 
 * time stamp types for calculation of unique view time
 * @author smgre
 */
public class TimeStampCalculator {
	
	private long runningTotal = 0;
	
	public long getRunningTotal() {
		return runningTotal;
	}
	
	private long differenceBetweenFirstTwoValues(List<TimeStamp> uniqueTimeStamps) {
		return uniqueTimeStamps.get(0).getTimeMilis() - uniqueTimeStamps.get(1).getTimeMilis();
	}
	
//	public 

	/**
	 * 1E 2S 3S
	 * (1E - 2S) + (2S - 3S)
	 * end-start
	 * trim end
	 * start-start
	 * @param uniqueTimeStamps
	 * @return modified List
	 */
	public long endStartStart(TimeStamp...timeStamps) {
		return (timeStamps[0].getTimeMilis()-timeStamps[1].getTimeMilis()) 
				+ (timeStamps[1].getTimeMilis()-timeStamps[2].getTimeMilis());
	}
	
	/**
	 * 1E 2S 3E
	 * (1E - 2S)
	 * end-start
	 * trim end
	 * trim start
	 * @param uniqueTimeStamps
	 * @return
	 */
	public long endStartEnd(TimeStamp...timeStamps) {
		return timeStamps[0].getTimeMilis() - timeStamps[1].getTimeMilis();
	}
	
	
	/**
	 * 1S 2S 3S
	 * (1S - 2S) + (2S - 3S)
	 * start1-start2
	 * trim start1
	 * start2-start3
	 * trim start2
	 * @param uniqueTimeStamps
	 * @return
	 */
	public long startStartStart(TimeStamp...timeStamps) {
		return endStartStart(timeStamps);
	}
	
	/**
	 * 1S 2E 3S
	 * (2E - 3S)
	 * trim start
	 * end-start
	 * @param uniqueTimeStamps
	 * @return
	 */
	public long startEndStart(TimeStamp...timeStamps) {
		return endStartEnd(timeStamps);
	}
}
