package chay.com.MoneyManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;

import chay.com.MoneyManager.exception.MoneyManagerException;

@SpringBootApplication
@ServletComponentScan
public class MoneyManagerApplication {

	public static void main(String[] args)
			throws MoneyManagerException {
		ConfigurableApplicationContext run = SpringApplication.run(MoneyManagerApplication.class, args);
		
	}

}
