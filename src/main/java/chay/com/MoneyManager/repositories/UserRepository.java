package chay.com.MoneyManager.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import chay.com.MoneyManager.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Collection<User> findByCustomerId(long customerId);

	User findByIdAndCustomerId(long userId, long customerId);
	
	User findByNameAndCustomerId(String userName, long customerId);
	
	@Query("select u from User u where u.active=true and u.customer.id = ?1")
	Collection<User> findActiveUserByCustomerId(long customerId);
}
