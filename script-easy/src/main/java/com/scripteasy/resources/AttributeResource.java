package com.scripteasy.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.scripteasy.DTO.AttributeDTO;
import com.scripteasy.DTO.AttributeNewDTO;
import com.scripteasy.domain.AttributeSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.services.AttributeService;

@RestController
@RequestMapping(value = "/attributes")
public class AttributeResource {

	@Autowired
	private AttributeService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<AttributeSE> find(@PathVariable Integer id) {

		AttributeSE obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}
	
	@RequestMapping(value = "/ref/{id}", method = RequestMethod.GET)
	public ResponseEntity<List<TableSE>>  findReferences(@PathVariable Integer id) {

		List<TableSE> listRef = service.findReferences(id);

		return ResponseEntity.ok().body(listRef);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody AttributeDTO objDto, @PathVariable Integer id) {
		AttributeSE obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody AttributeNewDTO objDto) {
		AttributeSE obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}
	
	

	

}
