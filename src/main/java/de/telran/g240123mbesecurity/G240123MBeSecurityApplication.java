package de.telran.g240123mbesecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class G240123MBeSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(G240123MBeSecurityApplication.class, args);
	}

}
