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

import com.scripteasy.DTO.TableDTO;
import com.scripteasy.DTO.TableNewDTO;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.repositories.TableRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
import com.scripteasy.services.excpetion.DataIntegrityException;
import com.scripteasy.services.excpetion.ObjectNotFoundException;

@Service
public class TableService {

	@Autowired
	private TableRepository repo;

	
	@Autowired
	UserService userService;

	@Autowired
	private SchemaService schemaService;

	@Autowired
	private DataBaseService baseService;

	public List<TableSE> findBySchema(Integer schemaId) {
		return repo.findTables(schemaId);
	}

	public TableSE find(Integer id) {
		Optional<TableSE> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + TableSE.class.getName()));
	}

	public Page<TableSE> findPage(Integer id, Integer page, Integer linesPerPage, String orderBy, String direction) {

		SchemaSE sc = schemaService.find(id);
		DataBaseSE db = baseService.find(sc.getDatabase().getId());

		UserSS userSS = UserSService.authenticated();
		if (userSS == null || userSS.getId() != db.getUser().getId()) {
			throw new AuthorizationException("Acess denied");
		}

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findBySchema(sc, pageRequest);
	}

	public TableSE update(TableSE obj) {
		TableSE tab = find(obj.getId());

		SchemaSE sc = schemaService.find(tab.getSchema().getId());

		TableSE tb = repo.findByNameContainingAndSchemaIn(obj.getName(), sc);
		if (tb != null) {

			throw new DataIntegrityException("Existing Table!");

		} else {

			TableSE newObj = find(obj.getId());
			updateData(newObj, obj);
			return repo.save(newObj);

		}
	}
	
	@Transactional
	public TableSE insert(TableSE obj) {
		TableSE tb = new TableSE();
		SchemaSE sc = new SchemaSE();
		sc = schemaService.find(obj.getSchema().getId());
		tb = repo.findByNameContainingAndSchemaIn(obj.getName(), sc);
		if(tb != null) {
			
			throw new DataIntegrityException("Existing Schema!");
			
		}else {
		
		} 
		
		obj.setId(null);
		obj.setSchema(sc);
		obj = repo.save(obj);

		return obj;
	}

	public void delete(Integer id) {

		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Can not delete because there are related orders");
		}
	}
	private void updateData(TableSE newObj, TableSE obj) {
		newObj.setName(obj.getName());

	}
	
	public TableSE fromDTO(TableDTO objDto) {
		return new TableSE(objDto.getId(), objDto.getName(), null);

	}
	
	public TableSE fromDTO(TableNewDTO objDto) {
		SchemaSE sc = new SchemaSE();
		sc = schemaService.find(objDto.getSchema().getId());
		TableSE table = new TableSE(null, objDto.getName(), sc);
		return table;

	}

}
