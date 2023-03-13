package telran.io.test;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.util.*;

class LoggerTest {
	private String fileName = "logs.txt";


	@BeforeEach
	void setUp() throws Exception {
		Files.deleteIfExists(Path.of(fileName));
	}

	@Test
	void logerTest() {
		 Handler consoleHandler = new SimpleStreamHandler(new PrintStream(System.out));
		 Logger LOG_CONSOLE = new Logger(consoleHandler, "consoleLoger");
		 
		 allLogs(LOG_CONSOLE);
		 
		 LOG_CONSOLE.setLevel(Level.TRACE);
		 System.out.println("\n\n");
		 allLogs(LOG_CONSOLE);
		 
	}
	
	@Test
	void fileLoggerTest() throws FileNotFoundException {
		 Handler fileHandler = new SimpleStreamHandler(new PrintStream(fileName));
		 Logger LOG_FILE = new Logger(fileHandler, "fileLoger");
		 allLogs(LOG_FILE);
	}

	private void allLogs(Logger LOG) {
		LOG.trace("Trace log");
		 LOG.debug("Debug log");
		 LOG.info("Info log");
		 LOG.warn("Warn log");
		 LOG.error("Error log");
	}

}
