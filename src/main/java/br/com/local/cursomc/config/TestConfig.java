package br.com.local.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.local.cursomc.services.DBService;
import br.com.local.cursomc.services.EmailService;
import br.com.local.cursomc.services.MockMailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBService dbService;
	
	@Bean
	public boolean instatiateDataBase() throws ParseException {
		dbService.instatianteTestDataBase();
		return true;
	}
	
	@Bean
	public EmailService emailService() {
		return new MockMailService();
	}


}
