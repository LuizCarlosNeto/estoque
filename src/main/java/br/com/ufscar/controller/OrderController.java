package br.com.ufscar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.OrderDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemOrder;
import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class OrderController {
	
	private GenericDAO dao;
	private OrderDAO orderDAO;
	private UserDAO userDAO;
	private Map<Long, Integer> preOrdem;
	private ItemMovimentationController itemController;

	public OrderController() {
		dao = new GenericDAO();
		orderDAO = new OrderDAO();
		preOrdem = new HashMap<>();
		itemController = new ItemMovimentationController();
		userDAO = new UserDAO();
	}
	
	public void verifyOrder(Orderr order, User userAdmin) {
		if (order != null && order.getItems() != null) {
			for (ItemOrder itemOrder : order.getItems()) {
				Item item = dao.find(Item.class, itemOrder.getId());
				try {
					itemController.saida(userAdmin, item, itemOrder.getQuantity());
				} catch (QuantityNotAvailableException e) {
					e.printStackTrace();
					order.setUserAdmin(userAdmin);
					order.setAsRejected();
					dao.update(order);
					return;
				}
			}
			
			order.setUserAdmin(userAdmin);
			order.setAsPacking();
			dao.update(order);
		}
		
	}
	
	public void requireOrder(User userClient) {
		if (!preOrdem.isEmpty()) {
			List<ItemOrder> itemOrders = new ArrayList<>();
			for (Long id : preOrdem.keySet()) {
				Item item = dao.find(Item.class, id);
				Integer quantity = preOrdem.get(id);
				ItemOrder itemOrder = new ItemOrder();
				itemOrder.setItem(item);
				itemOrder.setQuantity(quantity);
				itemOrders.add(itemOrder);
			}
			
			Orderr order = new Orderr();
			order.setDate(new Date());
			order.setItems(new ArrayList<ItemOrder>());
			order.setItems(itemOrders);
			order.setUserClient(userClient);
			order.setAsSent();
			dao.save(order);
		}
		
	}
	
	public void closeOrder(Orderr order) {
		order.setAsDelivered();
	}

	public void includeItem(Item item, Integer quantity) {
		preOrdem.put(item.getId(), quantity);
	}
	
	public List<Orderr> ordersByClient(User userClient) {
		return orderDAO.listOrderByClient(userDAO.findUserByEmail(userClient.getEmail()));
	}
	
}
