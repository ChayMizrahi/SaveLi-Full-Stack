package chay.com.MoneyManager.repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import chay.com.MoneyManager.entities.ActionType;
import chay.com.MoneyManager.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

	Category findByName(String name);

	@Query("select c from Category c where c.active = true and c.type = ?1")
	Collection<Category> findActiveByType(ActionType type);

	Collection<Category> findByType(ActionType type);
	
}
