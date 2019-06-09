package com.greenholtz.gomo.uvt.calculator;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import com.greenholtz.gomo.uvt.AbstractTest;
import com.greenholtz.gomo.uvt.entities.TimeStamp;

public class TimeStampCalculatorTest extends AbstractTest {

	private TimeStampCalculator calculator = new TimeStampCalculator();
	
	@Test
	public void testGetRunningTotal() {
		fail("Not yet implemented");
	}

	@Test
	public void testCalculateTotalUniqueViewTime() {
		fail("Not yet implemented");
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
