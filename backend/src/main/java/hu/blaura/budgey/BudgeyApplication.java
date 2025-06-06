package hu.blaura.budgey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BudgeyApplication {

	public static void main(String[] args) {
		SpringApplication.run(BudgeyApplication.class, args);
	}

}
