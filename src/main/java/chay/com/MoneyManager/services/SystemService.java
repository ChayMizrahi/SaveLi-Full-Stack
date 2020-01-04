package chay.com.MoneyManager.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chay.com.MoneyManager.entities.CustomLogin;
import chay.com.MoneyManager.entities.Customer;
import chay.com.MoneyManager.entities.LoginType;
import chay.com.MoneyManager.exception.CustomerAlreadyExists;
import chay.com.MoneyManager.exception.InvalidLogin;
import chay.com.MoneyManager.repositories.CustomerRepository;

@Service
public class SystemService {

	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private AdminService adminService;
	@Autowired
	private CustomerService customerService;

	public CustomLogin login(String email, String password, LoginType loginType) throws InvalidLogin {

		if (loginType == LoginType.ADMIN) {
			if (adminService.performLogin(email, password)) {
				// If the login succeeded we return custom login with id 1 because there is only
				// one admin.
				return new CustomLogin(LoginType.ADMIN, 1);
			} else {
				throw new InvalidLogin();
			}
		} else if (loginType == LoginType.CUSTOMER) {
			if (customerService.performLogin(email.trim(), password.trim())) {
				return new CustomLogin(LoginType.CUSTOMER,
						customerRepository.findByEmailAndPassword(email, password).getId());
			} else {
				throw new InvalidLogin();
			}
		} else {
			throw new InvalidLogin();
		}
	}
	
	public CustomLogin registration(Customer customer) throws CustomerAlreadyExists {
		customer.setEmail(customer.getEmail().trim());
		Customer theCustomer = adminService.createCustomer(customer);
		CustomLogin customLogin = new CustomLogin(LoginType.CUSTOMER, theCustomer.getId());
		return customLogin;
	}
	
	public Collection<String> getAllEmailAddress() {
		Collection<String> emails = new ArrayList<String>();
		Collection<Customer> customers = customerRepository.findAll();
		for (Customer customer : customers) {
			emails.add(customer.getEmail());
		}
		return emails;
	}
	
	public boolean checkIfEmailAlreadyExists(String email) {
		if(customerRepository.findByEmail(email.trim()) == null) {
			return false;
		}else {
			return true;
		}
	}
}
