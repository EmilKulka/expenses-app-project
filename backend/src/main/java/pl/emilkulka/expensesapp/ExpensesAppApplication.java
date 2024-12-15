package pl.emilkulka.expensesapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ExpensesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpensesAppApplication.class, args);
	}

}
