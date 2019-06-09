package com.greenholtz.gomo.uvt.calculator;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.greenholtz.gomo.uvt.AbstractTest;
import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

public class TimeStampCalculatorTest extends AbstractTest {

	private TimeStampCalculator calculator = new TimeStampCalculator();

	@Test
	public void testCalculateTotalUniqueViewTime() {
		List<TimeStamp> uniqueSegments = new ArrayList<>();
		uniqueSegments.add(new TimeStamp(0, TimestampType.END, 70l));
		uniqueSegments.add(new TimeStamp(0, TimestampType.START, 60l));
		uniqueSegments.add(new TimeStamp(0, TimestampType.START, 50l));
		uniqueSegments.add(new TimeStamp(0, TimestampType.START, 40l));
		uniqueSegments.add(new TimeStamp(0, TimestampType.START, 30l));
		uniqueSegments.add(new TimeStamp(0, TimestampType.END, 25l));
		uniqueSegments.add(new TimeStamp(0, TimestampType.START, 15l));
		long total = calculator.calculateTotalUniqueViewTime(uniqueSegments);
		assertEquals(50l, total);
	}

	@Test
	public void testAddBothDifferences() {
		long total = calculator.addBothDifferences(getTimeStampArray());
		assertEquals(4l, total);
	}

	@Test
	public void testAddFirstDifference() {
		long total = calculator.addFirstDifference(getTimeStampArray());
		assertEquals(3l, total);
	}

	@Test
	public void testAddSecondDifference() {
		long total = calculator.addSecondDifference(getTimeStampArray());
		assertEquals(1l, total);
	}
	
	private TimeStamp[] getTimeStampArray() {
		TimeStamp[] times = new TimeStamp[3];
		Arrays.asList(new TimeStamp(0, null, 5l), 
				new TimeStamp(0, null, 2l),
				new TimeStamp(0, null, 1l)).toArray(times);
		
		return times;
	}

}
