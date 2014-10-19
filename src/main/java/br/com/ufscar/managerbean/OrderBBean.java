package br.com.ufscar.managerbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.com.ufscar.controller.OrderController;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;


@SessionScoped
@ManagedBean
public class OrderBBean implements Serializable {

	OrderController orderController;
	UserDAO dao;
	
	private Item item;
	
	private Map<Long, Item> itemsSelected;

	public OrderBBean() {
		super();
		this.init();
	}
	
	@PostConstruct
	private void init() {
		orderController = new OrderController();
		dao = new UserDAO();
		itemsSelected = new HashMap<>();
	}
	
	public List<Orderr> getOrders() {
		return orderController.ordersByClient(getUserLogged());
	}

	public List<Item> getListItems() {
		return dao.findAll(Item.class);
	}

	private User getUserLogged() {
		try {
			return (User) getSession().getAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	public void aumenta() {
		if (item.getQuantity() == null) {
			item.setQuantity(1);
		} else {
			Integer qtd = item.getQuantity();
			item.setQuantity(++qtd);
		}
	}
	
	public void diminui() {
		if (item.getQuantity() != null && item.getQuantity() > 0 ) {
			Integer qtd = item.getQuantity();
			item.setQuantity(--qtd);
		}
	}
	
	public String adiciona() {
		itemsSelected.put(item.getId(), item);
		return "";
	}
	
	public List<Item> getItemsPedido() {
		List<Item> itens = new ArrayList<>();
		for (Item item : itemsSelected.values()) {
			itens.add(item);
		}
		return itens;
	}
	
	public void enviarPedido() {
		for (Item item : itemsSelected.values()) {
			orderController.includeItem(item, item.getQuantity());
		}
		try {
			orderController.createOrder(null, getUserLogged());
		} catch (QuantityNotAvailableException e) {
			//TODO: Messagem de aviso
			e.printStackTrace();
		}
	}
	//getters and setters 

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Map<Long, Item> getItemsSelected() {
		return itemsSelected;
	}

	public void setItemsSelected(Map<Long, Item> itemsSelected) {
		this.itemsSelected = itemsSelected;
	}

}