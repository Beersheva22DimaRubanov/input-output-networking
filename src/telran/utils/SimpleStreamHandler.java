package telran.utils;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class SimpleStreamHandler implements Handler {

	private PrintStream stream;
	public SimpleStreamHandler(PrintStream stream) {
		this.stream = stream;
	}
	@Override
	public void publish(LoggerRecord loggerRecord) {
		LocalDateTime ldt = LocalDateTime.ofInstant(loggerRecord.timestamp,
				ZoneId.of(loggerRecord.zoneId));
		stream.printf("%s %s %s %s\n", ldt, loggerRecord.level,
				loggerRecord.loggerName, loggerRecord.message);

	}
}
