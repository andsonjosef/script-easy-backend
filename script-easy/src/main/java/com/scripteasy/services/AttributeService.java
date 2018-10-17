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

import com.scripteasy.DTO.AttributeDTO;
import com.scripteasy.DTO.AttributeNewDTO;
import com.scripteasy.domain.AttributeSE;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.repositories.AttributeRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
import com.scripteasy.services.excpetion.DataIntegrityException;
import com.scripteasy.services.excpetion.ObjectNotFoundException;

@Service
public class AttributeService {

	@Autowired
	private AttributeRepository repo;

	@Autowired
	UserService userService;

	@Autowired
	private TableService tableService;

	@Autowired
	private SchemaService schemaService;
	@Autowired
	private DataBaseService baseService;

	public List<AttributeSE> findByTable(Integer tableId) {
		return repo.findAttributes(tableId);
	}

	public AttributeSE find(Integer id) {
		Optional<AttributeSE> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Obect not found! Id: " + id + ", Type: " + AttributeSE.class.getName()));
	}
	
	public List<TableSE> findReferences(Integer schema_id) {
		return repo.findReferences(schema_id, "PRIMARY");
	}

	public Page<AttributeSE> findPage(Integer id, Integer page, Integer linesPerPage, String orderBy,
			String direction) {

		TableSE tb = tableService.find(id);
		SchemaSE sc = schemaService.find(tb.getSchema().getId());
		DataBaseSE db = baseService.find(sc.getDatabase().getId());

		UserSS userSS = UserSService.authenticated();
		if (userSS == null || userSS.getId() != db.getUser().getId()) {
			throw new AuthorizationException("Acess denied");
		}

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findByTable(tb, pageRequest);
	}

	public AttributeSE update(AttributeSE obj) {
		AttributeSE att = find(obj.getId());

		TableSE tb = tableService.find(att.getTable().getId());

		AttributeSE attr = repo.findByNameContainingAndTableIn(obj.getName(), tb);
		if (attr != null) {

			throw new DataIntegrityException("Existing Table!");

		} else {

			AttributeSE newObj = find(obj.getId());
			updateData(newObj, obj);
			return repo.save(newObj);

		}
	}
	
	public boolean hasPk(Integer id) {
		boolean result = false;
		
		TableSE tb = tableService.find(id);
		AttributeSE attr = repo.findByIndexAContainingAndTableIn("PRIMARY", tb);
		if(attr!= null) {
			result = true;
		}else {
			result = false;
		}
		
		
		return result;
	}

	@Transactional
	public AttributeSE insert(AttributeSE obj) {
		TableSE tb = tableService.find(obj.getTable().getId());
		
		AttributeSE att = repo.findByNameContainingAndTableIn(obj.getName(), tb);
		if (att != null) {

			throw new DataIntegrityException("Existing Attribute" + obj.getName() + "!");

		} else {

		}

		obj.setId(null);
		obj.setTable(tb);
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

	private void updateData(AttributeSE newObj, AttributeSE obj) {
		newObj.setName(obj.getName());

	}

	public AttributeSE fromDTO(AttributeDTO objDto) {
		new TableSE(objDto.getId(), objDto.getName(), null);
		return new AttributeSE(objDto.getId(), objDto.isAi(), objDto.getDefaultA(), objDto.getIndexA(), objDto.getName(),
				objDto.isNullA(), objDto.getSize(), objDto.getType(), objDto.getComment(), objDto.getReferencesTable(),
				null);

	}

	public AttributeSE fromDTO(AttributeNewDTO objDto) {
		TableSE tb = tableService.find(objDto.getTable().getId());
		AttributeSE att = new AttributeSE(null, objDto.isAi(), objDto.getDefaultA(), objDto.getIndexA(), objDto.getName(),
				objDto.isNullA(), objDto.getSize(), objDto.getType(), objDto.getComment(), objDto.getReferencesTable(),
				tb);
		
		return att;

	}

}
