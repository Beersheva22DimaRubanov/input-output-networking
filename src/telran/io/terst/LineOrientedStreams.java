package telran.io.terst;

import java.io.PrintStream;
import java.io.PrintWriter;

import org.junit.jupiter.api.Test;

public class LineOrientedStreams {
	static final String fileNamePrintStream = "lines-stream.txt";
	static final String filenamePrintWriter = "lines-writer.txt";
	static final String line = "Hello World!!!";
	
	@Test
	void printStreamTest() throws Exception {
		PrintStream printStream = new PrintStream(fileNamePrintStream);
		printStream.println(line);
	}
	
	@Test
	void printWritterTest() throws Exception {
		try(PrintWriter printWriter = new PrintWriter(filenamePrintWriter)){
			printWriter.println(line);
		}
	}
}
