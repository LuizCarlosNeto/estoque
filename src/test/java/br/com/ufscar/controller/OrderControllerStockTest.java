package br.com.ufscar.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.ufscar.BaseTest;
import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.User;

public class OrderControllerStockTest extends BaseTest{


	@Test
	public void testCreateOrderStock()  {
		GenericDAO dao = new GenericDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		
		OrderStockController controller = new OrderStockController();
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM_ORDER);
		Item itemDB2 = dao.findOneByCustomField(Item.class, "name", ITEM_ORDER2);
		
		controller.includeItemStock(itemDB, 2);
		controller.includeItemStock(itemDB2, 1);
		controller.requireOrderStock(userAdmin);
		assertTrue(true);
	}
}
