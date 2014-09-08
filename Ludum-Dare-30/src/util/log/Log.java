package util.log;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {

	private static File log;
	private static PrintStream printStream;
	private static DateFormat dateFormat;
	private static DateFormat timeFormat;

	public static boolean openLog(String path) {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		timeFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			Log.log = new File(path + "/Log.log");
			File logOld = new File(path + "/Log.log.old");
			if (log.exists()) {
				Files.copy(log.toPath(), logOld.toPath(), StandardCopyOption.REPLACE_EXISTING);
				log.delete();
			}
			new File(log.getParent()).mkdirs();
			log.createNewFile();
			printStream = new PrintStream(log);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void closeLog() {
		printStream.flush();
		printStream.close();
	}

	private static void writeLog(String msg, String level) {
		StringBuilder sb = new StringBuilder();

		Date date = new Date();
		sb.append('[');
		sb.append(dateFormat.format(date));
		sb.append(']');

		sb.append('[');
		sb.append(timeFormat.format(date));
		sb.append(']');

		sb.append('[');
		sb.append(level);
		sb.append(']');

		StackTraceElement e = Thread.currentThread().getStackTrace()[3];
		String source = e.getFileName().replace(".java", "") + ':' + e.getLineNumber();
		sb.append('[');
		sb.append(source);
		sb.append(']');

		sb.append(' ');
		sb.append(msg);

		printStream.println(sb.toString());
	}

	public static void info(String msg) {
		writeLog(msg, "INFO");
	}

	public static void warning(String msg) {
		writeLog(msg, "WARNING");
	}

	public static void error(String msg) {
		writeLog(msg, "ERROR");
	}

	public static void debug(String msg) {
		writeLog(msg, "DEBUG");
	}

}
