package telran.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;

public class TcpServerClient implements Runnable {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Protocol protocol;
	private ExecutorService executor;

	public TcpServerClient(Socket socket, Protocol protocol, ExecutorService executor)
			throws IOException {
		this.socket = socket;
		this.protocol = protocol;
		this.executor = executor;
		socket.setSoTimeout(5000);
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		boolean running = true;
		while (running) {
			try {
				Request request = (Request) input.readObject();
				Response response = protocol.getResponse(request);
				output.reset();
				output.writeObject(response);
			} catch (SocketTimeoutException e) {
				if(executor.isShutdown()) {
					System.out.println("No new clients, shutdown");
					running = false;
				}
				
			} catch (EOFException e) {
				System.out.println("client closed connection");
				running = false;
			} catch (Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
