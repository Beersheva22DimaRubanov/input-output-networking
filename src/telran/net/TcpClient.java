package telran.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;

public class TcpClient implements NetworkClient {
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;

	public TcpClient(String hostname, int port) throws Exception {
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
		try {
			output.writeObject(request);
			Response response = (Response) input.readObject();
			if (!response.code.equals(ResponseCode.OK)) {
				throw new Exception(response.data.toString());

			}
			res = (T) response.data;
		} catch (Exception e) {
			throw new RuntimeException();
		}
		return res;
	}

}
