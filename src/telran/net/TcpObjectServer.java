package telran.net;

public class TcpObjectServer {
private static final int PORT = 4000;

public static void main(String[] args) {
	StringProtocol stringProtocol = new StringProtocol();
	TcpServer server;
	try {
		server = new TcpServer(stringProtocol, PORT);
		server.run();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
