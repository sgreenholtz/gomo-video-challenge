package com.greenholtz.gomo.uvt;

public class TimeStamp implements Comparable<TimeStamp> {

	private int index;
	private TimestampType type;
	private Long timeMilis;
	
	public TimeStamp(int index, TimestampType type, Long timeMilis) {
		this.index=index;
		this.timeMilis=timeMilis;
		this.type=type;
	}
	
	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public TimestampType getType() {
		return type;
	}

	public void setType(TimestampType type) {
		this.type = type;
	}

	public Long getTimeMilis() {
		return timeMilis;
	}

	public void setTimeMilis(Long timeMilis) {
		this.timeMilis = timeMilis;
	}

	enum Type {
		START, END;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("(").append(index).append(type.shortDisplay()).append(")").append(timeMilis).toString();
	}

	@Override
	public int compareTo(TimeStamp timeToCompare) {
		return this.getTimeMilis().compareTo(timeToCompare.getTimeMilis());
	}
}
