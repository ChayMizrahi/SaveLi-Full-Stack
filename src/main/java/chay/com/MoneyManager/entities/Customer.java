package chay.com.MoneyManager.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	/**
	 * Will be used to logged in.
	 */
	private String email;
	/**
	 * Will be used to logged in.
	 */
	private String password;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	@Valid
	private Collection<MethodPayment> methodPayments;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Collection<User> users;

	public Customer() {
		this.methodPayments = new ArrayList<>();
		this.users = new ArrayList<>();
	}

	public Customer(String customerName, String email, String password) {
		this.email = email;
		this.password = password;
		this.methodPayments = new ArrayList<>();
		this.users = new ArrayList<>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Collection<MethodPayment> getMethodPayments() {
		return methodPayments;
	}

	public void setMethodPayment(Collection<MethodPayment> methodPayments) {
		this.methodPayments = methodPayments;
	}

	public Collection<User> getUsers() {
		return users;
	}

	public void setUsers(Collection<User> users) {
		this.users = users;
	}

	public void setMethodPayments(Collection<MethodPayment> methodPayments) {
		this.methodPayments = methodPayments;
	}

	public void addUser(User user) {
		this.users.add(user);
		user.setCustomer(this);
	}

	// Allow us to insert income to the list incomes.
	public void addAction(MethodPayment methodPayment) {
		this.methodPayments.add(methodPayment);
		methodPayment.setCustomer(this);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", email=" + email + ", password=" + password + ", methodPayments=" + methodPayments + ", users=" + users + "]";
	}

}
