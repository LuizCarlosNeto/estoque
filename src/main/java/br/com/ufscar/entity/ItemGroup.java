package br.com.ufscar.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "ItemGroup.findItemGroupByName", query = "SELECT ig FROM ItemGroup ig WHERE UPPER(ig.name) = UPPER(:name)")
public class ItemGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_NAME = "ItemGroup.findItemGroupByName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(unique = true)
	private String name;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name!=null && !name.isEmpty())
			this.name = name.toUpperCase();
	}

	@Override
	public int hashCode() {
		return getId();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ItemGroup) {
			ItemGroup classObj = (ItemGroup) obj;
			return classObj.getId() == id;
		}

		return false;
	}
}