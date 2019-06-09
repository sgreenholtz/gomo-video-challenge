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
			StringBuilder helpMessage = new StringBuilder()
				.append("In order to run the Unique View Time Calculator, call the main method with the argument 'input'.")
				.append(System.lineSeparator())
				.append("You will be prompted for a list of view times entered as a space-delimited list where the first")
				.append(System.lineSeparator())
				.append("number is the timestamp of when the view STARTED, the second number is the timestamp of when the")
				.append(System.lineSeparator())
				.append("view ENDED, the next number is a START timestamp, the next number is an END timestamp, etc.");
			System.out.println(helpMessage);
		} else if (args[0].equals("input")) {
			Console console = System.console();
			String timeSegmentString = console.readLine("Input view time segments: ");
			UVTAlgorithm.uniqueViewTimeCalculator(timeSegmentString);
		} else {
			System.out.println("Unknown option specified. The only available commands are 'help' and 'input'.");
		}
	}
}
