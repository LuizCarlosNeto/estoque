package br.com.ufscar.managerbean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.controller.ItemMovimentationController;
import br.com.ufscar.dao.ItemGroupDAO;
import br.com.ufscar.dao.ItemMovimentationDAO;
import br.com.ufscar.dao.UserDAO;
import br.com.ufscar.entity.Item;
import br.com.ufscar.entity.ItemGroup;
import br.com.ufscar.entity.ItemMovimentation;
import br.com.ufscar.entity.User;
import br.com.ufscar.exception.QuantityNotAvailableException;
import br.com.ufscar.util.UserSessionUtil;


@SessionScoped
@ManagedBean
public class ItemBBean implements Serializable {

	private Item item;
	private Map<String,Long> itensGroup;
	private ItemMovimentationDAO itemMovimentationDAO;
	private UserDAO userDAO;
	private ItemGroupDAO itemGroupDAO;
	private Long itemGroupSelectedId;
	List<String> itemGroups; 
	private ItemMovimentation itemMovimentation;
	private Integer quantity;
	private BigDecimal valorUnitario;
	private Date startPeriod;
	private Date endPeriod;
	private List<ItemMovimentation> itemMovimentations;
	private List<ItemGroup> groupsSelected;
	private List<User> usersSelected;
	
	public ItemBBean() {
		super();
		this.init();
	}
	
	private void init() {
		itemMovimentationDAO = new ItemMovimentationDAO();
		itemGroupDAO = new ItemGroupDAO();
		userDAO = new UserDAO();
		item = new Item();
		itemGroupSelectedId = null;
		quantity = null;
	}
	
	private void reset() {
		this.init();
	}
	
	public String saveItem() {
		if (itemGroupSelectedId != null){
			item.setItemGroup(itemMovimentationDAO.find(ItemGroup.class, itemGroupSelectedId));
		}
		if (item.getId() != null) {
			itemMovimentationDAO.update(item);
		} else{
			itemMovimentationDAO.save(item);
		}
		this.reset();
		return "/pages/protected/admin/item/item-listar.xhtml";
	}
	
	public Map<String,Long> getListItemGroups() {
		Map<String,Long> departmentsHash = new HashMap<String, Long>();
		for (ItemGroup itemGroup : itemMovimentationDAO.findAll(ItemGroup.class)) {
			departmentsHash.put(itemGroup.getName(), itemGroup.getId());
		}
		return departmentsHash;
	}
	
	public List<Item> getListItens() {
		return itemMovimentationDAO.findAll(Item.class);
	}
	
	public String removeItem() {
		itemMovimentationDAO.delete(item.getId(), Item.class);
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
		return itemMovimentationDAO.listItemMovimentationByItem(item);
	}
	
	public Integer getSaldo() {
		System.out.println(item.getName());
		return itemMovimentationDAO.saldoItem(item);
	}
	
	public String registrarEntrada() {
		ItemMovimentationController controller = new ItemMovimentationController();
		try {
			controller.entrada(UserSessionUtil.getUserFromSession(), item, quantity, valorUnitario);
			return "";
		} catch (Exception e) {
			// TODO mensagem de alerta
			e.printStackTrace();
			return "";
		}
		
	}
	
	public String registrarSaida() {
		ItemMovimentationController controller = new ItemMovimentationController();
		try {
			controller.saida(UserSessionUtil.getUserFromSession(), item, quantity);
			return "";
		} catch (QuantityNotAvailableException e) {
			// TODO mensagem de alerta
			e.printStackTrace();
			return "";
		} 
	}
	
	public void listItensByPeriod() {
		this.itemMovimentations = itemMovimentationDAO.listItemMovimentationByPeriod(startPeriod, endPeriod);
	}
	
	public void listItensByPeriodGroupByItemGroup() {
		this.itemMovimentations = itemMovimentationDAO.listItemMovimentationByPeriodGroupByItemGroup(startPeriod, endPeriod, getGroupsSelected());
	}
	
	public void listItensByPeriodGroupByUser() {
		this.itemMovimentations = itemMovimentationDAO.listItemMovimentationByPeriodGroupByUser(startPeriod, endPeriod, getUsersSelected());
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

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Date getStartPeriod() {
		//Período padrão: Do início do mês até o dia atual.
		if (this.startPeriod == null){
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(getEndPeriod());
			gc.set(GregorianCalendar.DAY_OF_MONTH, 1);
			setStartPeriod(gc.getTime());
		}
		return startPeriod;
	}

	public void setStartPeriod(Date startPeriod) {
		this.startPeriod = startPeriod;
	}

	public Date getEndPeriod() {
		//Período padrão: Do início do mês até o dia atual.
		if (this.endPeriod == null)
			setEndPeriod(new Date());
		return endPeriod;
	}

	public void setEndPeriod(Date endPeriod) {
		this.endPeriod = endPeriod;
	}

	public List<ItemMovimentation> getItemMovimentations() {
		return itemMovimentations;
	}

	public void setItemMovimentations(List<ItemMovimentation> itemMovimentations) {
		this.itemMovimentations = itemMovimentations;
	}

	public List<ItemGroup> getGroupsSelected() {
		return groupsSelected;
	}

	public void setGroupsSelected(List<ItemGroup> groupsSelected) {
		this.groupsSelected = groupsSelected;
	}

	public List<User> getUsersSelected() {
		return usersSelected;
	}

	public void setUsersSelected(List<User> usersSelected) {
		this.usersSelected = usersSelected;
	}
	
	public List<ItemGroup> getListItemGroup() {
		return itemGroupDAO.findAll(ItemGroup.class);
	}

	public List<User> getListUser() {
		return userDAO.findAll(User.class);
	}
	
}