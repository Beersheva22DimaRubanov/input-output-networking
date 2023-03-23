package telran.net;

public class NetworkTcpExample {
	private static final int PORT = 4000;
	private static final String HOST = "localhost";
	private static final String REVERSE = "reverse";
	private static final String LENGTH = "length";

	public static void main(String[] args) throws Exception {
		TcpClient client = new TcpClient(HOST, PORT);
		String response =  client.send(LENGTH, "Data");
		System.out.println(response);
		String res = client.send(REVERSE, "Computer");
		System.out.println(res);
	}
}
