package br.com.local.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.local.cursomc.services.DBService;
import br.com.local.cursomc.services.EmailService;
import br.com.local.cursomc.services.MockMailService;
import br.com.local.cursomc.services.SMTPEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBService dbService;
	
	@Value("spring.jpa.hibernate.ddl-auto")
	private String strategy;
	
	@Bean
	public boolean instatiateDataBase() throws ParseException {
		
		if(!"create".equals(strategy)) {
			return false;
		}
		dbService.instatianteTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new SMTPEmailService();
	}
}
