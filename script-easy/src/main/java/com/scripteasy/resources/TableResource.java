package com.scripteasy.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.scripteasy.DTO.TableDTO;
import com.scripteasy.domain.TableSE;
import com.scripteasy.resources.utils.URL;
import com.scripteasy.services.TableService;

@RestController
@RequestMapping(value = "/tables")
public class TableResource {

	@Autowired
	private TableService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<TableSE> find(@PathVariable Integer id) {

		TableSE obj = service.find(id);

		return ResponseEntity.ok().body(obj);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<TableDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "schema", defaultValue = "") String schema,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		Integer id = URL.decodeInt(schema);
		String nameDecoded = URL.decodeParam(name);
		Page<TableSE> list = service.search(nameDecoded, id, page, linesPerPage, orderBy, direction);
		Page<TableDTO> listDto = list.map(obj -> new TableDTO(obj));

		return ResponseEntity.ok().body(listDto);

	}

}