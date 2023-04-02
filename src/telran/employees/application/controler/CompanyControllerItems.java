package telran.employees.application.controler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.employees.Departments;
import telran.employees.Employee;
import telran.employees.net.NetworkCompany;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;
import telran.view.StandartInputOutput;

public class CompanyControllerItems {

	private static Company company;

	public CompanyControllerItems(Company company, StandartInputOutput io) {
		CompanyControllerItems.company = company;
	}

	public static Item getUserItemMenu() {
		return new Menu("User menu",
				new Item[] { Item.of("Get employee by id", t -> getEmployee(t)),
						Item.of("Get all employees", t -> getAllEmployees(t)),
						Item.of("Get employee by month birth", t -> getEmployeeByMonthBirth(t)),
						Item.of("Get employee by salary", t -> getEmployeesBySalary(t)),
						Item.of("Get employee by department", t -> getEmployeesByDepartment(t)),
						Item.exit() });
	}

	public static Item getAdminItemMenu() {
		Menu menu = new Menu("Admin menu", Item.of("Add employee", t -> addEmployee(t)),
				Item.of("Remove employee", t -> removeEmployee(t)),
				Item.of("Save to file", t -> saveToFile(t)),
				Item.of("Restore file", t -> restoreFile(t)),
				Item.of(getExitMessage(), t -> modifiedExit(t), true), Item.exit());
		return menu;
	}

	private static String getExitMessage() {
		String res = "";
		if (company instanceof NetworkCompany) {
			res = "Close conection and exit";
		} else if (company instanceof CompanyImpl) {
			res = "Close and save";
		}
		return res;
	}

	private static void removeEmployee(InputOutput io) {
		int id = io.readInt("Enter employee id", "Wrong number", 1, Integer.MAX_VALUE);
		Employee employee = company.removeemployee(id);
		if (employee != null) {
			io.writeLine("Delete employee: " + employee.getName() + " from: "
					+ employee.getDepartment());
		} else {
			io.writeLine("No employee with such ID");
		}
	}

	private static void addEmployee(InputOutput io) {
		int id = io.readInt("Enter emp id", "Wrong number", 1, Integer.MAX_VALUE);
		String name = io.readStringPredicate("Enter empl name", "Wrong input", t -> !t.isEmpty());
		LocalDate birthDate = io.readDateISO("Enter birth date(yyyy-MM-dd)", "Wrong date");
		String department = io.readStringOptions("Enter department ", "Wrong department",
				getDepartments());
		int salary = io.readInt("Enter salary", "Wrong number", 1, Integer.MAX_VALUE);
		if (!company.addEmployee(new Employee(id, name, birthDate, department, salary))) {
			io.writeLine("Wrong id. Try again");
		} else {
			io.writeLine("New employee has been added");
		}
	}

	private static void saveToFile(InputOutput io) {
		String pathName = io.readStringPredicate("Enter file name", "Wrong name",
				t -> t.equals("^[A-Za-z]"));
		company.save(pathName);
	}

	private static void restoreFile(InputOutput io) {
		String pathName = io.readStringPredicate("Enter file name", "Wrong name",
				t -> t.equals("[A-Z]+[a-z]"));
		company.restore(pathName);
	}

	private static void getEmployee(InputOutput io) {
		int id = io.readInt("Entere employee id", "Wrong number", 1,
				company.getAllEmployees().size());
		Employee empl = company.getEmployee(id);
		writeEmployees(empl, io);
	}

	private static void getAllEmployees(InputOutput io) {
		List<Employee> employees = company.getAllEmployees();
		writeEmployees(employees, io);

	}

	private static void writeEmployees(Object obj, InputOutput io) {
		Object message = obj == null ? ("There are no employees in the company") : obj;
		io.writeLine(message);
	}

	private static void getEmployeeByMonthBirth(InputOutput io) {
		int monthNum = io.readInt("Entere employee month of birth", "Wrong number", 1, 12);
		writeEmployees(company.getEmployeesByMonthBirth(monthNum), io);

	}

	private static void getEmployeesBySalary(InputOutput io) {
		int salaryFrom = io.readInt("Entere salary from", "Wrong number", 1, Integer.MAX_VALUE);
		int salaryTo = io.readInt("Entere salary to", "Wrong number", 1, Integer.MAX_VALUE);
		io.writeLine(company.getEmployeesBySalary(salaryFrom, salaryTo));
	}

	private static void getEmployeesByDepartment(InputOutput io) {
		String dep = io.readStringOptions("Entere department", "Department should be",
				getDepartments());
		writeEmployees(company.getEmployeesByDepartment(dep), io);
	}

	private static void modifiedExit(InputOutput io) {
		if (company instanceof NetworkCompany) {
			try {
				((NetworkCompany) company).close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			io.writeLine("NetWork company close connection");
		} else if (company instanceof CompanyImpl) {
			saveToFile(io);
		}
	}

	private static Set<String> getDepartments() {
		Departments[] departments = Departments.values();
		Set<String> res = new HashSet<>();
		for (Departments dep : departments) {
			res.add(dep.toString());
		}
		return res;
	}
}
