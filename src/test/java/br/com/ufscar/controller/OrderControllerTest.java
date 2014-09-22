package br.com.ufscar.controller;

import static org.junit.Assert.*;

import org.junit.Test;

import br.com.ufscar.BaseTest;
import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class OrderControllerTest extends BaseTest{

	@Test
	public void testCreateOrder() throws QuantityNotAvailableException {
		GenericDAO dao = new GenericDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		User userClient = userDAO.findUserByEmail(USER_CLIENT+"@estoque.com");
		
		OrderController controller = new OrderController();
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM_ORDER);
		Item itemDB2 = dao.findOneByCustomField(Item.class, "name", ITEM_ORDER2);
		
		ItemMovimentationController movimentationController = new ItemMovimentationController();
		movimentationController.entrada(userAdmin, itemDB, 2);
		movimentationController.entrada(userAdmin, itemDB2, 1);
		
		controller.includeItem(itemDB, 2);
		controller.includeItem(itemDB2, 1);
		controller.createOrder(userAdmin, userClient);
		assertTrue(true);
	}
}
