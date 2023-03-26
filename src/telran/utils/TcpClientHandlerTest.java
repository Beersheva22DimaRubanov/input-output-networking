package telran.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.net.application.ServerLogAppl;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TcpClientHandlerTest {
private static final int N_ERRORS = 1;
private static final int N_WARNS = 2;
private static final int N_INFOS = 3;
private static final int N_DEBUGS = 4;
private static final int N_TRACES = 5;
static Handler logHandler;
static Logger logger;
static Socket socket;
static PrintStream stream;
static BufferedReader reader;
@BeforeAll
static void createLogger() throws Exception {
	logHandler = new TcpClientHandler("localhost", ServerLogAppl.PORT);
	logger = new Logger(logHandler, "test-tcp-logger");
	logger.setLevel(Level.TRACE);
	socket = new Socket("localhost", ServerLogAppl.PORT);
	stream = new PrintStream(socket.getOutputStream());
	reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
}
	@Order(1)
	@Test
	void sending() {
		sendMessage(N_ERRORS, i -> logger.error("message" + i));
		sendMessage(N_WARNS, i -> logger.warn("message" + i));
		sendMessage(N_INFOS, i -> logger.info("message" + i));
		sendMessage(N_DEBUGS, i -> logger.debug("message" + i));
		sendMessage(N_TRACES, i -> logger.trace("message" + i));
		logHandler.close();
		
	}
	
	private void sendMessage(int nSendings, IntConsumer consumer) {
		IntStream.range(0, nSendings).forEach(consumer);
	}
	@Test
	void counterErrorTest() throws Exception {
		runCounterTest("ERROR", N_ERRORS);
	}
	@Test
	void counterWarnTest() throws Exception {
		runCounterTest("WARN", N_WARNS);
	}
	private void runCounterTest(String level, int nLogs) throws Exception{
		stream.println("counter#" + level);
		int actual = Integer.parseInt(reader.readLine());
		assertEquals(nLogs , actual);
		
	}
	@Test
	void counterInfoTest() throws Exception {
		runCounterTest("INFO", N_INFOS);
	}
	@Test
	void counterDebugTest() throws Exception {
		runCounterTest("DEBUG", N_DEBUGS);
	}
	@Test
	void counterTraceTest() throws Exception {
		runCounterTest("TRACE", N_TRACES);
	}
	@AfterAll
	static void closing () throws IOException {
		socket.close();
	}

}