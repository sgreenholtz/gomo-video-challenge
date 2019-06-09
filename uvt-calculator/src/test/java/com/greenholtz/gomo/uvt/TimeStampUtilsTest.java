package com.greenholtz.gomo.uvt;

import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import com.greenholtz.gomo.uvt.entities.TimeStamp;


public class TimeStampUtilsTest extends AbstractTest{

	@Test
	public void testGetStartFromEnd() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp start = TimeStampUtils.getStartFromEnd(timeStampList, 2);
		Assert.assertEquals(timeStampList.get(2), start);
	}

	@Test
	public void testGetEndFromStart() {
		List<TimeStamp> timeStampList = getTimeStampList_OneIteration();
		TimeStamp end = TimeStampUtils.getEndFromStart(timeStampList, 3);
		Assert.assertEquals(timeStampList.get(4), end);
	}

}
