package com.scripteasy.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.DTO.SchemaDTO;
import com.scripteasy.DTO.SchemaNewDTO;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.repositories.SchemaRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
import com.scripteasy.services.excpetion.DataIntegrityException;
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
		UserSS user = UserSService.authenticated();
		if (user == null ) {

			throw new AuthorizationException("Acess denied");
		}
		Optional<SchemaSE> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + SchemaSE.class.getName()));
	}
	
	public List<SchemaSE> findByDataBase(Integer databaseId) {
		UserSS user = UserSService.authenticated();
		if (user == null ) {

			throw new AuthorizationException("Acess denied");
		}
		return repo.findSchemas(databaseId, user.getId());
	}
	
	public SchemaSE fromDTO(SchemaDTO objDto) {
		return new SchemaSE(objDto.getId(), objDto.getName(), null);

	}
	
	public SchemaSE fromDTO(SchemaNewDTO objDto) {
		System.out.println("nw " + objDto.getBase().getId());
		DataBaseSE db = new DataBaseSE();
		db = databaseService.find(objDto.getBase().getId());
		SchemaSE schema = new SchemaSE(null, objDto.getName(), db);
		return schema;

	}

	@Transactional
	public SchemaSE insert(SchemaSE obj) {
		SchemaSE sc = new SchemaSE();
		DataBaseSE db = new DataBaseSE();
		db = databaseService.find(obj.getDatabase().getId());
		sc = repo.findByNameContainingAndDatabaseIn(obj.getName(), db);
		if(sc != null) {
			
			throw new DataIntegrityException("Existing Schema!");
			
		}else {
		
		} 
		
		obj.setId(null);
		obj.setDatabase(db);
		obj = repo.save(obj);

		return obj;
	}

	

	
	public Page<SchemaSE> findPage(Integer id, Integer page, Integer linesPerPage, String orderBy, String direction) {
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		DataBaseSE base = databaseService.find(id);
		return repo.findByDatabase(base, pageRequest);
	}
	
	public SchemaSE update(SchemaSE obj) {
		SchemaSE se = find(obj.getId());
		
		DataBaseSE db = databaseService.find(se.getDatabase().getId());
				
		SchemaSE sc = repo.findByNameContainingAndDatabaseIn(obj.getName(), db);
		if(sc != null) {
			
			throw new DataIntegrityException("Existing Schema!");
			
		}else {

		SchemaSE newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
		
		}
	}

	public void delete(Integer id) {
	

		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete because there are related orders");
		}
	}
	

	private void updateData(SchemaSE newObj, SchemaSE obj) {
		newObj.setName(obj.getName());

	}
	
}
