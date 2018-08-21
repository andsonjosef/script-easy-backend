package com.scripteasy.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.scripteasy.services.DBService;
import com.scripteasy.services.EmailService;
import com.scripteasy.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean InstantiateDataBase() throws ParseException {

		dbService.IstantiateTestDataBase();
		return true;
	}

	@Bean
	public EmailService emailService() {
		return new MockEmailService();
	}

}