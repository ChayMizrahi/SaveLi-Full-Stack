package chay.com.MoneyManager.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String name;

	private boolean active;
	
	@ManyToOne
	@JsonIgnore
	private Customer customer;
	
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private Collection<Action> actions;

	public User() {
	}

	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Collection<Action> getActions() {
		return actions;
	}



	public void setActions(Collection<Action> actions) {
		this.actions = actions;
	}



	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", customer=" + customer + "]";
	}

}
