package br.com.ufscar.controller;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import br.com.ufscar.BaseTest;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.InvalidPriceException;
import br.com.ufscar.exception.InvalidQuantityException;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class ItemMovimentationControllerTest extends BaseTest{

	@Test
	public void testeEntrada() throws InvalidQuantityException, InvalidPriceException {
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		Integer saldoAntes = dao.saldoItem(itemDB);
		controller.entrada(userAdmin, itemDB, 2, new BigDecimal("3.33"));
		Integer saldoDepois = dao.saldoItem(itemDB);
		
		assertTrue(saldoAntes + 2 == saldoDepois);
	}
	
	@Test(expected=InvalidQuantityException.class)
	public void testeEntradaQuantidadeNula() throws InvalidQuantityException, InvalidPriceException {
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		controller.entrada(userAdmin, itemDB, null, new BigDecimal("3.33"));
		
	}
	
	@Test
	public void testeSaida() throws QuantityNotAvailableException, InvalidQuantityException, InvalidPriceException {
		
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		UserDAO userDAO = new UserDAO();
		User userAdmin = userDAO.findUserByEmail(USER_ADMIN+"@estoque.com");
		Item itemDB = dao.findOneByCustomField(Item.class, "name", ITEM);
		ItemMovimentationController controller = new ItemMovimentationController();
		
		controller.entrada(userAdmin, itemDB, 2, new BigDecimal("3.33"));
		
		Integer saldoAntes = dao.saldoItem(itemDB);
		controller.saida(userAdmin, itemDB, 2);
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
		controller.saida(userAdmin, itemDB, saldoAntes + 1);
		
	}
}
