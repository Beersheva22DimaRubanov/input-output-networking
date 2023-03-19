package telran.net.application;

import java.net.*;
import java.io.*;

public class ServerTcpAppl {
	private static final int PORT = 4000;

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("server listening on port " + PORT);
		while (true) {
			Socket socket = serverSocket.accept();
			try {
				runServerClient(socket);
			} catch (IOException e) {
				System.out.println("abnormal closing connection");
			}
		}
	}

	private static void runServerClient(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream writer = new PrintStream(socket.getOutputStream());
		while (true) {
			String request = reader.readLine();
			if (request == null) {
				break;
			}
			String response = getResponse(request);
			writer.println(response);
		}
		System.out.println("client closed connection");
	}

	private static String getResponse(String request) {
		String res = "Wronf request";
		String tokens[] = request.split("#");
		if(tokens.length == 2) {
			res = switch(tokens[0]) {
			case "reverse" -> new StringBuilder(tokens[1]).reverse().toString();
			case "length" -> tokens[1].length() + "";
			default -> "Wrong type " + tokens[0];
			};
		}
		return res;
	}
}
