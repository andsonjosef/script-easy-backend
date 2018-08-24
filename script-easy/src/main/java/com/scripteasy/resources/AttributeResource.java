package com.scripteasy.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scripteasy.DTO.AttributeDTO;
import com.scripteasy.domain.AttributeSE;
import com.scripteasy.resources.utils.URL;
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

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<AttributeDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "table", defaultValue = "") String table,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Integer id = URL.decodeInt(table);
		String nameDecoded = URL.decodeParam(name);
		Page<AttributeSE> list = service.search(nameDecoded, id, page, linesPerPage, orderBy, direction);
		Page<AttributeDTO> listDto = list.map(obj -> new AttributeDTO(obj));

		return ResponseEntity.ok().body(listDto);

	}

}
