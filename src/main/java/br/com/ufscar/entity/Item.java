package br.com.ufscar.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.ufscar.dao.ItemMovimentationDAO;

@Entity
public class Item implements Serializable {

	@Transient
	private ItemMovimentationDAO dao = new ItemMovimentationDAO();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	
	@ManyToOne
	private ItemGroup itemGroup;
	
	@Transient
	private Integer quantity; 

	private BigDecimal precoUnitario;
	
	private BigDecimal valorAjuste;
	
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
		this.name = name;
	}

	public ItemGroup getItemGroup() {
		return itemGroup;
	}

	public void setItemGroup(ItemGroup itemGroup) {
		this.itemGroup = itemGroup;
	}

	public Integer getQuantity() {
		return dao.saldoItem(this);
	}

	public BigDecimal getPrecoUnitario() {
		if (this.precoUnitario==null)
			setPrecoUnitario(BigDecimal.ZERO);
		return precoUnitario;
	}

	public void setPrecoUnitario(BigDecimal precoUnitario) {
		this.precoUnitario = precoUnitario;
	}

	public BigDecimal getValorAjuste() {
		if (this.valorAjuste==null)
			setValorAjuste(BigDecimal.ZERO);
		return valorAjuste;
	}

	public void setValorAjuste(BigDecimal valorAjuste) {
		this.valorAjuste = valorAjuste;
	}

	public BigDecimal getValorTotal() {
		BigDecimal valorTotal = BigDecimal.ZERO;
		valorTotal = valorTotal.add(getPrecoUnitario().multiply(new BigDecimal(getQuantity())));
		valorTotal = valorTotal.add(getValorAjuste());
		return valorTotal;
	}

}