package br.com.ufscar.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import br.com.ufscar.enums.OrderStatus;
import br.com.ufscar.enums.OrderType;

@Entity
public class Orderr implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private Date date;
	
	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn(name="orderr_id")
	private List<ItemOrder> Items;

	@ManyToOne
	private User userAdmin;

	@ManyToOne
	private User userClient;
	
	@Enumerated(EnumType.STRING)
	private OrderStatus status;
	
	@Enumerated(EnumType.STRING)
	private OrderType type;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<ItemOrder> getItems() {
		return Items;
	}

	public void setItems(List<ItemOrder> items) {
		Items = items;
	}

	public User getUserAdmin() {
		return userAdmin;
	}

	public void setUserAdmin(User userAdmin) {
		this.userAdmin = userAdmin;
	}

	public User getUserClient() {
		return userClient;
	}

	public void setUserClient(User userClient) {
		this.userClient = userClient;
	}

	public OrderStatus getStatus() {
		return status;
	}
	
	public OrderType getType() {
		return type;
	}
	
	public void setType(OrderType type) {
		this.type = type;
	}

	public void setAsDelivered() {
		status = OrderStatus.DELIVERED;
	}

	public void setAsPacking() {
		status = OrderStatus.PACKING;
	}
	
	public void setAsSent() {
		status = OrderStatus.SENT;
	}

	public void setAsRejected() {
		status = OrderStatus.REJECTED;
	}
	
}
