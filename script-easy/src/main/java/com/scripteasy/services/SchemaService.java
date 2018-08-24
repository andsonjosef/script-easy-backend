package com.scripteasy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.repositories.SchemaRepository;
import com.scripteasy.services.excpetion.ObjectNotFoundException;



@Service
public class SchemaService {


	@Autowired
	private SchemaRepository repo;


	@Autowired
	UserService userService;

	@Autowired
	private DataBaseService databaseService;

	public SchemaSE find(Integer id) {
		Optional<SchemaSE> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + SchemaSE.class.getName()));
	}

	public Page<SchemaSE> search(String name, Integer id, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		DataBaseSE database = databaseService.find(id); 
		return repo.findDistinctByNameContainingAndDatabaseIn(name, database, pageRequest);

	}
	

	
}