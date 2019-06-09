package com.greenholtz.gomo.uvt.launcher;

import java.io.Console;
import java.util.Arrays;

import com.greenholtz.gomo.uvt.UVTAlgorithm;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

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
				.append("view ENDED, the next number is a START timestamp, the next number is an END timestamp, etc.")
				.append(System.lineSeparator())
				.append("You can also use the -i flag and input the timestamps, and use the -v flag to specify verbose output.");
			System.out.println(helpMessage);			
		} else if (args[0].equals("-v")) {
			Logger root = (Logger) org.slf4j.LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		    root.setLevel(Level.DEBUG);
		}
		
		if (Arrays.asList(args).contains("-i")) {
			int inputFlagIndex = Arrays.asList(args).indexOf("-i");
			String[] timeSegmentArr = Arrays.copyOfRange(args, inputFlagIndex+1, args.length-1);
			UVTAlgorithm.uniqueViewTimeCalculator(timeSegmentArr);
		} else {
			Console console = System.console();
			String timeSegmentsStr = console.readLine("Input view time segments: ");
			UVTAlgorithm.uniqueViewTimeCalculator(timeSegmentsStr);
		}
		
	}
	
}
