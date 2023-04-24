package telran.employees;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class CompanyImpl implements Company {

	private static final long serialVersionUID = 1L;

	HashMap<Long, Employee> employees;
	HashMap<Integer, Set<Employee>> employeesBirth;
	TreeMap<Integer, Set<Employee>> employeesSalary;
	HashMap<String, Set<Employee>> employeesDepartment;

	private static enum LockType {
		READ, WRITE
	}

	private static final int N_RESOURCES = 4;
	private static final int EMPLOYEES_INDEX = 0;
	private static final int EMPLOYEES_DEPARTMENT_INDEX = 1;
	private static final int EMPLOYEES_MONTH_INDEX = 2;
	private static final int EMPLOYEES_SALARY_INDEX = 3;

	private ReentrantReadWriteLock[] rwLocks = IntStream.range(0, N_RESOURCES)
			.mapToObj(i -> new ReentrantReadWriteLock()).toArray(ReentrantReadWriteLock[]::new);

	private Lock[] readLocks = Arrays.stream(rwLocks).map(ReentrantReadWriteLock::readLock)
			.toArray(Lock[]::new);
	private Lock[] writeLocks = Arrays.stream(rwLocks).map(ReentrantReadWriteLock::writeLock)
			.toArray(Lock[]::new);

	private void lock(LockType type, int... indexes) {
		Lock[] locks = type == LockType.READ ? readLocks : writeLocks;
		Arrays.stream(indexes).sorted().forEach(i -> locks[i].lock());
	}

	private void unlock(LockType type, int... indexes) {
		Lock[] locks = type == LockType.READ ? readLocks : writeLocks;
		Arrays.stream(indexes).sorted().forEach(i -> locks[i].unlock());
	}

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
		int[] indexes = IntStream.range(0, N_RESOURCES).toArray();
		lock(LockType.WRITE, indexes);
		try {
			boolean res = false;
			if (!employees.containsKey(empl.id)) {
				res = true;
				employees.put(empl.id, empl);
				putEmplToMap(empl, employeesBirth, empl.getBirthDate().getMonthValue());
				putEmplToMap(empl, employeesSalary, empl.getSalary());
				putEmplToMap(empl, employeesDepartment, empl.getDepartment());
			}
			return res;
		} finally {
			unlock(LockType.WRITE, indexes);
		}
	}

	private <T> void putEmplToMap(Employee empl, Map<T, Set<Employee>> map, T key) {
		map.computeIfAbsent(key, k -> new HashSet<>()).add(empl);
	}

	@Override
	public Employee removeEmployee(long id) {
		int[] indexes = IntStream.range(0, N_RESOURCES).toArray();
		lock(LockType.WRITE, indexes);
		try {
			Employee res = employees.remove(id);
			if (res != null) {
				removeEmplFromMap(res.getBirthDate().getMonthValue(), employeesBirth);
				removeEmplFromMap(res.getDepartment(), employeesDepartment);
				removeEmplFromMap(res.getSalary(), employeesSalary);
			}
			return res;
		} finally {
			unlock(LockType.WRITE, indexes);
		}
	}

	private <T> void removeEmplFromMap(T key, Map<T, Set<Employee>> map) {
		map.remove(key);
	}

	@Override
	public List<Employee> getAllEmployees() {
		lock(LockType.READ, EMPLOYEES_INDEX);
		try {
			return new ArrayList<>(employees.values());
		} finally {
			unlock(LockType.READ, EMPLOYEES_INDEX);
		}
	}

	@Override
	public List<Employee> getEmployeesByMonthBirth(int month) {
		lock(LockType.READ, EMPLOYEES_MONTH_INDEX);
		try {
			return new ArrayList<Employee>(
					employeesBirth.getOrDefault(month, Collections.emptySet()));
		} finally {
			unlock(LockType.READ, EMPLOYEES_MONTH_INDEX);
		}
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		lock(LockType.READ, EMPLOYEES_SALARY_INDEX);
		try {
			return employeesSalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
					.flatMap(Set::stream).toList();
		} finally {
			unlock(LockType.READ, EMPLOYEES_SALARY_INDEX);
		}
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		lock(LockType.READ, EMPLOYEES_DEPARTMENT_INDEX);
		try {
			return new ArrayList<>(
					employeesDepartment.getOrDefault(department, Collections.emptySet()));
		} finally {
			unlock(LockType.READ, EMPLOYEES_DEPARTMENT_INDEX);
		}
	}

	@Override
	public Employee getEmployee(long id) {
		lock(LockType.READ, EMPLOYEES_INDEX);
		try {
			return employees.get(id);
		} finally {
			unlock(LockType.READ, EMPLOYEES_INDEX);
		}
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

	@Override
	public void updateSalary(long emplId, int newSalary) {
		lock(LockType.READ, EMPLOYEES_INDEX);
		lock(LockType.WRITE, EMPLOYEES_SALARY_INDEX);
		try {
			Employee empl = employees.get(emplId);
			if (empl != null) {
			removeEmplFromMap(empl.salary, employeesSalary);
			empl.salary = newSalary;
			putEmplToMap(empl, employeesSalary, empl.salary);
			}
		} finally {
			unlock(LockType.READ, EMPLOYEES_INDEX);
			unlock(LockType.WRITE, EMPLOYEES_SALARY_INDEX);
		} 
	}

	@Override
	public void updateDepartment(long emplId, String newDepartment) {
		lock(LockType.READ, EMPLOYEES_INDEX);
		lock(LockType.WRITE, EMPLOYEES_DEPARTMENT_INDEX);
		try {
			Employee empl = employees.get(emplId);
			if (empl != null) {
				removeEmplFromMap(empl.department, employeesDepartment);
				empl.department = newDepartment;
				putEmplToMap(empl, employeesDepartment, empl.department);
			}
		} finally {
			unlock(LockType.READ, EMPLOYEES_INDEX);
			unlock(LockType.WRITE, EMPLOYEES_DEPARTMENT_INDEX);
		}
	}
}
