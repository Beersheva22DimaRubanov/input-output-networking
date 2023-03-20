package telran.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TcpClientHandlerTest {
	private static final String HOST = "localhost";
	private static final int PORT = 4000;
	static TcpClientHandler handler;
	static Logger logger;
	
	@BeforeAll
	static void setUp() throws Exception {
		handler = new TcpClientHandler(HOST, PORT);
		logger = new Logger(handler, "TcpLogger");
	}

	@Test
	@Order(1)
	void test() {
		logger.setLevel(Level.TRACE);
		logger.debug("debug#1");
		logger.debug("debug#2");
		logger.trace("trace#1");
		logger.warn("warn#1");
		logger.info("info#1");
		logger.error("error#1");
		logger.error("error#2");
		logger.error("error#3");
	}
	
	@Test
	void loggerCounterTest() {
		assertEquals(3, handler.getLogCount("error"));
		assertEquals(2, handler.getLogCount("debug"));
		assertEquals(1, handler.getLogCount("trace"));
		assertEquals(1, handler.getLogCount("warn"));
		assertEquals(1, handler.getLogCount("info"));
	}
	
	@AfterAll
	static void close() {
		handler.close();
	}

}
