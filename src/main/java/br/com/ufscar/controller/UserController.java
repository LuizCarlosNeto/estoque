package br.com.ufscar.controller;

import java.util.List;

import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.User;

public class UserController {
	private UserDAO userDAO = new UserDAO();

	public List<User> findAll() {
		return userDAO.findAll(User.class);
	}

	public void persist(User user) {
		if (user.getId() == null) {
			userDAO.save(user);
		}else {
			userDAO.update(user);
		}
	}
	

	public User isValidLogin(String email, String password) {
		User user = userDAO.findUserByEmail(email);

		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}

		return user;
	}
	
}
