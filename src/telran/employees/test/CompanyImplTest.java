package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import telran.employees.CompanyImpl;

class CompanyImplTest extends CompanyTest{

	@BeforeEach
	@Override
	void setUp() throws Exception {
		company = new CompanyImpl();
		super.setUp();
	}

}
