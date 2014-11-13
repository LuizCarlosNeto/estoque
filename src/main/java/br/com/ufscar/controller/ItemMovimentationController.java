package br.com.ufscar.controller;

import java.math.BigDecimal;
import java.util.Date;

import br.com.ufscar.dao.GenericDAO;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.entity.User;
import br.com.ufscar.enums.ItemMovimentationType;
import br.com.ufscar.exception.InvalidPriceException;
import br.com.ufscar.exception.InvalidQuantityException;
import br.com.ufscar.exception.QuantityNotAvailableException;

public class ItemMovimentationController {
	
	ItemMovimentationDAO itemMovimentationDAO;
	
	public ItemMovimentationController() {
		itemMovimentationDAO = new ItemMovimentationDAO();
	}

	public void entrada(User user, Item item, Integer quantity, BigDecimal valorUnitario) throws InvalidQuantityException, InvalidPriceException {
		GenericDAO dao = new GenericDAO();
		if (quantity == null || quantity < 1 ) {
			throw new InvalidQuantityException();
		}

		if (valorUnitario == null || valorUnitario.compareTo(BigDecimal.ZERO)<=0){
			throw new InvalidPriceException();
		}
		//Dados para cálculo de valores
		Integer quantidadeTotal = item.getQuantity() + quantity;
		BigDecimal valorTotalEstoque = item.getValorTotal();
		valorTotalEstoque = valorTotalEstoque.add(valorUnitario.multiply(new BigDecimal(quantity)));

		//Atualização do valor do item estocado
		BigDecimal precoMedioItemEstoque = valorTotalEstoque.divide(new BigDecimal(quantidadeTotal), 2, BigDecimal.ROUND_DOWN); 
		long precoMedioTruncado = precoMedioItemEstoque.multiply(new BigDecimal(100)).longValue();
		precoMedioItemEstoque = new BigDecimal(precoMedioTruncado).movePointLeft(2);
		BigDecimal valorAjuste = valorTotalEstoque.subtract(precoMedioItemEstoque.multiply(new BigDecimal(quantidadeTotal)));
		item.setPrecoUnitario(precoMedioItemEstoque);
		item.setValorAjuste(valorAjuste);
		dao.update(item);

		//Registro da movimentação
		ItemMovimentation itemMovimentation = new ItemMovimentation();
		itemMovimentation.setItem(item);
		itemMovimentation.setQuantity(quantity);
		itemMovimentation.setDate(new Date());
		itemMovimentation.setType(ItemMovimentationType.IN);
		itemMovimentation.setUserAdmin(user);
		itemMovimentation.setValorUnitario(valorUnitario);
		dao.save(itemMovimentation);
		
	}
	
	public void saida(User user, Item item, Integer quantity) throws QuantityNotAvailableException {
		
		ItemMovimentationDAO dao = new ItemMovimentationDAO();
		
		if (dao.saldoItem(item) < quantity ) {
			throw new QuantityNotAvailableException();
		} else {
			
			// Verifica se tem valor de ajuste. 
			// Se tiver, sai 1 unidade com valor de ajuste e as demais no preço normal.
			if (item.getValorAjuste().compareTo(BigDecimal.ZERO)>0){
				ItemMovimentation itemMovimentation = new ItemMovimentation();
				itemMovimentation.setItem(item);
				itemMovimentation.setQuantity(1);
				itemMovimentation.setDate(new Date());
				itemMovimentation.setType(ItemMovimentationType.OUT);
				itemMovimentation.setUserAdmin(user);
				itemMovimentation.setValorUnitario(item.getPrecoUnitario().add(item.getValorAjuste()));
				dao.save(itemMovimentation);
				item.setValorAjuste(BigDecimal.ZERO);
				dao.update(item);
				quantity = quantity - 1;
			}
			
			//Saída das possíveis demais unidades 
			if (quantity > 0){
				ItemMovimentation itemMovimentation = new ItemMovimentation();
				itemMovimentation.setItem(item);
				itemMovimentation.setQuantity(quantity);
				itemMovimentation.setDate(new Date());
				itemMovimentation.setType(ItemMovimentationType.OUT);
				itemMovimentation.setUserAdmin(user);
				itemMovimentation.setValorUnitario(item.getPrecoUnitario());
				dao.save(itemMovimentation);
			}
		}
	}
}
