package telran.employees;

import java.io.Serializable;

import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class ComponyProtocol implements Protocol {
	private Company company = new CompanyImpl();

	@Override
	public Response getResponse(Request request) {
		Response response = switch (request.type) {
		case "addEmployee" -> addEmployee(request.data);
		case "removeEmployee" -> removeEmployee(request.data);
		case "getAllEmployees" -> getAllEmployees(request.data);
		case "getEmployeesByMonthBirth" -> getEmployeesByMonthBirth(request.data);
		case "getEmployeesBySalary" -> getEmployeesBySalary(request.data);
		case "getEmployeesByDepartment" -> getEmployeesByDepartment(request.data);
		case "save" -> save(request.data);
		case "restore" -> restore(request.data);
		case "getEmployee" -> getEmployee(request.data);
		default -> throw new IllegalArgumentException("Unexpected value: " + request.type);
		};
		return response;
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
		return new Response(ResponseCode.OK, company.removeemployee((long) data));
	}

	private Response addEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.addEmployee((Employee)data));
	}

}
