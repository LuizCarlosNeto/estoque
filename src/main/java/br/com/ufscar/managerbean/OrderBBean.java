package br.com.ufscar.managerbean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
//			return dao.findUserByEmail("userClient1@estoque.com");
			return (User) getSession().getAttribute("user");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private HttpSession getSession() {
		return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}
	
	
	//getters and setters 

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	
}