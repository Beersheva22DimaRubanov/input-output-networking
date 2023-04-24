package telran.employees.net.application;

import java.util.Scanner;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.employees.net.CompanyProtocol;
import telran.net.Protocol;
import telran.net.TcpServer;

public class CompanyTcpApplication {
	private static final String FILE_NAME = "company.data";

	public static void main(String[] args) throws Exception {
		Company company = new CompanyImpl();
		company.restore(FILE_NAME);
		Protocol protocol = new CompanyProtocol(company);
		TcpServer server = new TcpServer(protocol, 4000);
		Thread thread = new Thread(server);
		thread.start();
		Scanner scanner = new Scanner(System.in);
		boolean running = true;
		while(running) {
			System.out.println("For stopping server enter command 'shutdown'");
			String line = scanner.nextLine();
			if(line.equals("shutdown")) {
				company.save(FILE_NAME);
				server.shutdown();
				
				System.out.println("Closing TCP app");
				running = false;
				scanner.close();
			}
		}
	}
}
