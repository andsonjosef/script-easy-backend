package com.scripteasy.services;

import java.text.ParseException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.AttributeSE;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.domain.UserSE;
import com.scripteasy.domain.enums.Profile;
import com.scripteasy.repositories.AttributeRepository;
import com.scripteasy.repositories.DataBaseRepository;
import com.scripteasy.repositories.SchemaRepository;
import com.scripteasy.repositories.TableRepository;
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
	
	@Autowired
	private TableRepository tableRepository;
	
	@Autowired
	private AttributeRepository attributeRepository;

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
		
		TableSE tb1 = new TableSE(null, "table1", sc1);
		TableSE tb2 = new TableSE(null, "table2", sc1);
		TableSE tb3 = new TableSE(null, "table3", sc2);
		
		AttributeSE att1 = new AttributeSE(null, true, "", "PRIMARY", "id", false, 30, "INT", "", null, tb1);
		AttributeSE att2 = new AttributeSE(null, false, "", "", "name", false, 30, "CHAR", "", null, tb1);
		AttributeSE att3 = new AttributeSE(null, false, "", "FOREIGN", "table1_id", false, 30, "int", "", "table1", tb2);
		AttributeSE att4 = new AttributeSE(null, false, "", "", "name", false, 30, "int", "", "table2", tb2);

		us1.getDataBases().addAll(Arrays.asList(db1, db2));
		us2.getDataBases().addAll(Arrays.asList(db3));

		db1.getSchemas().addAll(Arrays.asList(sc1, sc2));
		db1.getSchemas().addAll(Arrays.asList(sc1, sc2));
		db1.getSchemas().addAll(Arrays.asList(sc1, sc2));
		
		sc1.getTables().addAll(Arrays.asList(tb1, tb2));
		sc2.getTables().addAll(Arrays.asList(tb3));
		
		tb1.getAttributes().addAll(Arrays.asList(att1,att2));
		tb2.getAttributes().addAll(Arrays.asList(att3,att4));

		userRepository.saveAll(Arrays.asList(us1, us2));
		databaseRepository.saveAll(Arrays.asList(db1, db2, db3));
		schemaRepository.saveAll(Arrays.asList(sc1, sc2, sc3));
		tableRepository.saveAll(Arrays.asList(tb1,tb2,tb3));
		attributeRepository.saveAll(Arrays.asList(att1,att2,att3,att4));

	}

}
