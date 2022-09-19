package app;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**A simple logger that outputs both to console and to a unique file each runtime.
 * */
public class AppLog {

	private static DateTimeFormatter date_time_format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	private static final String app_start_time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
	private static PrintWriter log_output;
	
	/**Initializes the logger's print writer so log messages can be printed to a file.
	 * */
	public static void initialize() {
		try{
			log_output = new PrintWriter(new FileWriter("log_"+app_start_time+".txt"));
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public static void close() {
		log_output.close();
	}
	
	/**Prints a message with the ERROR label both to console and to a log file.
	 * */
	public static void err(Object src, String msg) {
		String s = String.format("[%s][%s][%s]: %s\n", "ERROR",date_time_format.format(LocalDateTime.now()), src.getClass().getName(), msg);
		System.out.print(s);
		log_output.print(s);
	}
	
	/**Prints a message with the INFO label both to console and to a log file.
	 * */
	public static void info(Object src, String msg) {
		String s = String.format("[%s][%s][%s]: %s\n", "INFO", date_time_format.format(LocalDateTime.now()), src.getClass().getName(), msg);
		System.out.print(s);
		log_output.print(s);
	}
	
	/**Prints the stack trace (details) of an exception thrown at runtime to console and to a log file.
	 * */
	public static void printStackTrace(Object src, StackTraceElement[] stack_trace) {
		String s = String.format("[%s][%s][%s]: Stack trace output:\n", "ERROR", date_time_format.format(LocalDateTime.now()), src.getClass().getName());
		System.out.printf(s);
		log_output.print(s);
		
		for(StackTraceElement st : stack_trace) {
			System.out.println(st.toString());
			log_output.print(st.toString());
		}
	}
	
}//end of class
