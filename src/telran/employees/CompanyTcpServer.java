package telran.employees;

import telran.employees.net.ComponyProtocol;
import telran.net.Protocol;
import telran.net.TcpServer;

public class CompanyTcpServer {
	private static final int PORT = 4000;

	public static void main(String[] args) throws Exception {
		Protocol protocol = new ComponyProtocol();
		TcpServer server = new TcpServer(protocol, PORT);
		server.run();
	}
}
