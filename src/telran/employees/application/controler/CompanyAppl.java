package telran.employees.application.controler;

import java.util.ArrayList;
import java.util.Arrays;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class CompanyAppl {
	private static final String FILE_PATH = "employees.data";

	public static void main(String[] args) {
		StandartInputOutput io = new StandartInputOutput();
		Company company = new CompanyImpl();
		company.restore(FILE_PATH);
		Item[] companyItems = CompanyControllerItems.getCompanyItems(company,
				new String[] { "QA", "Development", "Audit", "Management", "Accounting" });
		ArrayList<Item> items = new ArrayList<>(Arrays.asList(companyItems));
		items.add(Item.of("Exit & save", io1 -> company.save(FILE_PATH), true));
		Menu menu = new Menu("Employee app", items);
		menu.perform(io);
	}

}
