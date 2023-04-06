package telran.employees.net;

import java.io.Serializable;
import java.lang.reflect.Method;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.employees.Employee;
import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements Protocol {
	private Company company = new CompanyImpl();
	
	public CompanyProtocol(Company company) {
		this.company = company;
	}

	private Response getEmployeesBySalary(Serializable data) {
		int[] salary = (int[])data;
		return new Response(ResponseCode.OK, (Serializable)company.getEmployeesBySalary(salary[0], salary[1]));
	}

	private Response getEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.getEmployee((long) data));
	}

	private Response restore(Serializable data) {
		company.restore(data.toString());
		return new Response(ResponseCode.OK, "Restored");
	}

	private Response save(Serializable data) {
		company.save(data.toString());
		return new Response(ResponseCode.OK, "Saved");
	}

	private Response getEmployeesByDepartment(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable)company.getEmployeesByDepartment(data.toString()));
	}

	private Response getEmployeesByMonthBirth(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable)company.getEmployeesByMonthBirth((int)data));
	}

	private Response getAllEmployees(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable)company.getAllEmployees());
	}

	private Response removeEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.removeEmployee((long) data));
	}

	private Response addEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.addEmployee((Employee)data));
	}

	@Override
	public Response getResponse(Request request) {
		try {
		Method	method = CompanyProtocol.class.getDeclaredMethod(request.type, Serializable.class);
		method.setAccessible(true);
		return (Response) method.invoke(this, request.data);
		} catch (NoSuchMethodException e) {
			return new Response(ResponseCode.WRONG_REQUEST, e.toString());
		} catch (Exception e) {
			return new Response(ResponseCode.WRONG_DATA, e.toString());
		}
		
	}

}
