package br.com.ufscar.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.ufscar.BaseTest;
import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.InvalidPriceException;
import br.com.ufscar.exception.InvalidQuantityException;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class OrderControllerTest extends BaseTest{

	@Test
	public void testCreateOrder() throws InvalidQuantityException, InvalidPriceException  {
		GenericDAO dao = new GenericDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		User userClient = userDAO.findUserByEmail(USER_CLIENT+"@estoque.com");
		
		OrderController controller = new OrderController();
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM_ORDER);
		Item itemDB2 = dao.findOneByCustomField(Item.class, "name", ITEM_ORDER2);
		
		ItemMovimentationController movimentationController = new ItemMovimentationController();
		movimentationController.entrada(userAdmin, itemDB, 2, new BigDecimal("2.22"));
		movimentationController.entrada(userAdmin, itemDB2, 1, new BigDecimal("1.11"));
		
		controller.includeItem(itemDB, 2);
		controller.includeItem(itemDB2, 1);
		controller.requireOrder(userClient);
		assertTrue(true);
	}
}
