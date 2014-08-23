package br.com.ufscar.managerBean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.ufscar.dao.ItemGroupDAO;
import br.com.ufscar.entity.ItemGroup;


@SessionScoped
@ManagedBean
public class ItemGroupBBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private ItemGroup itemGroup;
	private ItemGroupDAO dao;
	
	public ItemGroupBBean() {
		super();
		this.init();
	}
	
	private void init() {
		dao = new ItemGroupDAO();
		itemGroup = new ItemGroup();
	}
	
	private void reset() {
		this.init();
	}
	
	public String saveItemGroup() {
		if (itemGroup.getId()!=null)
			dao.update(itemGroup);
		else
			dao.save(itemGroup);
		this.reset();
		return "/pages/protected/admin/item/grupo-item-listar.xhtml";
	}
	
	public List<ItemGroup> getListItemGroups() {
		return dao.findAll(ItemGroup.class);
	}
	
	public String removeItemGroup() {
		dao.delete(itemGroup.getId(), ItemGroup.class);
		return "";
	}
	
	public String showNew() {
		this.reset();
		return "/pages/protected/admin/item/grupo-item-cadastro.xhtml";
	}
	
	public String showEdit() {
		return "/pages/protected/admin/item/grupo-item-cadastro.xhtml";
	}

	public String showList() {
		this.reset();
		return "/pages/protected/admin/item/grupo-item-listar.xhtml";
	}

	
	//getters and setters 
	public ItemGroup getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}

	
}