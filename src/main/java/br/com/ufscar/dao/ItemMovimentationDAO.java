package br.com.ufscar.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemGroup;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.entity.ItemOrder;
import br.com.ufscar.entity.User;
import br.com.ufscar.enums.ItemMovimentationType;
import br.com.ufscar.enums.OrderStatus;
import br.com.ufscar.enums.OrderType;


public class ItemMovimentationDAO extends GenericDAO{
	
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
		Map<String, Object> parameters = new HashMap<>();

		String queryStr = "SELECT SUM(m.quantity) FROM " + ItemMovimentation.class.getSimpleName() 
				+ " m WHERE m.item = :item AND m.type = :type";
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
				+ " c WHERE c.item = :item ORDER BY c.date";
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("item", item);
		
		return findResults(queryStr, parameters);
	}
	
	public Integer calcularQuantidadeReposicao(Item item) {
		long meses = 0;
		if (listItemMovimentationByItem(item)!=null && !listItemMovimentationByItem(item).isEmpty()){
			Date primeiraMovimentacao = listItemMovimentationByItem(item).iterator().next().getDate();
			long tempo = new Date().getTime() - primeiraMovimentacao.getTime();
			meses = tempo / (1000 * 60 * 60 * 24 * 30);
		}
		meses = meses > 0? meses : 1;
		Integer saidaMensal = (int) (soma(item, ItemMovimentationType.OUT).intValue() / meses);
		Integer pedidosAbertos = somaPedidosAbertos(item, OrderType.CLIENT);
		Integer reposicoesAbertas = somaPedidosAbertos(item, OrderType.STOCK);
		Integer saldo = saldoItem(item);
		
		int repor = ((saldo + reposicoesAbertas) - (pedidosAbertos + (saidaMensal))) * -1;
		
		if (repor > 0)
			return repor;
		return 0;
	}
	
	private Integer somaPedidosAbertos(Item item, OrderType orderType) {
		Map<String, Object> parameters = new HashMap<>();
		Collection<OrderStatus> statusPedidoAberto = new ArrayList<OrderStatus>();
		statusPedidoAberto.add(OrderStatus.SENT);
		statusPedidoAberto.add(OrderStatus.PACKING);
		String queryStr = "SELECT SUM(i.quantity) FROM " + ItemOrder.class.getSimpleName() 
				+ " i WHERE i.orderr.type = :pOrderType" 
				+ " AND i.orderr.status IN (:pOrderStatus)"
				+ " AND i.item = :pItem";
		parameters.put("pItem", item);
		parameters.put("pOrderStatus", statusPedidoAberto);
		parameters.put("pOrderType", orderType);

		Long result= null;
		
		 
        try {
        	beginTransaction();
            Query query = em.createQuery(queryStr);
 
            // Method that will populate parameters if they are passed not null and empty
            if (parameters != null && !parameters.isEmpty()) {
                populateQueryParameters(query, parameters);
            }
 
            result = (Long)query.getSingleResult();
            
            if (result == null) {
            	result = 0l;
            }
 
        } catch (NoResultException e) {
            System.out.println("No result found for named query: " + queryStr);
        } catch (Exception e) {
            System.out.println("Error while running query: " + e.getMessage());
            e.printStackTrace();
        } finally {
        	this.closeTransaction();
        }
		
		return result.intValue();
	}

	public List<ItemMovimentation> listItemMovimentationByPeriod(Date start, Date end) {
		return listMovimentationByPeriod(start, end, null, null);
	}
	
	public List<ItemMovimentation> listItemMovimentationByPeriodGroupByItemGroup(Date start, Date end, List<ItemGroup> groups) {
		return listMovimentationByPeriod(start, end, groups, null);
	}

	public List<ItemMovimentation> listItemMovimentationByPeriodGroupByUser(Date start, Date end, List<User> users) {
		return listMovimentationByPeriod(start, end, null, users);
	}
	
	private List<ItemMovimentation> listMovimentationByPeriod(Date start, Date end, List<ItemGroup> groups, List<User> users) {
		StringBuilder query = new StringBuilder("FROM " + ItemMovimentation.class.getSimpleName())
		.append(" WHERE (date BETWEEN :start AND :end) ");
		
		if (groups != null) query.append(" AND item.itemGroup IN :groups ");
		if (users != null) query.append(" AND userAdmin IN :users ");
		
		query.append(" ORDER BY date ");
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("start", start);
		parameters.put("end", end);
		if (groups != null) parameters.put("groups", groups);
		if (users != null) parameters.put("users", users);
		
		return findResults(query.toString(), parameters);
	}

}
