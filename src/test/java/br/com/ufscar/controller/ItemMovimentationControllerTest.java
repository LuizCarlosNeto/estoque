package br.com.ufscar.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import br.com.ufscar.BaseTest;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class ItemMovimentationControllerTest extends BaseTest{

	@Test
	public void testeEntrada() throws QuantityNotAvailableException {
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		Integer saldoAntes = dao.saldoItem(itemDB);
		controller.entrada(userAdmin, itemDB, 2, null);
		Integer saldoDepois = dao.saldoItem(itemDB);
		
		assertTrue(saldoAntes + 2 == saldoDepois);
	}
	
	@Test(expected=QuantityNotAvailableException.class)
	public void testeEntradaQuantidadeNula() throws QuantityNotAvailableException {
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		controller.entrada(userAdmin, itemDB, null, null);
		
	}
	
	@Test
	public void testeSaida() throws QuantityNotAvailableException {
		
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		Integer saldoAntes = dao.saldoItem(itemDB);
		controller.saida(userAdmin, itemDB, 2, null);
		Integer saldoDepois = dao.saldoItem(itemDB);
		
		assertTrue(saldoAntes - 2 == saldoDepois);
		
	}
	
	@Test(expected=QuantityNotAvailableException.class)
	public void testeSaidaSaldoNegativo() throws QuantityNotAvailableException {
		
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		Integer saldoAntes = dao.saldoItem(itemDB);
		controller.saida(userAdmin, itemDB, saldoAntes + 1, null);
		
	}
}
