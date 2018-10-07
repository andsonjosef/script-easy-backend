package com.scripteasy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.repositories.TableRepository;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.excpetion.AuthorizationException;
import com.scripteasy.services.excpetion.ObjectNotFoundException;



@Service
public class TableService {


	@Autowired
	private TableRepository repo;

	@Autowired
	UserService userService;

	@Autowired
	private  SchemaService schemaService;
	

	@Autowired
	private  DataBaseService baseService;
	
	
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
		if (userSS == null || userSS.getId() != db.getUser().getId() ) {
			throw new AuthorizationException("Acess denied");
		}
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findBySchema(sc, pageRequest);
	}
	

	
}
