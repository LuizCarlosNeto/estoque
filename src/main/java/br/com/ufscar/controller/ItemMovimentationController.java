package br.com.ufscar.controller;

import java.util.Date;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.entity.ItemMovimentationType;
import br.com.ufscar.entity.Orderr;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class ItemMovimentationController {

	public void entrada(User user, Item item, Integer quantity, Orderr order) {
		ItemMovimentation itemMovimentation = new ItemMovimentation();
		itemMovimentation.setItem(item);
		itemMovimentation.setQuantity(quantity);
		itemMovimentation.setDate(new Date());
		itemMovimentation.setType(ItemMovimentationType.IN);
		itemMovimentation.setUserAdmin(user);
		if (order != null) itemMovimentation.setOrder(order);
		
		GenericDAO dao = new GenericDAO();
		dao.save(itemMovimentation);
	}
	
	public void saida(User user, Item item, Integer quantity, Orderr order) throws QuantityNotAvailableException {
		
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		
		if (dao.saldoItem(item) < quantity ) {
			throw new QuantityNotAvailableException();
		} else {
			ItemMovimentation itemMovimentation = new ItemMovimentation();
			itemMovimentation.setItem(item);
			itemMovimentation.setQuantity(quantity);
			itemMovimentation.setDate(new Date());
			itemMovimentation.setType(ItemMovimentationType.OUT);
			itemMovimentation.setUserAdmin(user);
			if (order != null) itemMovimentation.setOrder(order);
			dao.save(itemMovimentation);
		}
		
	}
}
