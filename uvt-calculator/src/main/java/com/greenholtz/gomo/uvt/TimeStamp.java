package com.greenholtz.gomo.uvt;

public class TimeStamp implements Comparable<TimeStamp> {

	private int id;
	private TimestampType type;
	private Long timeMilis;
	
	public TimeStamp(int id, TimestampType type, Long timeMilis) {
		this.id=id;
		this.timeMilis=timeMilis;
		this.type=type;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
		return new StringBuilder("(").append(id).append(type.shortDisplay()).append(")").append(timeMilis).toString();
	}
	
	@Override
	public int compareTo(TimeStamp timeToCompare) {
		return this.getTimeMilis().compareTo(timeToCompare.getTimeMilis());
	}
	
}
