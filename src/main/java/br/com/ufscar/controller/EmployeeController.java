package br.com.ufscar.controller;

import java.util.List;

import br.com.ufscar.dao.EmployeeDAO;
import br.com.ufscar.entity.Employee;

public class EmployeeController {
	private EmployeeDAO employeeDAO = new EmployeeDAO();

	public List<Employee> findAll() {
		return employeeDAO.findAll(Employee.class);
	}

	public void persist(Employee employee) {
		employeeDAO.save(employee);
	}
	
}
