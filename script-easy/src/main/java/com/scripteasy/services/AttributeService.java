package com.scripteasy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.TableSE;
import com.scripteasy.domain.AttributeSE;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.repositories.AttributeRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
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

	public Page<AttributeSE> findPage(Integer id, Integer page, Integer linesPerPage, String orderBy, String direction) {

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

}
