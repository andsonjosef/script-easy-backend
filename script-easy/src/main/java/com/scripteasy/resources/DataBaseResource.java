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

import com.scripteasy.DTO.DataBaseDTO;
import com.scripteasy.DTO.DataBaseNewDTO;
import com.scripteasy.DTO.SchemaDTO;
import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.security.UserSS;
import com.scripteasy.services.DataBaseService;
import com.scripteasy.services.SchemaService;
import com.scripteasy.services.UserSService;
import com.scripteasy.services.excpetion.AuthorizationException;

@RestController
@RequestMapping(value = "/bases")
public class DataBaseResource {

	@Autowired
	private DataBaseService service;

	@Autowired
	private SchemaService schemaService;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<DataBaseDTO>> findBases() {

		UserSS userSS = UserSService.authenticated();
		if (userSS == null) {
			throw new AuthorizationException("Acess denied");
		}

		List<DataBaseSE> list = service.findByUser(userSS.getId());
		List<DataBaseDTO> listDto = list.stream().map(obj -> new DataBaseDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<DataBaseSE> find(@PathVariable Integer id) {

		DataBaseSE obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody DataBaseNewDTO objDto) {
		DataBaseSE obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody DataBaseDTO objDto, @PathVariable Integer id) {
		DataBaseSE obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {

		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "/{databaseId}/schemas", method = RequestMethod.GET)
	public ResponseEntity<List<SchemaDTO>> findSchemas(@PathVariable Integer databaseId) {
		List<SchemaSE> list = schemaService.findByDataBase(databaseId);
		List<SchemaDTO> listDto = list.stream().map(obj -> new SchemaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);
	}
	
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<DataBaseDTO>> findPage(
			@RequestParam(value="page", defaultValue ="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue ="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue ="name") String orderBy,
			@RequestParam(value="direction", defaultValue ="ASC") String direction) {
 		Page<DataBaseSE> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<DataBaseDTO> listDto = list.map(obj -> new DataBaseDTO(obj));
 		return ResponseEntity.ok().body(listDto);
  	}
	
	
	@RequestMapping(value = "/{databaseId}/schemas/page", method = RequestMethod.GET)
	public ResponseEntity<Page<SchemaDTO>> findSchemaPage(
			@PathVariable Integer databaseId,
			@RequestParam(value="page", defaultValue ="0") Integer page,
			@RequestParam(value="linesPerPage", defaultValue ="24") Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue ="name") String orderBy,
			@RequestParam(value="direction", defaultValue ="ASC") String direction
			) {
 		Page<SchemaSE> list = schemaService.findPage(databaseId, page, linesPerPage, orderBy, direction);
		Page<SchemaDTO> listDto = list.map(obj -> new SchemaDTO(obj));
 		return ResponseEntity.ok().body(listDto);
  	}

}
