package telran.employees;

import telran.employees.net.CompanyProtocol;
import telran.net.Protocol;
import telran.net.UdpServer;

public class CompanyUdpServer {
	private static final int PORT = 4000;

	public static void main(String[] args) throws Exception {
		Protocol protocol = new CompanyProtocol(new CompanyImpl());
		UdpServer server = new UdpServer(PORT, protocol);
		server.run();
	}
}
