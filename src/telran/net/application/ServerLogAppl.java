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

import telran.utils.Level;

public class ServerLogAppl {
	public static final int PORT = 4000;
	static HashMap<String, Integer> logCounters = new HashMap<>();
	public static final String OK = "ok";
	private static final String WRONG_LEVEL = "Wrong Level in logger record";
	public static final String LOG_TYPE = "log";
	public static final String COUNTER_TYPE = "counter";

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
		String tokens[] = request.split("#");
		String response = "";
		if (tokens.length != 2) {
			response = "Wrong request: should be <type>#<string>";
		} else {
			response = switch (tokens[0]) {
			case LOG_TYPE -> processLogRequest(tokens[1]);
			case COUNTER_TYPE -> processCounterRequest(tokens[1]);
			default -> String.format("Wrong request type: should be either %s or %s", LOG_TYPE,
					COUNTER_TYPE);
			};

		}
		return response;
	}

	private static String processCounterRequest(String requestData) {

		return "" + logCounters.getOrDefault(requestData.toUpperCase(), 0);
	}

	private static String processLogRequest(String requestData) {
		Level level = getLevel(requestData);
		String res = WRONG_LEVEL;
		if (level != null) {
			res = OK;
			logCounters.merge(level.toString(), 1, Integer::sum);
		}
		return res;
	}

	private static Level getLevel(String requestData) {
		Level levels[] = Level.values();
		int index = 0;
		while (index < levels.length && !requestData.contains(levels[index].toString())) {
			index++;
		}
		return index < levels.length ? levels[index] : null;
	}

//	private static String getLogCount(String level) {
//		String res = switch(level) {
//		case "error" -> logs.get("error").size() + "";
//		case "warn" -> logs.get("warn").size() + "";
//		case "info" -> logs.get("info").size() + "";
//		case "debug" -> logs.get("debug").size() + "";
//		case "trace" -> logs.get("trace").size() + "";
//		
//		default -> "Wrong Level " + level; 
//		};
//		return res;
//	}
//
//	private static String addLog(String key, String string) {
//		String res = "Not OK";
//		if(logs.computeIfAbsent(key, k-> new ArrayList<>()).add(string)) {
//			res = "OK" + logs.size();
//		};
//		return res;
//	}
}
