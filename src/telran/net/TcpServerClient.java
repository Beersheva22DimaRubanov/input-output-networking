package telran.net;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TcpServerClient implements Runnable {

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	private Protocol protocol;
	
	public TcpServerClient(Socket socket, Protocol protocol) throws IOException {
		this.socket = socket;
		this.protocol = protocol;
		input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run() {
		while(true) {
			try {
				Request request = (Request) input.readObject();
				Response response = protocol.getResponse(request);
				output.writeObject(response);
			} catch (EOFException e) {
				System.out.println("client closed connection");
				break;
			} catch(Exception e) {
				throw new RuntimeException(e.toString());
			}
		}
	}

}
