package com.scripteasy.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.scripteasy.domain.SchemaSE;

public class TableNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Required Field")
	@Length(min = 5, max = 120, message = "The size must to be between 5 and 120 characters")
	private String name;
	private SchemaSE schema;

	public TableNewDTO() {

	}

	
	public SchemaSE getSchema() {
		return schema;
	}


	public void setSchema(SchemaSE schema) {
		this.schema = schema;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
