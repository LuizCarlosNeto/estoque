package br.com.ufscar.managerBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.controller.DepartmentController;
import br.com.ufscar.entity.Department;

@ManagedBean
@SessionScoped
public class UsuarioBBean {
	private DepartmentController departmentController;
	private String name;

	public UsuarioBBean() {
		this.departmentController = new DepartmentController();
	}
	
	public String saveDepartment() {
		Department department = new Department();
		department.setName(name);
		departmentController.persist(department);
		
		return "/usuario/usuarioEditar.xhtml";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
