package telran.employees;

import java.io.Serializable;
import java.time.LocalDate;

public class Employee implements Serializable, Comparable<Employee>  {

	private static final long serialVersionUID = 1L;
	
	long id;
	String name;
	LocalDate birthDate;
	String department;
	int salary;
	
	public Employee(long id, String name, LocalDate birthDate, String department, int salary) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.department = department;
		this.salary = salary;
	}

	public String getDepartment() {
		return department;
	}

	public int getSalary() {
		return salary;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return id == other.id;
	}
	@Override
	public int compareTo(Employee o) {
		return Long.compare(id, o.id);
	}

	@Override
	public String toString() {
		return "Employee id=" + id + ", name=" + name + ", birthDate=" + birthDate
				+ ", department=" + department + ", salary=" + salary + " \n";
	}
	
	
}
