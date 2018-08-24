package com.scripteasy.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.TableSE;
import com.scripteasy.domain.AttributeSE;
import com.scripteasy.repositories.AttributeRepository;
import com.scripteasy.services.excpetion.ObjectNotFoundException;



@Service
public class AttributeService {


	@Autowired
	private AttributeRepository repo;

	@Autowired
	UserService userService;

	@Autowired
	private  TableService tableService;

	public AttributeSE find(Integer id) {
		Optional<AttributeSE> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFoundException("Obect not found! Id: " + id + ", Type: " + AttributeSE.class.getName()));
	}

	public Page<AttributeSE> search(String name, Integer id, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		TableSE table = tableService.find(id); 
		return repo.findDistinctByNameContainingAndTableIn(name, table, pageRequest);

	}
	

	
}