package telran.employees;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CompanyImpl implements Company {

	private static final long serialVersionUID = 1L;

	HashMap<Long, Employee> employees;
	HashMap<Integer, Set<Employee>> employeesBirth;
	TreeMap<Integer, Set<Employee>> employeesSalary;
	HashMap<String, Set<Employee>> employeesDepartment;

	public CompanyImpl() {
		super();
		this.employees = new HashMap<>();
		this.employeesBirth = new HashMap<>();
		this.employeesSalary = new TreeMap<>();
		this.employeesDepartment = new HashMap<String, Set<Employee>>();
	}

	@Override
	public Iterator<Employee> iterator() {
		return getAllEmployees().iterator();
	}

	@Override
	public boolean addEmployee(Employee empl) {
		boolean res = false;
		if (!employees.containsKey(empl.id)) {
			res = true;
			employees.put(empl.id, empl);
			putEmplToMap(empl, employeesBirth, empl.getBirthDate().getMonthValue());
			putEmplToMap(empl, employeesSalary, empl.getSalary());
			putEmplToMap(empl, employeesDepartment, empl.getDepartment());
		}
		return res;
	}

	private <T> void putEmplToMap(Employee empl, Map<T, Set<Employee>> map, T key) {
		map.computeIfAbsent(key, k -> new HashSet<>()).add(empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		Employee res = employees.remove(id);
		if (res != null) {
			removeEmplFromMap(res.getBirthDate().getMonthValue(), employeesBirth);
			removeEmplFromMap(res.getDepartment(), employeesDepartment);
			removeEmplFromMap(res.getSalary(), employeesSalary);
		}
		return res;
	}

	private <T> void removeEmplFromMap(T key, Map<T, Set<Employee>> map) {
		map.remove(key);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return new ArrayList<Employee>(employees.values());
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return new ArrayList<Employee>(employeesBirth.getOrDefault(month, Collections.emptySet()));
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		return employeesSalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
				.flatMap(Set::stream).toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return new ArrayList<>(
				employeesDepartment.getOrDefault(department, Collections.emptySet()));
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	@Override
	public void save(String pathName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pathName))) {
			output.writeObject(getAllEmployees());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void restore(String path) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
			List<Employee> allEmployees = (List<Employee>) input.readObject();
			allEmployees.forEach(this::addEmployee);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void readObject(ObjectInputStream input) throws ClassNotFoundException, IOException {
		input.defaultReadObject();
	}

}
