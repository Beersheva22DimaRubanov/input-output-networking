package telran.util;

import java.io.PrintStream;

public class SimpleStreamHandler implements Handler {
	private PrintStream stream;

	public SimpleStreamHandler(PrintStream stream) {
		this.stream = stream;
	}
	
	@Override
	public void publish(LoggerRecord loggerRecord) {
		try {
			stream.print(loggerRecord.toString());;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
