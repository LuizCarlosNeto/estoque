package br.com.ufscar.managerBean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.controller.ItemMovimentationController;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemGroup;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.exception.QuantityNotAvailableException;
import br.com.ufscar.util.UserSessionUtil;


@SessionScoped
@ManagedBean
public class ItemBBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private Item item;
	private Map<String,Long> itensGroup;
	private ItemMovimentationDAO dao;
	private Long itemGroupSelectedId;
	List<String> itemGroups; 
	private ItemMovimentation itemMovimentation;
	private Integer quantity;

	public ItemBBean() {
		super();
		this.init();
	}
	
	private void init() {
		dao = new ItemMovimentationDAO();
		item = new Item();
		itemGroupSelectedId = null;
		quantity = null;
	}
	
	private void reset() {
		this.init();
	}
	
	public String saveItem() {
		if (itemGroupSelectedId != null){
			item.setItemGroup(dao.find(ItemGroup.class, itemGroupSelectedId));
		}
		if (item.getId() != null) {
			dao.update(item);
		} else{
			dao.save(item);
		}
		this.reset();
		return "/pages/protected/admin/item/item-listar.xhtml";
	}
	
	public Map<String,Long> getListItemGroups() {
		Map<String,Long> departmentsHash = new HashMap<String, Long>();
		for (ItemGroup itemGroup : dao.findAll(ItemGroup.class)) {
			departmentsHash.put(itemGroup.getName(), itemGroup.getId());
		}
		return departmentsHash;
	}
	
	public List<Item> getListItens() {
		return dao.findAll(Item.class);
	}
	
	public String removeItem() {
		dao.delete(item.getId(), Item.class);
		return "";
	}
	
	public String editItem() {
		return "/pages/protected/admin/item/item-cadastro.xhtml";
	}
	
	public String newItem() {
		reset();
		return "/pages/protected/admin/item/item-cadastro.xhtml";
	}
	
	public String movimentationItem() {
		
		return "/pages/protected/admin/item/movimentacao-item-listar.xhtml";
	}
	
	public List<ItemMovimentation> getListItemMovimentations() {
		return dao.listItemMovimentationByItem(item);
	}
	
	public Integer getSaldo() {
		System.out.println(item.getName());
		return dao.saldoItem(item);
	}
	
	public String registrarEntrada() {
		ItemMovimentationController controller = new ItemMovimentationController();
		try {
			controller.entrada(UserSessionUtil.getUserFromSession(), item, quantity, null);
			return "";
		} catch (QuantityNotAvailableException e) {
			// TODO mensagem de alerta
			e.printStackTrace();
			return "";
		}
		
	}
	
	public String registrarSaida() {
		ItemMovimentationController controller = new ItemMovimentationController();
		try {
			controller.saida(UserSessionUtil.getUserFromSession(), item, quantity, null);
			return "";
		} catch (QuantityNotAvailableException e) {
			// TODO mensagem de alerta
			e.printStackTrace();
			return "";
		} 
	}
	
	
	//getters and setters 

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Map<String, Long> getItensGroup() {
		return itensGroup;
	}

	public void setItensGroup(Map<String, Long> itensGroup) {
		this.itensGroup = itensGroup;
	}

	public Long getItemGroupSelectedId() {
		return itemGroupSelectedId;
	}

	public void setItemGroupSelectedId(Long itemGroupSelectedId) {
		this.itemGroupSelectedId = itemGroupSelectedId;
	}
	
	public List<String> getItemGroups() {
		return itemGroups;
	}
	
	public void setItemGroups(List<String> itemGroups) {
		this.itemGroups = itemGroups;
	}

	public ItemMovimentation getItemMovimentation() {
		return itemMovimentation;
	}

	public void setItemMovimentation(ItemMovimentation itemMovimentation) {
		this.itemMovimentation = itemMovimentation;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
}