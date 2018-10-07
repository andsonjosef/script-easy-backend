package com.scripteasy.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.scripteasy.domain.AttributeSE;
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


}
