package chay.com.MoneyManager.rest;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chay.com.MoneyManager.entities.Category;
import chay.com.MoneyManager.entities.Customer;
import chay.com.MoneyManager.exception.CustomerAlreadyExists;
import chay.com.MoneyManager.exception.ObjectNotExists;
import chay.com.MoneyManager.exception.MoneyManagerException;
import chay.com.MoneyManager.exception.ObjectAlreadyExists;
import chay.com.MoneyManager.services.AdminService;

@RestController
@RequestMapping("moneyManager/admin")
public class AdminRest {

	@Autowired
	private AdminService adminService;

	/**
	 * The function create HTTP request from type post that triggers the function
	 * createCategory in adminService. The path :
	 * http://localhost:8080/moneyManager/admin/category
	 * 
	 * @param category must be category. through as request body.
	 * @return JSON of the category that created.
	 * @throws ObjectAlreadyExists if there is already category with the
	 *                             category.name that received.
	 */
	@RequestMapping(path = "category", method = RequestMethod.POST)
	public Category createCategory(@RequestBody Category category) throws ObjectAlreadyExists {
		return adminService.createCategory(category);
	}



	/**
	 * The function create HTTP request from type put that triggers the function
	 * updateCategory in adminService. The path :
	 * http://localhost:8080/moneyManager/admin/category
	 * 
	 * @param Category must be JSON of category through the request body.
	 * @return JSON of the category after changes.
	 * @throws MoneyManagerException if the id in the path not equals to the
	 *                               category id.
	 */
	@RequestMapping(path = "category", method = RequestMethod.PUT)
	public Category updateCategory(@RequestBody Category category) throws MoneyManagerException {
		return adminService.updateCategory(category);
	}

	@RequestMapping(path="category/{id}", method= RequestMethod.DELETE)
	public void removeCategory(@PathVariable long id) throws ObjectNotExists, MoneyManagerException {
		this.adminService.removeCategory(id);
	}

	/**
	 * The function create HTTP request from type get that triggers the function
	 * getCategory in adminService. The path :
	 * http://localhost:8080/moneyManager/admin/category
	 * 
	 * @param id must be long
	 * @return category.
	 * @throws ObjectNotExists if there is not category with this id.
	 */
	@RequestMapping(path = "category/{id}", method = RequestMethod.GET)
	public Category getCategoryById(@PathVariable long id) throws ObjectNotExists {
		return adminService.getCategoryById(id);
	}

	/**
	 * The function create HTTP request from type get that triggers the function
	 * getAllCategory in adminService. The path :
	 * http://localhost:8080/moneyManager/admin/category
	 * 
	 * @return collection of category.
	 */
	@RequestMapping(path = "category", method = RequestMethod.GET)
	public Collection<Category> getAllCategory() {
		return adminService.getAllCategory();
	}

}
