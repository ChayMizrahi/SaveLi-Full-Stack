package chay.com.MoneyManager.repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import chay.com.MoneyManager.entities.Action;
import chay.com.MoneyManager.entities.ActionType;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

	Action findByIdAndMethodPaymentCustomerId(long actionId, long customerId);
	
	Collection<Action> findByMethodPaymentCustomerId(long customerId);

}
