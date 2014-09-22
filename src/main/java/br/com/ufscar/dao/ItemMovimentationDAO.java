package br.com.ufscar.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.enums.ItemMovimentationType;


public class ItemMovimentationDAO extends GenericDAO{

	private static final long serialVersionUID = 1L;
	
	public ItemMovimentationDAO() {
		super();
	}

	public Integer saldoItem(Item item) {
		return somaIN(item).intValue() - somaOUT(item).intValue();
	}
	
	private Number somaIN(Item item) {
		return soma(item, ItemMovimentationType.IN);
	}
	
	private Number somaOUT(Item item) {
		return soma(item, ItemMovimentationType.OUT);
	}

	private Number soma(Item item, ItemMovimentationType itemMovimentationType) {
		String queryStr = "SELECT SUM(c.quantity) FROM " + ItemMovimentation.class.getSimpleName() 
				+ " c WHERE c.item = :item AND c.type = :type";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("item", item);
		parameters.put("type", itemMovimentationType);

		Number result= null;
		
		 
        try {
        	beginTransaction();
            Query query = em.createQuery(queryStr);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (Number)query.getSingleResult();
            
            if (result == null) {
            	result = 0;
            }
 
        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + queryStr);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        } finally {
        	this.closeTransaction();
        }
		
		return result;
	}
	
	public List<ItemMovimentation> listItemMovimentationByItem(Item item) {
		String queryStr = "SELECT c FROM " + ItemMovimentation.class.getSimpleName() 
				+ " c WHERE c.item = :item";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("item", item);
		
		return findResults(queryStr, parameters);
	}
	
}
