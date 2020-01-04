package chay.com.MoneyManager.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import chay.com.MoneyManager.entities.ActionType;
import chay.com.MoneyManager.entities.Category;
import chay.com.MoneyManager.exception.NotPossibleRemoveObject;
import chay.com.MoneyManager.exception.ObjectAlreadyExists;
import chay.com.MoneyManager.exception.ObjectNotExists;
import chay.com.MoneyManager.repositories.CategoryRepository;

/**
 * The class implements all the functions in {@link AdminService}
 * 
 * @author Chay Mizrahi
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	/**
	 * Allows us access to category table in the DB.
	 */
	@Autowired
	private CategoryRepository categoryRepository;

	/**
	 * Contains the email of the admin.
	 */
	@Value("${money.manager.project.admin.email}")
	private String adminEmail;

	/**
	 * Contains the password of the admin.
	 */
	@Value("${money.manager.project.admin.password}")
	private String adminPassword;

	@Override
	public boolean performLogin(String email, String password) {
		return (adminEmail.equals(email) && adminPassword.equals(password)) ? true : false;
	}

	@Override
	public Category createCategory(Category category) throws ObjectAlreadyExists {
		category.setName(category.getName().trim());
		throwExceptionIfCategoryNameAlreadyExists(category.getName());
		categoryRepository.save(category);
		return category;
	}

	@Override
	public void removeCategory(long categoryId) throws ObjectNotExists, NotPossibleRemoveObject {
		throwExceptionIfCategoryNotExists(categoryId);
		throwExceptionIfCategoryAssociatedActions(categoryId);
		categoryRepository.delete(categoryRepository.findById(categoryId).get());
	}

	@Override
	public Category updateCategory(Category category) throws ObjectNotExists, ObjectAlreadyExists {
		throwExceptionIfCategoryNotExists(category.getId());
		if (isCategoryNameChanged(category)) {
			throwExceptionIfCategoryNameAlreadyExists(category.getName());
		}
		categoryRepository.save(category);
		return category;
	}

	@Override
	public Category getCategoryById(long categoryId) throws ObjectNotExists {
		throwExceptionIfCategoryNotExists(categoryId);
		Optional<Category> category = categoryRepository.findById(categoryId);
		return category.get();
	}

	@Override
	public Collection<Category> getAllCategory() {
		return categoryRepository.findAll();
	}

	/**
	 * The function loading the categories table in list of initial categories as
	 * long as the table is empty. The loading will happen automatic after finish
	 * build the application.
	 * 
	 * @param event published after the application build.
	 */
	@EventListener
	private void addCategoriesWhenApplicationReady(ApplicationReadyEvent event) {
		// Creates list of initial categories.
		ArrayList<Category> initialCategories = new ArrayList<>();
		// Loading some initial categories to the collection.
		initialCategories.add(new Category("בילוים", "מסעדות, ברים , מסיבות וכו..", ActionType.EXPENSE, true));
		initialCategories.add(new Category("קניות לבית", "אוכל, מוצרי ניקיון, מוצרי הגיינה", ActionType.EXPENSE, true));
		initialCategories.add(new Category("הוצאות דיור", "משכנתה, ארנונה, שכד ועוד..", ActionType.EXPENSE, true));
		initialCategories.add(new Category("חשבונות", "חשבון חשמל, גז, מים וכו..", ActionType.EXPENSE, true));
		initialCategories.add(new Category("ספורט ובריאות", "חדר כושר", ActionType.EXPENSE, true));
		initialCategories.add(new Category("תחבורה ציבורית", "אוטובוסים , מוניות ועוד..", ActionType.EXPENSE, true));
		initialCategories.add(new Category("אירועים ומתנות", "מתנה לחתונה, לימי הולדת..", ActionType.EXPENSE, true));
		initialCategories.add(new Category("תקשורת", "אינטרנט, כבלים..", ActionType.EXPENSE, true));
		initialCategories.add(new Category("הוצאות שונות", "הוצאה לא מוגדרת", ActionType.EXPENSE, true));
		initialCategories.add(new Category("הפקדה לחיסכון", "חיסכון", ActionType.EXPENSE, true));
		initialCategories.add(new Category("ביגוד והנעלה", "בגדים ונעליים", ActionType.EXPENSE, true));
		initialCategories.add(new Category("משכורת", "משכורת ראשית", ActionType.INCOME, true));
		initialCategories.add(new Category("הכנסה נוספת", "עבודות מזמנות", ActionType.INCOME, true));
		initialCategories.add(new Category("עזרה מההורים", "עזרה ממהורים", ActionType.INCOME, true));
		initialCategories.add(new Category("חיסכון שנפתח", "קרן השתלמות ועוד..", ActionType.INCOME, true));
		initialCategories.add(new Category("הכנסה שונות", "מה שלא ניתן לשייך לקטגוריה", ActionType.INCOME, true));
		// Gets all the categories that already exists in the DB
		Collection<Category> categoriesFormDB = categoryRepository.findAll();
		if (categoriesFormDB.isEmpty()) {
			// If the categories table empty add all the initial categories.
			categoryRepository.saveAll(initialCategories);
		}
	}

	/**
	 * the function throws the exception {@link ObjectAlreadyExists} if the received
	 * categoryName already belong to category.
	 * 
	 * @param categoryName must be string.
	 * @throws ObjectAlreadyExists if the received categoryName already belong to
	 *                             category.
	 */
	private void throwExceptionIfCategoryNameAlreadyExists(String categoryName) throws ObjectAlreadyExists {
		if (categoryRepository.findByName(categoryName) != null) {
			throw new ObjectAlreadyExists("Category", "name", categoryName);
		}
	}

	/**
	 * The function throws the expectation {@link NotPossibleRemoveObject} if the
	 * category with received id associated to some actions.
	 * 
	 * @param categoryId the ID of the expected category to be removed.
	 * @throws NotPossibleRemoveObject
	 */
	private void throwExceptionIfCategoryAssociatedActions(long categoryId) throws NotPossibleRemoveObject {
		Category category = categoryRepository.findById(categoryId).get();
		if (!category.getActions().isEmpty()) {
			throw new NotPossibleRemoveObject("Category");
		}
	}

	/**
	 * the function throws the exception {@link ObjectNotExists} if not exists in
	 * the DB {@link Category} with the received id as argument.
	 * 
	 * @param categoryId the ID of the expected category to be perform.
	 * @return true if the category exists.
	 * @throws ObjectNotExists if the category not exists.
	 */
	private void throwExceptionIfCategoryNotExists(long categoryId) throws ObjectNotExists {
		if (!categoryRepository.findById(categoryId).isPresent()) {
			throw new ObjectNotExists("Category", categoryId);
		}
	}

	/**
	 * The function returns {@code true} if the name of the category different from
	 * the name of the origin category.
	 * 
	 * @param updateCategory category with updated details of exists category.
	 * @return {@code true} if the name of the category different from the name of
	 *         the origin category else {@code false}.
	 */
	private boolean isCategoryNameChanged(Category updateCategory) {
		Category originCategory = categoryRepository.findById(updateCategory.getId()).get();
		return (originCategory.getName().equals(updateCategory.getName())) ? false : true;
	}

}
