package chay.com.MoneyManager.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The object represent financial action. One of the fields of the object is
 * action type and he from boolean type. FinancailAction with actionType = true
 * -> income. FinancailAction with actionType = false -> expense.
 * 
 * @author Chay Mizrahi
 *
 */
@Entity
public class Action {

	/**
	 * The ID of the object is unique and allows to locate a specific object
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	/**
	 * An Enum object that determines whether the operation is an expense or income
	 */
	@NotNull
	private ActionType actionType;

	/**
	 * Displays a description of the action
	 */
	@Column(nullable = true)
	private String title;

	/**
	 * Represents the amount of action (must be positive)
	 */
	@Positive
	private double amount;

	/**
	 * Represents the date the action was performed
	 */
	@Temporal(TemporalType.DATE)
	private Date actionDate;

	/**
	 * Represents the date the operation was charged
	 */
	@Temporal(TemporalType.DATE)
	private Date debitDate;

	/**
	 * The category to which the action is associated
	 */
	@ManyToOne
	private Category category;

	/**
	 * The user whose action was named after it
	 */
	@ManyToOne
	private User user;

	/**
	 * Represents the payment method through which the action was performed
	 */
	@ManyToOne
	@Valid
	private MethodPayment methodPayment;

	public Action() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ActionType getActionType() {
		return actionType;
	}

	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Date getDebitDate() {
		return debitDate;
	}

	public void setDebitDate(Date debitDate) {
		this.debitDate = debitDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MethodPayment getMethodPayment() {
		return methodPayment;
	}

	public void setMethodPayment(MethodPayment methodPayment) {
		this.methodPayment = methodPayment;
	}

	@Override
	public String toString() {
		return "Action [id=" + id + ", actionType=" + actionType + ", title=" + title + ", amount=" + amount
				+ ", actionDate=" + actionDate + ", debitDate=" + debitDate + ", category=" + category + ", user="
				+ user + ", methodPayment=" + methodPayment + "]";
	}

}
