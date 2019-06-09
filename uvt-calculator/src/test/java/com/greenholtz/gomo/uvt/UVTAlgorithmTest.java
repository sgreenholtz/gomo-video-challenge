package com.greenholtz.gomo.uvt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.greenholtz.gomo.uvt.entities.TimeStamp;
import com.greenholtz.gomo.uvt.entities.TimestampType;

public class UVTAlgorithmTest extends AbstractTest{

	@Test
	public void testUniqueViewTimeCalculator_OneIteration() {
		long uvt = UVTAlgorithm.uniqueViewTimeCalculator(getTimeStampListString_OneIteration());
		Assert.assertEquals(95l, uvt);
	}
	
	@Test
	public void testUniqueViewTimeCalculator_TwoIterations() {
		long uvt = UVTAlgorithm.uniqueViewTimeCalculator(getTimeStampListString_TwoIterations());
		Assert.assertEquals(92l, uvt);
	}
	
	@Test
	public void testUniqueViewTimeCalculator_SomeSegmentsNextToEachOther() {
		long uvt = UVTAlgorithm.uniqueViewTimeCalculator(getTimeStampListString_SomeSegmentsNextToEachOther());
		Assert.assertEquals(71l, uvt);
	}
	
	@Test
	public void testUniqueViewTimeCalculator_OnePairedSegment() {
		long uvt = UVTAlgorithm.uniqueViewTimeCalculator(getTimeStampListString_OnePairedSegment());
		Assert.assertEquals(90l, uvt);
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
	public void testGetTimestampsBetweenLastAddedUniqueSegment() {
		List<TimeStamp> allTimeStamps = getTimeStampList_OneIteration();
		List<TimeStamp> uniqueTimeStamps = new ArrayList<>();
		uniqueTimeStamps.add(allTimeStamps.get(7));
		uniqueTimeStamps.add(allTimeStamps.get(2));
		allTimeStamps = UVTAlgorithm.getTimestampsBetweenLastAddedUniqueSegment(allTimeStamps, uniqueTimeStamps);
		Assert.assertEquals(4, allTimeStamps.size());
		Assert.assertEquals(25l, (long)allTimeStamps.get(0).getTimeMilis());
	}

	@Test
	public void testGetUniqueViewSegments() {
		List<TimeStamp> uniqueTimeStamps = UVTAlgorithm.getUniqueViewSegments(getTimeStampList_OneIteration());
		Assert.assertEquals(3, uniqueTimeStamps.size());
		Assert.assertEquals(12l, (long)uniqueTimeStamps.get(2).getTimeMilis());
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
		TimeStamp third = new TimeStamp(0, TimestampType.START, 0l);
		List<TimeStamp> timeList = Arrays.asList(start, end, third);
		UVTAlgorithm.sort(timeList);
		Assert.assertEquals(start, timeList.get(1));
		Assert.assertEquals(end, timeList.get(2));
	}

	@Test
	public void testCalculateUVTFromSegmentList() {
		List<TimeStamp> segments = new ArrayList<TimeStamp>(); 
		segments.add(new TimeStamp(0, TimestampType.END, 100l)); 
		segments.add(new TimeStamp(0, TimestampType.START, 50l));
		segments.add(new TimeStamp(0, TimestampType.START, 25l));
		long viewTime = UVTAlgorithm.calculateUniqueViewTime(segments);
		long expected = 75l;
		Assert.assertEquals(expected, viewTime);
	}
	
	@Test
	public void testCalculateUVTFromSegmentList_SameTimes() {
		List<TimeStamp> segments = new ArrayList<TimeStamp>(); 
		segments.add(new TimeStamp(0, TimestampType.END, 50l)); 
		segments.add(new TimeStamp(0, TimestampType.START, 50l));
		segments.add(new TimeStamp(0, TimestampType.START, 25l));
		long viewTime = UVTAlgorithm.calculateUniqueViewTime(segments);
		long expected = 25l;
		Assert.assertEquals(expected, viewTime);
	}
	
	@Test
	public void testCalculateUVTFromSegmentList_WrongOrder() {
		List<TimeStamp> segments = new ArrayList<TimeStamp>(); 
		segments.add(new TimeStamp(0, TimestampType.START, 25l));
		segments.add(new TimeStamp(0, TimestampType.START, 50l)); 
		segments.add(new TimeStamp(0, TimestampType.END, 100l));
		long viewTime = UVTAlgorithm.calculateUniqueViewTime(segments);
		long expected = 75l;
		Assert.assertNotEquals(expected, viewTime);
	}
	
	@Test
	public void testGetStartTimeClosestToBeginningFromIntermediaryTimes() {
		List<TimeStamp> times = getTimeStampList_OneIteration();
		List<TimeStamp> intermediary = new ArrayList<>();
		intermediary.add(new TimeStamp(1, TimestampType.START, 25l));
		intermediary.add(new TimeStamp(3, TimestampType.END, 30l));
		intermediary.add(new TimeStamp(1, TimestampType.END, 38l));
		intermediary.add(new TimeStamp(0, TimestampType.END, 94l));
		TimeStamp farthest = UVTAlgorithm.getStartTimeClosestToBeginningFromIntermediaryTimes(intermediary, times);
		Assert.assertEquals(12l, (long)farthest.getTimeMilis());
	}
	
	@Test
	public void testTrimReviewedValuesFromTimeStampList_LastValue() {
		List<TimeStamp> times = getTimeStampList_OneIteration();
		List<TimeStamp> uniqueSegments = Arrays.asList(times.get(0));
		times = UVTAlgorithm.trimReviewedValuesFromTimeStampList(times, uniqueSegments);
		Assert.assertEquals(0, times.size());
	}
	
	@Test
	public void testTrimReviewedValuesFromTimeStampList_IntermediateValue() {
		List<TimeStamp> times = getTimeStampList_OneIteration();
		List<TimeStamp> uniqueSegments = Arrays.asList(times.get(5), times.get(3));
		TimeStamp timeToRemove = times.get(times.size()-1);
		times = UVTAlgorithm.trimReviewedValuesFromTimeStampList(times, uniqueSegments);
		Assert.assertEquals(6, times.size());
		Assert.assertFalse(times.contains(timeToRemove));
	}
	

}
