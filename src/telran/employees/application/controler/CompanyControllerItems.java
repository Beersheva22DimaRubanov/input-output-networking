package telran.employees.application.controler;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import telran.employees.Company;
import telran.employees.Employee;
import telran.view.InputOutput;
import telran.view.Item;
import telran.view.Menu;

public class CompanyControllerItems {

	private static Company company;
	private static HashSet<String> departments;

	private CompanyControllerItems() {

	}

	public static Item[] getCompanyItems(Company company, String[] departments) {
		CompanyControllerItems.company = company;
		CompanyControllerItems.departments = new HashSet<>(Arrays.asList(departments));
		return getItems();
	}

	private static Item[] getItems() {
		return new Item[] { getUserItemMenu(), getAdminItemMenu() };
	}

	private static Item getUserItemMenu() {
		return new Menu("User menu",
				new Item[] { Item.of("Get employee by id", t -> getEmployee(t)),
						Item.of("Get all employees", t -> getAllEmployees(t)),
						Item.of("Get employee by month birth", t -> getEmployeeByMonthBirth(t)),
						Item.of("Get employee by salary", t -> getEmployeesBySalary(t)),
						Item.of("Get employee by department", t -> getEmployeesByDepartment(t)),
						Item.exit() });
	}

	private static Item getAdminItemMenu() {
		Menu menu = new Menu("Admin menu", Item.of("Add employee", t -> addEmployee(t)),
				Item.of("Remove employee", t -> removeEmployee(t)), Item.exit());
		return menu;
	}

	private static void removeEmployee(InputOutput io) {
		Long id = getId(io, true);
		Employee employee = company.removeEmployee(id);
		if (employee != null) {
			io.writeLine("Delete employee: " + employee.getName() + " from: "
					+ employee.getDepartment());
		} else {
			io.writeLine("No employee with such ID");
		}
	}

	private static void addEmployee(InputOutput io) {
		Long id = getId(io, false);
		if (id == null) {
			io.writeLine(String.format("Employee with id %s already exists", id));
		} else {
			String name = io.readString("Enter empl name");
			LocalDate birthDate = io.readDateISO("Enter birth date(yyyy-MM-dd)", "Wrong date");
			String department = getDepartment(io);
			int salary = io.readInt("Enter salary", "Wrong number", 1, Integer.MAX_VALUE);
			Employee employee = new Employee(id, name, birthDate, department, salary);
			io.writeLine(
					company.addEmployee(employee) ? "Employee added" : "Employee already exsist");
		}
	}

	private static String getDepartment(InputOutput io) {
		String department = io.readStringOptions("Enter department ", "Wrong department",
				departments);
		return department;
	}

	private static Long getId(InputOutput io, boolean exist) {
		long id = io.readLong("Enter Employee ID", "Wrong Id", 1, Long.MAX_VALUE);
		Employee empl = company.getEmployee(id);
		return (empl == null && !exist) || (empl != null && exist) ? id : null;
	}

	private static void getEmployee(InputOutput io) {
		int id = io.readInt("Entere employee id", "Wrong number", 1,
				company.getAllEmployees().size());
		Employee empl = company.getEmployee(id);
		if (company == null) {
			io.writeLine("No employee with such id");
		} else {
			io.writeLine(empl.toString());
		}
	}

	private static void getAllEmployees(InputOutput io) {
		List<Employee> employees = company.getAllEmployees();
		if (employees.isEmpty()) {
			io.writeLine("There are no employees");
		} else {
			io.writeLine(employees.toString());
		}
	}

	private static void getEmployeeByMonthBirth(InputOutput io) {
		int monthNum = io.readInt("Entere employee month of birth", "Wrong number", 1, 12);
		List<Employee> employees = company.getEmployeesByMonthBirth(monthNum);
		if (employees.isEmpty()) {
			io.writeLine("No employee with such date of birth");
		} else {
			io.writeLine(employees);
		}
	}

	private static void getEmployeesBySalary(InputOutput io) {
		int salaryFrom = io.readInt("Entere salary from", "Wrong number", 1, Integer.MAX_VALUE);
		int salaryTo = io.readInt("Entere salary to", "Wrong number", 1, Integer.MAX_VALUE);
		List<Employee> employees = company.getEmployeesBySalary(salaryFrom, salaryTo);
		if (employees.isEmpty()) {
			io.writeLine("No employee with such salary");
		} else {
			io.writeLine(employees);
		}
	}

	private static void getEmployeesByDepartment(InputOutput io) {
		String dep = io.readStringOptions("Entere department", "Department should be", departments);
		List<Employee> employees = company.getEmployeesByDepartment(dep);
		if (employees.isEmpty()) {
			io.writeLine("No employees in this department");
		} else {
			io.writeLine(employees);
		}
	}
}
