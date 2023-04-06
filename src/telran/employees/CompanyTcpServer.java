package telran.employees;

import telran.employees.net.CompanyProtocol;
import telran.net.Protocol;
import telran.net.TcpServer;

public class CompanyTcpServer {
	private static final int PORT = 4000;

	public static void main(String[] args) throws Exception {
		CompanyImpl company = new CompanyImpl();
		Protocol protocol = new CompanyProtocol(company);
		TcpServer server = new TcpServer(protocol, PORT);
		server.run();
	}
}
