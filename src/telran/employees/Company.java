package telran.employees;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

public interface Company extends Iterable<Employee>, Serializable {
	boolean addEmployee(Employee empl);//return true if added(no two emp with the same Id)
	Employee removeEmployee(long id);//return reference to removed Employee or null
	List<Employee> getAllEmployees();
	List<Employee> getEmployeesByMonthBirth(int month);//employees born at a given month
	List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo);// emp with salary in a given
	List<Employee> getEmployeesByDepartment(String department);
	Employee getEmployee(long id);
	void save(String pathName); //save all employee objects
	void restore(String path); //restore all employee objects
	void updateSalary(long emplId, int newSalary);
	void updateDepartment(long emplId, String newDepartment);
}
