package telran.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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
		String line = "log#" + loggerRecord.level.toString().toLowerCase() + "#" + loggerRecord.toString();
		output.println(line);
		try {
			String response = input.readLine();
			System.out.println(response);
		} catch (IOException e) {
			System.out.println("Wrong answer");
		}
	}
	
	public int getLogCount(String level) {
		String line = "counter#" + level;
		output.println(line);
		String response = "";
		try {
			response = input.readLine();
		} catch (IOException e) {
			System.out.println("Wrong answer");
		}
		return Integer.parseInt(response);
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
