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

	public static final String FIND_BY_NAME = "ItemGroup.findItemGroupByName";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String name;
	
//	@OneToMany(mappedBy="item",cascade=CascadeType.PERSIST)
//	private List<Item> itens = new ArrayList<Item>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (name!=null && !name.isEmpty())
			this.name = name.toUpperCase();
	}

}