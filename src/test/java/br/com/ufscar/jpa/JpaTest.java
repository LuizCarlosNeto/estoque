package br.com.ufscar.jpa;

import java.util.List;

import br.com.ufscar.controller.DepartmentController;
import br.com.ufscar.controller.EmployeeController;
import br.com.ufscar.controller.UserController;
import br.com.ufscar.entity.Department;
import br.com.ufscar.entity.Employee;
import br.com.ufscar.entity.Role;
import br.com.ufscar.entity.User;

public class JpaTest {
	
	DepartmentController departmentController = new DepartmentController();
	EmployeeController employeeController = new EmployeeController();
	UserController userController = new UserController();

	public JpaTest() {
		this.departmentController = new DepartmentController();
		this.employeeController = new EmployeeController();
		this.userController = new UserController();
	}

	public static void main(String[] args) {
		
		JpaTest test = new JpaTest();

		try {
			test.createEmployees();
			test.createUser();
		} catch (Exception e) {
			e.printStackTrace();
		}

		test.listEmployees();

		System.out.println(".. done");
	}

	private void createEmployees() {
		int numOfEmployees = employeeController.findAll().size();
		if (numOfEmployees == 0) {
			Department department = new Department("java");
			departmentController.persist(department);
			
			employeeController.persist(new Employee("Jakab Gipsz", department));
			employeeController.persist(new Employee("Captain Nemo", department));
		}
	}

	private void listEmployees() {
		List<Employee> resultList = employeeController.findAll();
		System.out.println("num of employess:" + resultList.size());
		for (Employee next : resultList) {
			System.out.println("next employee: " + next);
		}
	}
	
	private void createUser() {
		User user = new User();
		user.setEmail("a@a.com");
		user.setName("admin");
		user.setPassword("admin");
		user.setRole(Role.ADMIN);
		
		userController.persist(user);
	}
}
