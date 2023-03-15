package telran.employees.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.CompanyImpl;
import telran.employees.Employee;

class EmployeesTest {
	CompanyImpl company;
	Employee empl1 = new Employee(0, "Dima", LocalDate.of(1992, Month.AUGUST, 21), "Dev", 231);
	Employee empl2 = new Employee(1, "Vasya", LocalDate.of(1994, Month.DECEMBER, 12), "Dev", 123);
	Employee empl3 = new Employee(2, "Petya", LocalDate.of(1995, Month.AUGUST, 23), "QA", 215);
	Employee empl4 = new Employee(3, "Yura", LocalDate.of(1990, Month.FEBRUARY, 1), "QA", 122);
	Employee empl5 = new Employee(4, "Vitya", LocalDate.of(1967, Month.DECEMBER, 21), "QA", 125);
	Employee empl6 = new Employee(5, "Dan", LocalDate.of(1978, Month.SEPTEMBER, 3), "BA", 500);
	Employee empl7 = new Employee(6, "Dana", LocalDate.of(1990, Month.SEPTEMBER, 25), "BA", 342);
	Employee empl8 = new Employee(7, "Rita", LocalDate.of(1992, Month.JULY, 21), "Dev", 1231);
	private String filePath = "Employees.data";
	@BeforeEach
	void setUp() throws Exception {
		company = new CompanyImpl();
		company.addEmployee(empl1);
		company.addEmployee(empl2);
		company.addEmployee(empl3);
		company.addEmployee(empl4);
		company.addEmployee(empl5);
		company.addEmployee(empl6);
		company.addEmployee(empl7);
		company.addEmployee(empl8);
	}

	@Test
	void addTest() {
		assertFalse(company.addEmployee(empl1));
	}
	
	@Test
	void getEmployee() {
		assertEquals("Dima", company.getEmployee(0).getName());
		assertEquals("Dev", company.getEmployee(7).getDepartment());
	}
	
	@Test
	void writeTest() {
		company.save(filePath );
		company.restore(filePath);
		company.forEach(x -> System.out.println(x.getName()));
	}
	
	@Test
	void getAllEmployees() {
		List<Employee> list = company.getAllEmployees();
		Employee[] arr = {empl1, empl2, empl3, empl4, empl5, empl6, empl7, empl8};
		assertEquals(8, list.size());
		assertEquals(list.get(2).getName(), "Petya");
		assertArrayEquals(arr, list.toArray());
	}
	
	@Test
	void getEmpBirth() {
		List<Employee> august = company.getEmployeesByMonthBirth(8);
		Employee[] arr = {empl1, empl3};
		assertArrayEquals(arr, august.toArray());
	}
	
	@Test
	void getDepartmentEmp() {
		List<Employee> depEmp = company.getEmployeesByDepartment("Dev");
		Employee[] arr = {empl1, empl2, empl8};
		assertArrayEquals(arr, depEmp.toArray());
	}
	
	@Test
	void getSallaryEmp() {
		List<Employee> salaryEmp = company.getEmployeesBySalary(120, 220);
		Employee[] arr = {empl4, empl2, empl5, empl3};
		assertArrayEquals(arr, salaryEmp.toArray());
	}
	
	@Test
	void removeTest(){
		Employee res = company.removeemployee(3);
		assertEquals(empl4, res);
		assertNull(company.removeemployee(9));
	}

}
