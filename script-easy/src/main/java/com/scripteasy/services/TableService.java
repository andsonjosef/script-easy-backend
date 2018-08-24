package com.scripteasy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.repositories.TableRepository;
import com.scripteasy.services.excpetion.ObjectNotFoundException;



@Service
public class TableService {


	@Autowired
	private TableRepository repo;

	@Autowired
	UserService userService;

	@Autowired
	private  SchemaService schemaService;

	public TableSE find(Integer id) {
		Optional<TableSE> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + TableSE.class.getName()));
	}

	public Page<TableSE> search(String name, Integer id, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		SchemaSE schema = schemaService.find(id); 
		return repo.findDistinctByNameContainingAndSchemaIn(name, schema, pageRequest);

	}
	

	
}
