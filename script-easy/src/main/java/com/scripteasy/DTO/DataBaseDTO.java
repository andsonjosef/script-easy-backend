package com.scripteasy.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.scripteasy.domain.DataBaseSE;

public class DataBaseDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Required Field")
	@Length(min = 5, max = 120, message = "The size must to be between 5 and 120 characters")
	private String name;


	public DataBaseDTO() {
		
	}

	public  DataBaseDTO(DataBaseSE obj){
		id = obj.getId();
		name = obj.getName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
