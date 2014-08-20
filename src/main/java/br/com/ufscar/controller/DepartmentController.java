package br.com.ufscar.controller;

import java.util.List;

import br.com.ufscar.dao.DepartmentDAO;
import br.com.ufscar.entity.Department;

public class DepartmentController {
	private DepartmentDAO departmentDAO = new DepartmentDAO();
	
	public void persist(Department department) {
		departmentDAO.save(department);
	}
	
	public Department findById(Integer entityID) {
		return departmentDAO.find(Department.class, entityID);
	}
	
	public List<Department> findAll() {
		return departmentDAO.findAll(Department.class);
	}
	
	
}
