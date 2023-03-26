package telran.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;

import telran.net.application.ServerLogAppl;

public class TcpClientHandler implements Handler {
	Socket socket;
	PrintStream output;
	BufferedReader input;

	public TcpClientHandler(String host, int port) throws IOException {
		socket = new Socket(host, port);
		output = new PrintStream(socket.getOutputStream());
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	@Override
	public void publish(LoggerRecord loggerRecord) {
		LocalDateTime ldt = LocalDateTime.ofInstant(loggerRecord.timestamp,
				ZoneId.of(loggerRecord.zoneId));
		String message = String.format("%s %s %s %s", ldt, loggerRecord.level,
				loggerRecord.loggerName, loggerRecord.message);
		output.println(ServerLogAppl.LOG_TYPE + "#" + message);
		try {
			String response = input.readLine();
			if (!response.equals(ServerLogAppl.OK)) {
				throw new RuntimeException("Response from Logger Server is " + response);
			}
		} catch (IOException e) {
			new RuntimeException(e.getMessage());
		}
	}
	
	@Override
	public void close() {
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
