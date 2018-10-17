package com.scripteasy.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.scripteasy.DTO.AttributeDTO;
import com.scripteasy.DTO.TableDTO;
import com.scripteasy.DTO.TableNewDTO;
import com.scripteasy.domain.AttributeSE;
import com.scripteasy.domain.TableSE;
import com.scripteasy.services.AttributeService;
import com.scripteasy.services.TableService;

@RestController
@RequestMapping(value = "/tables")
public class TableResource {

	@Autowired
	private TableService service;
	
	@Autowired
	private AttributeService attributeService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TableSE> find(@PathVariable Integer id) {

		TableSE obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}

	
	@RequestMapping(value="/{tableId}/attributes", method = RequestMethod.GET)
	public ResponseEntity<List<AttributeDTO>> findAttributes(@PathVariable Integer tableId) {
 		List<AttributeSE> list = attributeService.findByTable(tableId);
		List<AttributeDTO> listDto = list.stream().map(obj -> new AttributeDTO(obj)).collect(Collectors.toList());
 		return ResponseEntity.ok().body(listDto);
 	}
	
	
	@RequestMapping(value="/{tableId}/attributes/pk", method = RequestMethod.GET)
	public ResponseEntity<Boolean> findPkAttributes(@PathVariable Integer tableId) {
 		boolean result = attributeService.hasPk(tableId);
 		return ResponseEntity.ok().body(result);
 	}
	
	@RequestMapping(value = "/{tableId}/attributes/page", method = RequestMethod.GET)
	public ResponseEntity<Page<AttributeDTO>> findSchemaPage(
			@PathVariable Integer tableId,
			@RequestParam(value="page", defaultValue ="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue ="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue ="name") String orderBy,
			@RequestParam(value="direction", defaultValue ="ASC") String direction
			) {
 		Page<AttributeSE> list = attributeService.findPage(tableId, page, linesPerPage, orderBy, direction);
		Page<AttributeDTO> listDto = list.map(obj -> new AttributeDTO(obj));
 		return ResponseEntity.ok().body(listDto);
  	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody TableDTO objDto, @PathVariable Integer id) {
		TableSE obj = service.fromDTO(objDto);
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
	public ResponseEntity<Void> insert(@Valid @RequestBody TableNewDTO objDto) {
		TableSE obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}


}
