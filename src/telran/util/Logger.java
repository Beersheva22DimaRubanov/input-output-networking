package telran.util;

import java.time.Instant;
import java.time.ZoneId;

public class Logger {
	private Level level = Level.INFO;
	private Handler handler;
	private String name;

	public Logger(Handler handler, String name) {
		this.handler = handler;
		this.name = name;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public void error(String message) {
		if(level.compareTo(Level.ERROR)  <=0) {
			makeLog(message);
		}
			
	}
 
	public void warn(String message) {
		if(level.compareTo(Level.WARN)  <=0) {
			makeLog(message);
		}
	}

	public void info(String message) {
		if(level.compareTo(Level.INFO)  <=0) {
			makeLog(message);
		}
	}

	

	public void debug(String message) {
		if(level.compareTo(Level.DEBUG)  <=0) {
			makeLog(message);
		}
	}

	public void trace(String message) {
		if(level.compareTo(Level.TRACE)  <=0) {
			makeLog(message);
		}
	}
	
	private void makeLog(String message) {
		LoggerRecord loggerRecord = new LoggerRecord(Instant.now(), 
				ZoneId.systemDefault().toString(), level, name, message);
		handler.publish(loggerRecord);
	}
	
}
