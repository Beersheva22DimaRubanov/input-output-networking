package telran.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoggerTest {
	private static final String LOGGER_FILE = "logFile.txt";
	Handler handler;
	Logger logger;
	BufferedReader reader;
	@BeforeEach
	void setUp() throws FileNotFoundException {
		File file = new File(LOGGER_FILE);
		
		handler = new SimpleStreamHandler(new PrintStream(file));
		logger = new Logger(handler, "test-logger");
		reader = new BufferedReader(new FileReader(file));
	}
		
		private void logging() {
			logger.error("error message");
			logger.warn("warn message");
			logger.info("info message");
			logger.debug("debug message");
			logger.trace("trace message");
		}
		@Test
		void errorTest() {
			logger.setLevel(Level.ERROR);
			logging();
			String [] levels = {Level.ERROR.toString()};
			runTestContent(levels);
			
		}
		@Test
		void warnTest() {
			logger.setLevel(Level.WARN);
			logging();
			String [] levels = {Level.ERROR.toString(), Level.WARN.toString()};
			runTestContent(levels);
			
		}
		@Test
		void infoTest() {
			logger.setLevel(Level.INFO);
			logging();
			String [] levels = {Level.ERROR.toString(), Level.WARN.toString(),
					Level.INFO.toString()};
			runTestContent(levels);
			
		}
		@Test
		void debugTest() {
			logger.setLevel(Level.DEBUG);
			logging();
			String [] levels = {Level.ERROR.toString(), Level.WARN.toString(),
					Level.INFO.toString(), Level.DEBUG.toString()};
			runTestContent(levels);
			
		}
		@Test
		void traceTest() {
			logger.setLevel(Level.TRACE);
			logging();
			String [] levels = {Level.ERROR.toString(), Level.WARN.toString(),
					Level.INFO.toString(), Level.DEBUG.toString(), Level.TRACE.toString()};
			runTestContent(levels);
			
		}
		@Test
		void consoleTest() {
			handler = new SimpleStreamHandler(System.out);
			logger = new Logger(handler, "logger-console-test");
			
			System.out.println("***********************Logger for console******************");
			System.out.println("******Should contain 3 logger records for ERROR, WARN and INFO levels******************");
			logging();
		}

		private void runTestContent(String[] levels) {
			List<String> records =  reader.lines().collect(Collectors.toCollection(ArrayList::new));
			assertEquals(levels.length, records.size());
			IntStream.range(0, levels.length).forEach(i -> assertTrue(records.get(i).contains(levels[i])));
			
		}
}
