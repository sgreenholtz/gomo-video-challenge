package com.greenholtz.gomo.uvt;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.greenholtz.gomo.uvt.entities.TimeStamp;


public class TimeStampListUtilsTest extends AbstractTest{

	@Test
	public void testGetStartFromEnd() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp start = TimeStampListUtils.getStartFromEnd(timeStampList, 2);
		Assert.assertEquals(timeStampList.get(2), start);
	}

	@Test
	public void testGetEndFromStart() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp end = TimeStampListUtils.getEndFromStart(timeStampList, 3);
		Assert.assertEquals(timeStampList.get(4), end);
	}
	
	@Test
	public void testTrimLastTwoTimestampsFromList() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		List<TimeStamp> trimmedList = TimeStampListUtils.trimLastTwoTimestampsFromList(timeStampList);
		Assert.assertEquals(timeStampList.size()-2, trimmedList.size());
	}
	
	@Test
	public void testGetLast() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp last = TimeStampListUtils.getLast(timeStampList);
		Assert.assertEquals(107l, (long)last.getTimeMilis());
	}
	
	@Test
	public void testGetFromBack_Final() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp last = TimeStampListUtils.getFromBack(timeStampList, 0);
		Assert.assertEquals(107l, (long)last.getTimeMilis());
	}
	
	@Test
	public void testGetFromBack_Middle() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp last = TimeStampListUtils.getFromBack(timeStampList, 3);
		Assert.assertEquals(30l, (long)last.getTimeMilis());		
	}

}
