package br.com.ufscar.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;


public class OrderDAO extends GenericDAO{
	
	public OrderDAO() {
		super();
	}
	
	public List<Orderr> listOrderByClient(User userClient) {
		String queryStr = "SELECT c FROM " + Orderr.class.getSimpleName() 
				+ " c WHERE c.userClient = :userClient";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userClient", userClient);
		
		return findResults(queryStr, parameters);
	}
	
}
