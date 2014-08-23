package br.com.ufscar.dao;

import java.util.HashMap;
import java.util.Map;

import br.com.ufscar.entity.ItemGroup;
import br.com.ufscar.entity.User;

public class ItemGroupDAO extends GenericDAO{

	private static final long serialVersionUID = 1L;

	public ItemGroupDAO() {
		super();
	}

	public User findItemGroupByName(String name) {
		Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", name);     
 
        return super.findOneResult(ItemGroup.FIND_BY_NAME, parameters);
	}
	
}
