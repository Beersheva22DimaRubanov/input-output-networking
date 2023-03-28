package telran.employees.net.application;

import telran.employees.Company;
import telran.employees.CompanyImpl;

public class CompanyTcpApplication {
	public static void main(String[] args) {
		Company company = new CompanyImpl();
		company.restore("company.data");
		
	}
}
