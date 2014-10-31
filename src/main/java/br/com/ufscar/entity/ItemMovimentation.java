package br.com.ufscar.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import br.com.ufscar.enums.ItemMovimentationType;

@Entity
public class ItemMovimentation implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;

	private Integer quantity;
	
	private BigDecimal valorUnitario;
	
	@Enumerated(EnumType.STRING)
	private ItemMovimentationType type;

	@ManyToOne
	private Item item;
	
	@ManyToOne
	private User userAdmin;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getQuantity() {
		if (this.quantity <= 0)
			setQuantity(0);
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public ItemMovimentationType getType() {
		return type;
	}

	public void setType(ItemMovimentationType type) {
		this.type = type;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public User getUserAdmin() {
		return userAdmin;
	}
	
	public void setUserAdmin(User userAdmin) {
		this.userAdmin = userAdmin;
	}

	public BigDecimal getValorUnitario() {
		if (this.valorUnitario == null)
			setValorUnitario(BigDecimal.ZERO);
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}
	
	@Transient
	public BigDecimal getValorTotal() {
		return getValorUnitario().multiply(new BigDecimal(getQuantity()));
	}

}