package telran.employees.application.controler;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class CompanyAppl {
	public static void main(String[] args) {
		StandartInputOutput io = new StandartInputOutput();
		Company company = new CompanyImpl();
		CompanyControllerItems controller = new CompanyControllerItems(company, io);
		Menu menu = new Menu("Employee app", controller.getAdminItemMenu(), 
				controller.getUserItemMenu(), Item.exit());
		menu.perform(io);
	}

}
