package br.com.ufscar.managerBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ufscar.managerBean.UserMB;
import br.com.ufscar.controller.UserController;
import br.com.ufscar.entity.User;

@RequestScoped
@ManagedBean
public class LoginMB extends AbstractMB {
	@ManagedProperty(value = UserMB.INJECTION_NAME)
	private UserMB userMB;

	private String email;
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String login() {
		UserController userFacade = new UserController();

		User user = userFacade.isValidLogin(email, password);
		
		if(user != null){
			userMB.setUser(user);
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.getSession().setAttribute("user", user);
			return "/pages/protected/index.xhtml";
		}

		displayErrorMessageToUser("Check your email/password");
		
		return null;
	}

	public void setUserMB(UserMB userMB) {
		this.userMB = userMB;
	}	
}