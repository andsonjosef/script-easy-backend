package com.scripteasy.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scripteasy.DTO.SchemaDTO;
import com.scripteasy.domain.SchemaSE;
import com.scripteasy.resources.utils.URL;
import com.scripteasy.services.SchemaService;

@RestController
@RequestMapping(value = "/schemas")
public class SchemaResource {

	@Autowired
	private SchemaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<SchemaSE> find(@PathVariable Integer id) {

		SchemaSE obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<SchemaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "database", defaultValue = "") String database,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Integer id = URL.decodeInt(database);
		String nameDecoded = URL.decodeParam(name);
		Page<SchemaSE> list = service.search(nameDecoded, id, page, linesPerPage, orderBy, direction);
		Page<SchemaDTO> listDto = list.map(obj -> new SchemaDTO(obj));

		return ResponseEntity.ok().body(listDto);

	}

}
