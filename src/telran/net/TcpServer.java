package telran.net;

import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer implements Runnable {
	private Protocol protocol;
	private int port;
	private ServerSocket serverSocket;

	public TcpServer(Protocol protocol, int port) throws Exception {
		this.protocol = protocol;
		this.port = port;
		serverSocket = new ServerSocket(port);
	}

	@Override
	public void run() {
		System.out.println("Server listening on poort" + this.port);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				TcpServerClient serverClient = new TcpServerClient(socket, protocol);
				Thread thread = new Thread(serverClient);
				thread.start();
			} catch (Exception e) {
				System.out.println(e.toString());
			}
		}
	}

}
