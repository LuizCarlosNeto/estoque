package br.com.ufscar.controller;

import br.com.ufscar.dao.DepartmentDAO;
import br.com.ufscar.entity.Department;

public class DepartmentController {
	private DepartmentDAO departmentDAO = new DepartmentDAO();
	
	public void persist(Department department) {
		departmentDAO.save(department);
	}
	
}
