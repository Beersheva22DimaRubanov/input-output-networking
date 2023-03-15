package telran.employees;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CompanyImpl implements Company {

	private static final long serialVersionUID = 1L;

	HashMap<Long, Employee> employees;
	HashMap<Integer, LinkedList<Employee>> employeesBirth;
	TreeMap<Integer, LinkedList<Employee>> employeesSalary;
	HashMap<String, LinkedList<Employee>> employeesDepartment;

	public CompanyImpl() {
		super();
		this.employees = new HashMap<>();
		this.employeesBirth = new HashMap<>();
		this.employeesSalary = new TreeMap<>();
		this.employeesDepartment = new HashMap<String, LinkedList<Employee>>();
	}

	@Override
	public Iterator<Employee> iterator() {
		return employees.values().iterator();
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

	private <T> void putEmplToMap(Employee empl, Map<T, LinkedList<Employee>> map, T key) {
		LinkedList<Employee> list = map.get(key);
		if (list == null) {
			list = new LinkedList<>();
			map.put(key, list);
		}
		list.add(empl);
	}

	@Override
	public Employee removeemployee(long id) {
		Employee res = employees.remove(id);
		if (res != null) {
			removeEmplFromMap(res.getBirthDate().getMonthValue(), employeesBirth);
			removeEmplFromMap(res.getDepartment(), employeesDepartment);
			removeEmplFromMap(res.getSalary(), employeesSalary);
		}
		return res;
	}

	private <T> void removeEmplFromMap(T key, Map<T, LinkedList<Employee>> map) {
		map.remove(key);
	}

	@Override
	public List<Employee> getAllEmployees() {
		return employees.values().stream().toList();
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		return employeesBirth.get(month);
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		ArrayList<Employee> res = new ArrayList<Employee>();
		employeesSalary.subMap(salaryFrom, salaryTo).values().forEach(x -> res.addAll(x));
		return res;
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		return employeesDepartment.get(department);
	}

	@Override
	public Employee getEmployee(long id) {
		return employees.get(id);
	}

	@Override
	public void save(String pathName) {
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(pathName))) {
			output.writeObject(employees);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void writeObject(ObjectOutputStream output) throws IOException {
		output.defaultWriteObject();
	}

	@Override
	public void restore(String path) {
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
			input.readObject();
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
