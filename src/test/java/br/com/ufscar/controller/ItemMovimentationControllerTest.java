package br.com.ufscar.controller;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import br.com.ufscar.BaseTest;
import br.com.ufscar.dao.ItemGroupDAO;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemGroup;
import br.com.ufscar.entity.ItemMovimentation;
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
	
	@Test
	public void testeRelatorioPorPeriodoEGrupo(){
		UserDAO userDAO = new UserDAO();
		User user1 = userDAO.find(User.class, 1l);
		User user2 = userDAO.find(User.class, 2l);
		ItemGroupDAO itemGroupDAO = new ItemGroupDAO();
		ItemGroup group1 = itemGroupDAO.find(ItemGroup.class, 1l);
		ItemGroup group2 = itemGroupDAO.find(ItemGroup.class, 2l);
		
		ItemMovimentationDAO itemMovimentationDAO = new ItemMovimentationDAO();
		
		List<ItemMovimentation> listGroup = itemMovimentationDAO.listItemMovimentationByPeriodGroupByItemGroup(new Date(1388552400000l), new Date(), Arrays.asList(group1, group2));
		assertFalse(listGroup.isEmpty());
		
		List<ItemMovimentation> listUser = itemMovimentationDAO.listItemMovimentationByPeriodGroupByUser(new Date(1388552400000l), new Date(), Arrays.asList(user1, user2));
		assertFalse(listUser.isEmpty());
		
	}
}
