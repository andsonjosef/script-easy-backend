package com.scripteasy.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.UserSE;
import com.scripteasy.domain.enums.Profile;
import com.scripteasy.repositories.DataBaseRepository;
import com.scripteasy.repositories.SchemaRepository;
import com.scripteasy.repositories.UserRepository;

@Service
public class DBService {

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private DataBaseRepository databaseRepository;

	@Autowired
	private SchemaRepository schemaRepository;

	public void IstantiateTestDataBase() throws ParseException {

		UserSE us1 = new UserSE(null, "Maria Silva", "maria@gmail.com", pe.encode("123"));

		UserSE us2 = new UserSE(null, "Ana Costa", "ana@gmail.com", pe.encode("123"));
		us2.addProfile(Profile.ADMIN);

		DataBaseSE db1 = new DataBaseSE(null, "base1", us1);
		DataBaseSE db2 = new DataBaseSE(null, "base2", us1);
		DataBaseSE db3 = new DataBaseSE(null, "base1", us2);

		SchemaSE sc1 = new SchemaSE(null, "schema1", db1);
		SchemaSE sc2 = new SchemaSE(null, "schema2", db1);
		SchemaSE sc3 = new SchemaSE(null, "schema3", db2);

		us1.getDataBases().addAll(Arrays.asList(db1, db2));
		us2.getDataBases().addAll(Arrays.asList(db3));

		db1.getSchemas().addAll(Arrays.asList(sc1, sc2));
		db1.getSchemas().addAll(Arrays.asList(sc1, sc2));
		db1.getSchemas().addAll(Arrays.asList(sc1, sc2));

		userRepository.saveAll(Arrays.asList(us1, us2));
		databaseRepository.saveAll(Arrays.asList(db1, db2, db3));
		schemaRepository.saveAll(Arrays.asList(sc1, sc2, sc3));

	}

}
