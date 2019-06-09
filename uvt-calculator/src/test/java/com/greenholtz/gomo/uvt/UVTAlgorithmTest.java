package com.greenholtz.gomo.uvt;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

public class UVTAlgorithmTest {

	@Test
	public void testUniqueViewTimeCalculator() {
		fail("Not yet implemented");
	}

	@Test
	public void testParseTimeSegmentsString() {
		String timeSegments = "12 94 14 38 99 107 15 30";
		List<TimeStamp> actual = UVTAlgorithm.parseTimeSegments(timeSegments);
		Assert.assertEquals((long)94l, (long)actual.get(1).getTimeMilis());
		Assert.assertEquals(TimestampType.START, actual.get(4).getType());
		Assert.assertEquals(1, actual.get(3).getId());
	}

	@Test
	public void testParseTimeSegmentsStringArray() {
		String[] times = {"12","94","14","38","99","107","15","30"};
		List<TimeStamp> actual = UVTAlgorithm.parseTimeSegments(times);
		Assert.assertEquals((long)94l, (long)actual.get(1).getTimeMilis());
		Assert.assertEquals(TimestampType.START, actual.get(4).getType());
		Assert.assertEquals(1, actual.get(3).getId());
	}

	@Test
	public void testGetUniqueViewSegments() {
		fail("Not yet implemented");
	}

	@Test
	public void testSort() {
		TimeStamp first = new TimeStamp(0, null, 0l);
		TimeStamp second = new TimeStamp(0, null, 1l);
		List<TimeStamp> timeList = Arrays.asList(second, first);
		UVTAlgorithm.sort(timeList);
		Assert.assertEquals(first, timeList.get(0));
	}
	
	@Test
	public void testSort_SameTimes() {
		TimeStamp start = new TimeStamp(0, TimestampType.START, 1l);
		TimeStamp end = new TimeStamp(0, TimestampType.END, 1l);
		TimeStamp third = new TimeStamp(0, null, 0l);
		List<TimeStamp> timeList = Arrays.asList(start, end, third);
		UVTAlgorithm.sort(timeList);
		Assert.assertEquals(start, timeList.get(1));
		Assert.assertEquals(end, timeList.get(2));
	}

	@Test
	public void testCalculateUVTFromSegmentList() {
		List<TimeStamp> segments = new ArrayList<TimeStamp>(); 
		segments.add(new TimeStamp(0, null, 100l)); 
		segments.add(new TimeStamp(0, null, 50l));
		segments.add(new TimeStamp(0, null, 25l));
		long viewTime = UVTAlgorithm.calculateUVTFromSegmentList(segments, 0l);
		long expected = 75l;
		Assert.assertEquals(expected, viewTime);
	}
	
	@Test
	public void testCalculateUVTFromSegmentList_SameTimes() {
		List<TimeStamp> segments = new ArrayList<TimeStamp>(); 
		segments.add(new TimeStamp(0, null, 50l)); 
		segments.add(new TimeStamp(0, null, 50l));
		segments.add(new TimeStamp(0, null, 25l));
		long viewTime = UVTAlgorithm.calculateUVTFromSegmentList(segments, 0l);
		long expected = 25l;
		Assert.assertEquals(expected, viewTime);
	}
	
	@Test
	public void testCalculateUVTFromSegmentList_WrongOrder() {
		List<TimeStamp> segments = new ArrayList<TimeStamp>(); 
		segments.add(new TimeStamp(0, null, 25l));
		segments.add(new TimeStamp(0, null, 50l)); 
		segments.add(new TimeStamp(0, null, 100l));
		long viewTime = UVTAlgorithm.calculateUVTFromSegmentList(segments, 0l);
		long expected = 75l;
		Assert.assertNotEquals(expected, viewTime);
	}
	
	@Test
	public void testGetStartTimeClosestToBeginningFromIntermediaryTimes() {
		List<TimeStamp> times = getTimeStampList();
		List<TimeStamp> intermediary = new ArrayList<>();
		intermediary.add(new TimeStamp(3, TimestampType.END, 30l));
		intermediary.add(new TimeStamp(1, TimestampType.END, 38l));
		intermediary.add(new TimeStamp(0, TimestampType.END, 94l));
		UVTAlgorithm.getStartTimeClosestToBeginningFromIntermediaryTimes(intermediary, times);
	}
	
	private ArrayList<TimeStamp> getTimeStampList() {
		ArrayList<TimeStamp> times = new ArrayList<>();
		times.add(new TimeStamp(0, TimestampType.START, 12l));
		times.add(new TimeStamp(1, TimestampType.START, 14l));
		times.add(new TimeStamp(3, TimestampType.START, 15l));
		times.add(new TimeStamp(2, TimestampType.START, 20l));
		times.add(new TimeStamp(3, TimestampType.END, 30l));
		times.add(new TimeStamp(1, TimestampType.END, 38l));
		times.add(new TimeStamp(0, TimestampType.END, 94l));
		times.add(new TimeStamp(2, TimestampType.END, 107l));
		return times;
	}

}
