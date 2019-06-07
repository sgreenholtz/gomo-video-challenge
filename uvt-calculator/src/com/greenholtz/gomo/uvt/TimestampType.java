package com.greenholtz.gomo.uvt;

public enum TimestampType {
	START, END;
	
	public String shortDisplay() {
		if (this==START) {
			return "S";
		} else {
			return "E";
		}
	}
}
