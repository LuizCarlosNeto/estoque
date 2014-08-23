package br.com.ufscar.managerBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.entity.Department;
import br.com.ufscar.entity.Role;
import br.com.ufscar.entity.User;


@SessionScoped
@ManagedBean
public class UsuarioCadastroBBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private User user;
	private List<String> tipos;
	private Map<String,Long> departments;
	private GenericDAO dao;
	private Long departmentSelectedId;
	
	public UsuarioCadastroBBean() {
		super();
		this.init();
	}
	
	private void init() {
		dao = new GenericDAO();
		user = new User();
		tipos = this.listarTiposUsuario();
		departments = this.listarDepartaments();
		departmentSelectedId = null;
	}
	
	private void reset() {
		this.init();
	}
	
	public String saveUser() {
		user.setDepartment(this.findDepartmentById(departmentSelectedId));
		dao.save(user);
		this.reset();
		return "/pages/protected/admin/usuario/usuario-editar.xhtml";
	}
	
	private Department findDepartmentById(Object entityID) {
		return dao.find(Department.class, entityID);
	}
	
	private List<String> listarTiposUsuario() {
		List<String> roles = new ArrayList<>();
		for (int i = 0; i < Role.values().length; i++) {
			roles.add(Role.values()[i].toString());
		}
		return roles;
	}
		
	private Map<String,Long> listarDepartaments() {
		Map<String,Long> departmentsHash = new HashMap<String, Long>();
		for (Department department : dao.findAll(Department.class)) {
			departmentsHash.put(department.getName(), department.getId());
		}
		return departmentsHash;
	}
	
	public List<User> getListUsers() {
		return dao.findAll(User.class);
	}
	
	public String removeUser() {
		dao.delete(user.getId(), User.class);
		return "";
	}
	
	public String editUser() {
		return "/pages/protected/admin/usuario/usuario-novo.xhtml";
	}
	
	public String mostrarNovoUsuario() {
		this.reset();
		return "/pages/protected/admin/usuario/usuario-novo.xhtml";
	}
	
	public String mostrarEditarUsuario() {
		this.reset();
		return "/pages/protected/admin/usuario/usuario-editar.xhtml";
	}

	
	//getters and setters 
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<String> getTipos() {
		return tipos;
	}

	public void setTipos(List<String> tipos) {
		this.tipos = tipos;
	}

	public Map<String, Long> getDepartments() {
		return departments;
	}

	public void setDepartment(Map<String, Long> departments) {
		this.departments = departments;
	}

	public Long getDepartmentSelectedId() {
		return departmentSelectedId;
	}

	public void setDepartmentSelectedId(Long departmentSelectedId) {
		this.departmentSelectedId = departmentSelectedId;
	}
	
}