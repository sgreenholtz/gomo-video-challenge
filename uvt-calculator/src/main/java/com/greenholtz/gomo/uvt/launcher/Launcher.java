package com.greenholtz.gomo.uvt.launcher;

import java.io.Console;

import com.greenholtz.gomo.uvt.UVTAlgorithm;

/**
 * Kick-off point for runnable jar
 * @author smgre
 */
public class Launcher {
	
	/**
	 * MAIN method containing logic for outputting info about how to run
	 * the application as well as inputting the times
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length==0 || args[0].equals("help")) {
			// insert the explanation text here
		} else if (args[0].equals("input")) {
			Console console = System.console();
			String timeSegmentString = console.readLine("Input view time segments: ");
			UVTAlgorithm.uniqueViewTimeCalculator(timeSegmentString);
		} else {
			// insert text stating the only options are "help" or "input"
		}
	}
}