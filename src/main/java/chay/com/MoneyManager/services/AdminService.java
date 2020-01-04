package chay.com.MoneyManager.services;

import java.util.Collection;

import javax.validation.constraints.NotNull;

import chay.com.MoneyManager.entities.Category;
import chay.com.MoneyManager.exception.NotPossibleRemoveObject;
import chay.com.MoneyManager.exception.ObjectAlreadyExists;
import chay.com.MoneyManager.exception.ObjectNotExists;

/**
 * Contains all the logical functions needed for the application manager.
 * 
 * @author Chay Mizrahi
 *
 */
public interface AdminService {

	/**
	 * Checks whether the email and password match the administrator's email and
	 * password.
	 * 
	 * @param email
	 * @param password
	 * @return boolean true if the password and the email correct.
	 */
	boolean performLogin(@NotNull String email, @NotNull String password);

	/**
	 * The function inserts to the DB the {@link Category} received as argument.
	 * 
	 * @param category must contain all the fields except the id.
	 * @return Category after inserted to the DB.
	 * @throws ObjectAlreadyExists if the name of the new category already exists.
	 */
	Category createCategory(Category category) throws ObjectAlreadyExists;

	/**
	 * The function removes from the DB the  {@link Category}  with the id that equal to the
	 *  received id as long as no actions has recorded by the category.
	 * 
	 * @param categoryId of the category that will removed.
	 * @throws ObjectNotExists         if not exists category with the id received.
	 * @throws NotPossibleRemoveObject if actions has recorded by the category.
	 */
	void removeCategory(long categoryId) throws ObjectNotExists, NotPossibleRemoveObject;

	/**
	 * The function updates category details by the  {@link Category} received as argument.
	 * 
	 * @param category must contain all the fields.
	 * @return Category after the update in DB.
	 * @throws ObjectNotExists   if not exists category id that equal to the
	 *                             category id received as argument.
	 * @throws ObjectAlreadyExists if category name equal to other category name
	 *                             that already exists.
	 */
	Category updateCategory(Category category) throws ObjectNotExists, ObjectAlreadyExists;

	/**
	 * The function returns the {@link Category} with the id that received as argument.
	 * 
	 * @param categoryId the id of the category that will return.
	 * @return The category with the received id.
	 * @throws ObjectNotExists if not exists category with the id received.
	 */
	Category getCategoryById(long categoryId) throws ObjectNotExists;

	/**
	 * The function returns collection of all the categories from the DB.
	 * 
	 * @return {@link Category} collection.
	 */
	Collection<Category> getAllCategory();
}
