package br.com.ufscar.database;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

import br.com.ufscar.controller.DepartmentController;
import br.com.ufscar.controller.EmployeeController;
import br.com.ufscar.controller.UserController;
import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.entity.Department;
import br.com.ufscar.entity.Employee;
import br.com.ufscar.entity.Role;
import br.com.ufscar.entity.User;

public class DataBaseInitTest extends TestCase {

	DepartmentController departmentController = new DepartmentController();
	EmployeeController employeeController = new EmployeeController();
	UserController userController = new UserController();

	private final String DEPARTMENT = "department1";
	private final String EMPLOYEE = "employee1";
	private final String USER_ADMIN = "admin1";
	private final String USER_CLIENT = "userClient1";

	@Test
	public void testeDataBaseInit() {

		try {
			createEmployeesAndDepartament();
			createUserAdmin();
			createUserClient();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertTrue(verificaSeExisteDepartment());
		assertTrue(verificaSeExisteEmployee());
		assertTrue(verificaSeExisteUserAdmin());
		assertTrue(verificaSeExisteUserClient());
		
	}

	private void createEmployeesAndDepartament() {
		Department department = new Department(DEPARTMENT);
		if (!verificaSeExisteDepartment()) {
			departmentController.persist(department);
		}

		Employee employee = new Employee(EMPLOYEE, department);
		if (!verificaSeExisteEmployee()) {
			employeeController.persist(employee);
		}
	}

	private void createUserAdmin() {
		if (!verificaSeExisteUserAdmin()) {
			User user = new User();
			user.setEmail(USER_ADMIN+"@estoque.com");
			user.setName(USER_ADMIN);
			user.setPassword(USER_ADMIN);
			user.setRole(Role.ADMIN);
			userController.persist(user);
		}
	}

	private void createUserClient() {
		if (!verificaSeExisteUserClient()) {
			User user = new User();
			user.setEmail(USER_CLIENT+"@estoque.com");
			user.setName(USER_CLIENT);
			user.setPassword(USER_CLIENT);
			user.setRole(Role.USER);
			userController.persist(user);
		}
	}

	private Boolean verificaSeExisteEmployee() {
		GenericDAO<Employee> genericEmployeesDAO = new GenericDAO<>(
				Employee.class);

		String query = "SELECT c FROM " + Employee.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", EMPLOYEE);
		Integer tamanho = genericEmployeesDAO.findResults(query, parameters)
				.size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteDepartment() {
		GenericDAO<Department> genericDepartmentDAO = new GenericDAO<>(
				Department.class);

		String query = "SELECT c FROM " + Department.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", DEPARTMENT);
		Integer tamanho = genericDepartmentDAO.findResults(query, parameters)
				.size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteUserAdmin() {
		GenericDAO<User> genericUserDAO = new GenericDAO<>(User.class);

		String query = "SELECT c FROM " + User.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", USER_ADMIN);
		Integer tamanho = genericUserDAO.findResults(query, parameters).size();

		return tamanho > 0 ? true : false;
	}

	private Boolean verificaSeExisteUserClient() {
		GenericDAO<User> genericUserDAO = new GenericDAO<>(User.class);

		String query = "SELECT c FROM " + User.class.getSimpleName()
				+ " c WHERE c.name = :name";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("name", USER_CLIENT);
		Integer tamanho = genericUserDAO.findResults(query, parameters).size();

		return tamanho > 0 ? true : false;
	}

}
