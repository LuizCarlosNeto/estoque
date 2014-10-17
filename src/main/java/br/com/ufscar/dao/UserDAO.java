package br.com.ufscar.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.ufscar.entity.User;

public class UserDAO extends GenericDAO{

	public UserDAO() {
		super();
	}

	public User findUserByEmail(String email) {
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("email", email);     
 
        return super.findOneResult(User.FIND_BY_EMAIL, parameters);
	}
	
}
