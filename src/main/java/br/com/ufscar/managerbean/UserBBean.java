package br.com.ufscar.managerbean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ufscar.controller.UserController;
import br.com.ufscar.entity.User;


@SessionScoped
@ManagedBean(name="userBBean")
public class UserBBean implements Serializable {
	public static final String INJECTION_NAME = "#{userBBean}";

	private User user;
	UserController userController;
	
	public UserBBean() {
		super();
		this.init();
	}
	
	private void init() {
		user = new User();
		userController = new UserController();
	}
	
	public boolean isAdmin() {
		return user.isAdmin();
	}

	public boolean isDefaultUser() {
		return user.isUser();
	}

	public String logOut() {
		getSession().invalidate();
		return "/pages/public/login.xhtml";
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	private HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	public User getUserLogged() {
		try {
			return (User) getSession().getAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}