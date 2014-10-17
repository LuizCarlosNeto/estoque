package br.com.ufscar.managerbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.controller.OrderController;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;


@SessionScoped
@ManagedBean
public class OrderBBean implements Serializable {

	OrderController controller;
	UserDAO dao;
	
	private Item item;

	public OrderBBean() {
		super();
		this.init();
	}
	
	private void init() {
		controller = new OrderController();
		dao = new UserDAO();
	}
	
	public List<Orderr> getOrders() {
		return controller.ordersByClient(getUserLogged());
	}

	private User getUserLogged() {
		try {
			// TODO: verificar
			return dao.findUserByEmail("userClient1@estoque.com");
//			return (User) getRequest().getAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
//	private HttpServletRequest getRequest() {
//		return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//	}
	
	
	//getters and setters 

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}