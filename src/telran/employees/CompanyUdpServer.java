package telran.employees;

import telran.net.Protocol;
import telran.net.UdpServer;

public class CompanyUdpServer {
	private static final int PORT = 4000;

	public static void main(String[] args) throws Exception {
		Protocol protocol = new ComponyProtocol();
		UdpServer server = new UdpServer(PORT, protocol);
		server.run();
	}
}
