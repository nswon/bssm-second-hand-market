package usedmarket.usedmarket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UsedmarketApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsedmarketApplication.class, args);
	}

}
