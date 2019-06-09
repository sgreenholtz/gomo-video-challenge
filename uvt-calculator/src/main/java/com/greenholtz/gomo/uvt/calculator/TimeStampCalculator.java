package com.greenholtz.gomo.uvt.calculator;

import java.util.List;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

/**
 * Contains methods to separate different cases of 
 * time stamp types for calculation of unique view time
 * @author smgre
 */
public class TimeStampCalculator {
		
	public long calculateTotalUniqueViewTime(List<TimeStamp> uniqueSegments) {
		long runningTotal = 0;
		while (uniqueSegments.size()>2) {
			runningTotal += calculateTotalFromThreeTimeStampSegment(uniqueSegments);
			uniqueSegments.remove(0);
			uniqueSegments.remove(0);
		}
		return runningTotal;
	}
	
	private long calculateTotalFromThreeTimeStampSegment(List<TimeStamp> uniqueSegments) {
		if (uniqueSegments.get(1).getType()==TimestampType.START
				&& uniqueSegments.get(2).getType()==TimestampType.START) {
			return addBothDifferences(getArrayFirstThreeValues(uniqueSegments));
		} else if (uniqueSegments.get(0).getType()==TimestampType.START) {
			return addSecondDifference(getArrayFirstThreeValues(uniqueSegments));
		} else {
			return addFirstDifference(getArrayFirstThreeValues(uniqueSegments));
		}
	}

	private TimeStamp[] getArrayFirstThreeValues(List<TimeStamp> uniqueSegments) {
		TimeStamp[] timeStampArray = new TimeStamp[3];
		uniqueSegments.subList(0, 3).toArray(timeStampArray);
		return timeStampArray;
	}
	
	/**
	 * 1E 2S 3S
	 * (1E - 2S) + (2S - 3S)
	 * 
	 * or
	 * 
	 * 1S 2S 3S
	 * (1S - 2S) + (2S - 3S)
	 * @param uniqueTimeStamps
	 * @return modified List
	 */
	public long addBothDifferences(TimeStamp...timeStamps) {
		return (timeStamps[0].getTimeMilis()-timeStamps[2].getTimeMilis());
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
	public long addFirstDifference(TimeStamp...timeStamps) {
		return timeStamps[0].getTimeMilis() - timeStamps[1].getTimeMilis();
	}
	
	/**
	 * 1S 2E 3S
	 * (2E - 3S)
	 * trim start
	 * end-start
	 * @param uniqueTimeStamps
	 * @return
	 */
	public long addSecondDifference(TimeStamp...timeStamps) {
		return timeStamps[1].getTimeMilis() - timeStamps[2].getTimeMilis();
	}
}
