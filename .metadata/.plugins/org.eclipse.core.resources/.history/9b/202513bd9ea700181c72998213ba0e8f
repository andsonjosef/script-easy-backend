package com.scripteasy.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.UserSE;
import com.scripteasy.domain.enums.Profile;
import com.scripteasy.repositories.DataBaseRepository;
import com.scripteasy.repositories.UserRepository;


@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private DataBaseRepository databaseRepository;
	


	public void IstantiateTestDataBase() throws ParseException {
		

		UserSE us1 = new UserSE (null, "Maria Silva", "maria@gmail.com",  pe.encode("123"));
		
		UserSE us2 = new UserSE(null, "Ana Costa", "ana@gmail.com", pe.encode("123"));
		us2.addProfile(Profile.ADMIN);

		userRepository.saveAll(Arrays.asList(us1, us2));
		
		DataBaseSE db1 = new DataBaseSE (null, "base1", us1);
		DataBaseSE db2 = new DataBaseSE (null, "base2", us1);
		DataBaseSE db3 = new DataBaseSE (null, "base1", us2);
		
		databaseRepository.saveAll(Arrays.asList(db1, db2, db3));


		

	}

}
