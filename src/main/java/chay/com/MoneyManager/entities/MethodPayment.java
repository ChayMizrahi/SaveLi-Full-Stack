package chay.com.MoneyManager.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MethodPayment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	/**
	 * 0 = The billing date will be on the date defined in action (Represented
	 * wallet). 1-31 = The action date will be every month on the date entered into
	 * this field. If the date entered is 10 then all the actions associated with
	 * that object will appear in the balance table on the 10th of each month
	 * (represented credit card ).
	 */
	@Min(0)
	@Max(31)
	private int debitDay;

	/**
	 * The field defines if the method payment is active. If the method payment is
	 * not active the customer can't use in this any more.
	 */
	private boolean active;

	/**
	 * The field will contain all the actions that belong to this methodPayment.
	 */
	@OneToMany(mappedBy="methodPayment")
	@JsonIgnore
	private Collection<Action> actions;
	
	/**
	 * The field represents the owner of the method payment.
	 */
	@ManyToOne
	@JsonIgnore
	private Customer customer;

	public MethodPayment() {
		this.actions = new ArrayList<>();
		this.active = true;
	}

	public MethodPayment(String name, @Min(0) @Max(31) int debitDate, boolean active) {
		this.name = name;
		this.debitDay = debitDate;
		this.active = active;
		this.actions = new ArrayList<>();
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

	public void setName(String name) {
		this.name = name;
	}

	public int getDebitDate() {
		return debitDay;
	}

	public void setDebitDate(int debitDate) {
		this.debitDay = debitDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public void addAction(Action action) {
		this.actions.add(action);
		action.setMethodPayment(this);
	}
	
	@Override
	public String toString() {
		return "MethodPayment [id=" + id + ", name=" + name + ", debitDate=" + debitDay + ", active=" + active
				 + "]";
	}

}
