package br.com.ufscar.managerBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class UsuarioBBean {
	 
	private String name;
 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
