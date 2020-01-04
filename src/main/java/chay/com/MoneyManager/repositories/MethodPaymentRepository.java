package chay.com.MoneyManager.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import chay.com.MoneyManager.entities.Action;
import chay.com.MoneyManager.entities.MethodPayment;

@Repository
public interface MethodPaymentRepository extends JpaRepository<MethodPayment, Long> {

	Collection<MethodPayment> findByCustomerId(long customerId);

	@Query("select mp from MethodPayment mp where mp.active=true and mp.customer.id = ?1")
	Collection<MethodPayment> getAllActiveByCustomerId(long customerId);

	MethodPayment findByNameAndCustomerId(String name, long customerId);
	
}
