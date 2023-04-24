package telran.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class TcpClient implements NetworkClient {
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String hostName;
	private int port;

	public TcpClient(String hostname, int port) throws Exception {
		this.hostName = hostname;
		this.port = port;
		socket = new Socket(hostname, port);
		output = new ObjectOutputStream(socket.getOutputStream());
		input = new ObjectInputStream(socket.getInputStream());
	}

	@Override
	public void close() throws IOException {
		socket.close();
	}

	@Override
	public <T> T send(String type, Serializable requestData) {
		T res = null;
		Request request = new Request(type, requestData);
		boolean serverIsAvailable = true;
		while (serverIsAvailable ) {
			try {
				output.writeObject(request);
				Response response = (Response) input.readObject();
				if (!response.code.equals(ResponseCode.OK)) {
					throw new Exception(response.data.toString());

				}
				res = (T) response.data;
				break;
			} catch(SocketException e) {
				serverIsAvailable =  tryReconnect();
			}
			catch (Exception e) {
				throw new RuntimeException();

			} 
		}
		return res;
	}

	private boolean tryReconnect() {
		boolean reconnectSuccessfully = false;
		try {
			System.out.println("Connection has been lost, try to reconnect");
			socket.close();
			socket = new Socket(hostName, port);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());
		} catch (ConnectException e1) {
			throw new RuntimeException(e1.getMessage());
		} catch (Exception e1) {
			throw new RuntimeException(e1.getMessage());
		}
		reconnectSuccessfully = socket.isConnected();
		return reconnectSuccessfully;
	}

}
