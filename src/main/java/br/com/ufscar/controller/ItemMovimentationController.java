package br.com.ufscar.controller;

import java.util.Date;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.entity.User;
import br.com.ufscar.enums.ItemMovimentationType;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class ItemMovimentationController {

	public void entrada(User user, Item item, Integer quantity) throws QuantityNotAvailableException {
		GenericDAO dao = new GenericDAO();
		if (quantity == null || quantity < 1 ) {
			throw new QuantityNotAvailableException();
		} else {
			ItemMovimentation itemMovimentation = new ItemMovimentation();
			itemMovimentation.setItem(item);
			itemMovimentation.setQuantity(quantity);
			itemMovimentation.setDate(new Date());
			itemMovimentation.setType(ItemMovimentationType.IN);
			itemMovimentation.setUserAdmin(user);
			dao.save(itemMovimentation);
		}
	}
	
	public void saida(User user, Item item, Integer quantity) throws QuantityNotAvailableException {
		
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
			dao.save(itemMovimentation);
		}
		
	}
	
}
