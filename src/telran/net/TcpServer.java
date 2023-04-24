package telran.net;

import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TcpServer implements Runnable {
	private Protocol protocol;
	private int port;
	private ServerSocket serverSocket;
	private ExecutorService executor;
	private boolean shutdown = false;

	public TcpServer(Protocol protocol, int port) throws Exception {
		this.protocol = protocol;
		this.port = port;
		serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(10000);
		int nThreads = Runtime.getRuntime().availableProcessors();
		System.out.println("Number threads in threads pool is: " + nThreads);
		executor = Executors.newFixedThreadPool(nThreads);
	}

	@Override
	public void run() {
		System.out.println("Server listening on poort" + this.port);
		while (!shutdown) {
			try {
				Socket socket = serverSocket.accept();
				TcpServerClient serverClient = new TcpServerClient(socket, protocol, executor);
				executor.execute(serverClient);
				
			} catch (SocketTimeoutException e) {
				if (executor.isTerminated()) {
					try {
						executor.awaitTermination(60, TimeUnit.SECONDS);
						System.out.println("Server has been shutdown");
						shutdown = true;
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			} catch (Exception c) {
				System.out.println(c.toString());
			}
		}
	}

	public void shutdown() {
		executor.shutdown();
	}

}
