package br.com.ufscar.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import br.com.ufscar.entity.User;

public class UserSessionUtil {

	public static User getUserFromSession() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		return (User)request.getSession().getAttribute("user");
	}
}
