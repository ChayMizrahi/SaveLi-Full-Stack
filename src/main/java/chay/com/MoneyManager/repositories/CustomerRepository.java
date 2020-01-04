package chay.com.MoneyManager.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import chay.com.MoneyManager.entities.Customer;

/**
 * The class allows us to use in built in functions that connection with the DB
 * and perform action that related the the object {@link Customer}.
 * 
 * @author Chay Mizrahi
 *
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	/**
	 * The function looking in the DB customer with the received email and password
	 * and if it find it returns him else it returns null.
	 * 
	 * @param email    must be string.
	 * @param password must be string.
	 * @return {@link Customer}
	 */
	Customer findByEmailAndPassword(String email, String password);

	/**
	 * The function looking in the DB customer with the received email and if it
	 * find it returns him else it returns null.
	 * 
	 * @param email must be string.
	 * @return {@link Customer}
	 */
	Customer findByEmail(String email);

}
