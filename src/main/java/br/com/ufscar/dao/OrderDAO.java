package br.com.ufscar.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;
import br.com.ufscar.enums.OrderStatus;
import br.com.ufscar.enums.OrderType;


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
	
	public List<Orderr> listOrderByAdmin(User userAdmin) {
		String queryStr = "SELECT c FROM " + Orderr.class.getSimpleName() 
				+ " c WHERE c.userAdmin = :userAdmin";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userAdmin", userAdmin);
		
		return findResults(queryStr, parameters);
	}

	public List<Orderr> listOrdersToVerify() {
		String queryStr = "SELECT c FROM " + Orderr.class.getSimpleName() 
				+ " c WHERE c.status = :status"
				+ " AND c.type = :type";
				
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("status", OrderStatus.SENT);
				parameters.put("type", OrderType.CLIENT);
		
		return findResults(queryStr, parameters);
	}
	
	public List<Orderr> listOrdersToVerifyStock() {
		String queryStr = "SELECT c FROM " + Orderr.class.getSimpleName() 
				+ " c WHERE c.status = :status"
				+ " AND c.type = :type";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("status", OrderStatus.SENT);
		parameters.put("type", OrderType.STOCK);
		
		return findResults(queryStr, parameters);
	}
	
	public List<Orderr> listAllOrdersClient() {
		String queryStr = "SELECT c FROM " + Orderr.class.getSimpleName() 
				+ " c WHERE c.type = :type";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("type", OrderType.CLIENT);
		
		return findResults(queryStr, parameters);
	}
	
	public List<Orderr> listAllOrdersStock() {
		String queryStr = "SELECT c FROM " + Orderr.class.getSimpleName() 
				+ " c WHERE c.type = :type";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("type", OrderType.STOCK);
		
		return findResults(queryStr, parameters);
	}
	
}
