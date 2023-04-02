package telran.employees.application.controler;

import telran.employees.Company;
import telran.employees.net.NetworkCompany;
import telran.net.TcpClient;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class CompanyClientAppl {
	public static void main(String[] args) throws Exception {
		StandartInputOutput io = new StandartInputOutput();
		Company company = new NetworkCompany(new TcpClient("localhost", 4000));
		CompanyControllerItems companyControllerItems = new CompanyControllerItems(company, io);
		Menu menu = new Menu("Company app", companyControllerItems.getAdminItemMenu(),
				companyControllerItems.getUserItemMenu());
		menu.perform(io);
	}
}
