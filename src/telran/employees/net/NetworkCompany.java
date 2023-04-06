package telran.employees.net;

import java.io.Closeable;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import telran.employees.Company;
import telran.employees.Employee;
import telran.net.NetworkClient;

public class NetworkCompany implements Company, Closeable {

	private static final long serialVersionUID = 1L;
	private NetworkClient client;
	
	public NetworkCompany(NetworkClient client) {
		this.client = client;
	}

	@Override
	public boolean addEmployee(Employee empl) {
		return client.send("addEmployee", empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		return client.send("removeEmployee", id);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return client.send("getAllEmployees", 0);
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return client.send("getEmployeesByMonthBirth", month);
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return client.send("getEmployeesBySalary", new int[] {salaryFrom, salaryTo});
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return client.send("getEmployeesByDepartment", department);
	}

	@Override
	public Employee getEmployee(long id) {
		return client.send("getEmployee", id);
	}

	@Override
	public void save(String pathName) {
		client.send("save", pathName);
		
	}

	@Override
	public void restore(String path) {
		client.send("restore", path);
	}

	@Override
	public void close() throws IOException {
		client.close();
	}

	@Override
	public Iterator<Employee> iterator() {
		return getAllEmployees().iterator();
	}
}
