package telran.employees.test;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import telran.employees.net.NetworkCompany;
import telran.net.TcpClient;

class CompanyTcpTest extends CompanyTest {

	static NetworkCompany netProxy;
	@BeforeAll
	static void createProxy() throws Exception {
		netProxy = new NetworkCompany(new TcpClient("localhost", 4000));
	}
@BeforeEach
@Override
void setUp() throws Exception{
	company = netProxy;
	company.forEach(e -> company.removeemployee(e.getId()));
	super.setUp();
}
@AfterAll
static void closeConnection() throws IOException {
	
	netProxy.close();
}

}
