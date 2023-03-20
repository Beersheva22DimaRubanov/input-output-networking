package telran.net.application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServerLogAppl {
	private static final int PORT = 4000;
	private static HashMap<String, List<String>> logs = new HashMap<>();

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
		String res = "Wrong request";
		String tokens[] = request.split("#");
		if(tokens.length >= 2) {
			res = switch(tokens[0]) {
			case "log" -> addLog(tokens[1], tokens[2]);
			case "counter" -> getLogCount(tokens[1]);
//			case "counter" -> switch(tokens[1]) {
//			case "error" -> logs.get("error").size() + "";
//			case "warn" -> logs.get("warn").size() + "";
//			case "info" -> logs.get("info").size() + "";
//			case "debug" -> logs.get("debug").size() + "";
//			case "trace" -> logs.get("trace").size() + "";
//			
//			default -> "Wrong Level " + tokens[1]; 
//			};
			default -> "Wrong type " + tokens[0];
			};
		}
		return res;
	}

	private static String getLogCount(String level) {
		String res = switch(level) {
		case "error" -> logs.get("error").size() + "";
		case "warn" -> logs.get("warn").size() + "";
		case "info" -> logs.get("info").size() + "";
		case "debug" -> logs.get("debug").size() + "";
		case "trace" -> logs.get("trace").size() + "";
		
		default -> "Wrong Level " + level; 
		};
		return res;
	}

	private static String addLog(String key, String string) {
		String res = "Not OK";
//		List<String> list = logs.get(key);
//		if( list == null) {
//			list = new ArrayList<>();
//			logs.put(key, list);
//		}
//		list.add(string);
		if(logs.computeIfAbsent(key, k-> new ArrayList<>()).add(string)) {
			res = "OK" + logs.size();
		};
		return res;
	}
}
