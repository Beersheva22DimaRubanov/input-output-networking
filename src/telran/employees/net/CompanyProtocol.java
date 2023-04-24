package telran.employees.net;

import java.io.Serializable;
import java.lang.reflect.Method;

import telran.employees.Company;
import telran.employees.CompanyImpl;
import telran.employees.Employee;
import telran.employees.PairId;
import telran.net.Protocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements Protocol {
	private Company company;
	
	public CompanyProtocol(Company company) {
		this.company = company;
	}

	Serializable getEmployeesBySalary(Serializable data) {
		int[] salary = (int[])data;
		return new Response(ResponseCode.OK, (Serializable)company.getEmployeesBySalary(salary[0], salary[1]));
	}

	Serializable getEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.getEmployee((long) data));
	}

	Serializable restore(Serializable data) {
		company.restore(data.toString());
		return new Response(ResponseCode.OK, "Restored");
	}

	Serializable save(Serializable data) {
		company.save(data.toString());
		return new Response(ResponseCode.OK, "Saved");
	}

	Serializable getEmployeesByDepartment(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable)company.getEmployeesByDepartment(data.toString()));
	}

	Serializable getEmployeesByMonthBirth(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable)company.getEmployeesByMonthBirth((int)data));
	}

	Serializable getAllEmployees(Serializable data) {
		return new Response(ResponseCode.OK, (Serializable)company.getAllEmployees());
	}

	Serializable removeEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.removeEmployee((long) data));
	}

	Serializable addEmployee(Serializable data) {
		return new Response(ResponseCode.OK, company.addEmployee((Employee)data));
	}
	
	Serializable updateSalary(Serializable data) {
		@SuppressWarnings("unchecked")
		PairId<Integer> idSalary = (PairId<Integer>) data;
		company.updateSalary(idSalary.id(), idSalary.value());
		return new Response(ResponseCode.OK, data);
	}
	Serializable updateDepartment(Serializable data) {
		@SuppressWarnings("unchecked")
		PairId<String> idDepartment = (PairId<String>) data;
		company.updateDepartment(idDepartment.id(), idDepartment.value());
		return new Response(ResponseCode.OK, data);
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
