package br.com.ufscar.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Department {
	@Id
	@GeneratedValue
	private Long id;
	
	private String name;
	 
	@OneToMany(mappedBy="department",cascade=CascadeType.PERSIST)
	private List<User> employees = new ArrayList<User>();
	 
	public Department() {
	super();
	}
	public Department(String name) {
	this.name = name;
	}
	public Long getId() {
	return id;
	}
	public void setId(Long id) {
	this.id = id;
	}
	public String getName() {
	return name;
	}
	public void setName(String name) {
	this.name = name;
	}
	public List<User> getEmployees() {
	return employees;
	}
	public void setEmployees(List<User> employees) {
	this.employees = employees;
	}
}
