package dev.kamko.lnu_ass;

import dev.kamko.lnu_ass.oauth.google.GoogleClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = GoogleClientProperties.class)
public class LnuAssApplication {

	public static void main(String[] args) {
		SpringApplication.run(LnuAssApplication.class, args);
	}

}
