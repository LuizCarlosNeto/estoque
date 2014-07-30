package br.com.ufscar.dao;

import br.com.ufscar.entity.Employee;

public class EmployeeDAO extends GenericDAO<Employee>{

	private static final long serialVersionUID = 1L;

	public EmployeeDAO() {
		super(Employee.class);
	}
	
}
