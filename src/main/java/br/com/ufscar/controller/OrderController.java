package br.com.ufscar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.OrderDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemOrder;
import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class OrderController {
	
	private GenericDAO dao;
	private OrderDAO orderDAO;
	private Map<Long, Integer> preOrdem;
	private ItemMovimentationController itemController;

	public OrderController() {
		dao = new GenericDAO();
		orderDAO = new OrderDAO();
		preOrdem = new HashMap<>();
		itemController = new ItemMovimentationController();
	}
	
	public void createOrder(User userAdmin, User userClient) throws QuantityNotAvailableException {
		if (!preOrdem.isEmpty()) {
			List<ItemOrder> itemOrders = new ArrayList<>();
			for (Long id : preOrdem.keySet()) {
				Item item = dao.find(Item.class, id);
				Integer quantity = preOrdem.get(id);
				ItemOrder itemOrder = new ItemOrder();
				itemOrder.setItem(item);
				itemOrder.setQuantity(quantity);
				itemOrders.add(itemOrder);
				itemController.saida(userAdmin, item, quantity);
			}
			
			Orderr order = new Orderr();
			order.setDate(new Date());
			order.setItems(new ArrayList<ItemOrder>());
			order.setItems(itemOrders);
			order.setUserAdmin(userAdmin);
			order.setUserClient(userClient);
			order.setAsPacking();
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
		return orderDAO.listOrderByClient(userClient);
	}
	
}
