package br.com.ufscar.managerBean;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.entity.Department;

@ManagedBean
@SessionScoped
public class DepartamentoCadastroBBean {
	private GenericDAO genericDAO;
	private Department department;

	public DepartamentoCadastroBBean() {
		init();
	}
	
	private void init() {
		genericDAO = new GenericDAO();
		department = new Department();
	}
	
	public String saveDepartment() {
		if(department.getId() != null){
			department = genericDAO.find(Department.class, department.getId());
		}
		genericDAO.save(department);
		init();
		return "/pages/protected/admin/departamento/departamento-editar.xhtml";
	}
	
	public List<Department> getListDepartments() {
		return genericDAO.findAll(Department.class);
	}
	
	public String removeDepartment() {
		genericDAO.delete(department.getId(), Department.class);
		return "";
	}
	
	public String editDepartment() {
		return "/pages/protected/admin/departamento/departamento-novo.xhtml";
	}
	
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
}
