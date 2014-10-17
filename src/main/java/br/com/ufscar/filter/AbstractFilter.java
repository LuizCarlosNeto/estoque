package br.com.ufscar.filter;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

public class AbstractFilter {

	public AbstractFilter() {
		super();
	}

	protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
		RequestDispatcher rd = req.getRequestDispatcher("/pages/public/login.xhtml");
		rd.forward(request, response);
	}

	protected void accessDenied(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setStatus(HttpStatus.SC_UNAUTHORIZED);
		RequestDispatcher rd = req.getRequestDispatcher("/pages/public/accessDenied.xhtml");
		rd.forward(req, resp);
		
	}
}