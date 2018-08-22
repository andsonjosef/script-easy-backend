package com.scripteasy.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.UserSE;
import com.scripteasy.domain.enums.Profile;
import com.scripteasy.repositories.UserRepository;


@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;
	


	public void IstantiateTestDataBase() throws ParseException {
		

		UserSE us1 = new UserSE (null, "Maria Silva", "maria@gmail.com",  pe.encode("123"));
		
		UserSE us2 = new UserSE(null, "Ana Costa", "ana@gmail.com", pe.encode("123"));
		us2.addProfile(Profile.ADMIN);

		userRepository.saveAll(Arrays.asList(us1, us2));

	}

}
