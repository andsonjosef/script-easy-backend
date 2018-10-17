package com.scripteasy.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.scripteasy.domain.AttributeSE;

public class AttributeDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	private boolean ai;
	private String defaultA;
	private String indexA;

	@NotEmpty(message = "Name Required!")
	@Length(min = 5, max = 120, message = "The nama's  size must to be between 5 and 120 characters")
	private String name;

	private boolean nullA;

	@NotEmpty(message = "Size Required!")
	private Integer size;
	@NotEmpty(message = "Type Required")
	private String type;
	private String comment;
	private String referencesTable;

	public AttributeDTO() {

	}

	public AttributeDTO(AttributeSE obj) {
		id = obj.getId();
		ai = obj.isAi();
		defaultA = obj.getDefaulltA();
		indexA = obj.getIndexA();
		name = obj.getName();
		nullA = obj.isNullA();
		size = obj.getSize();
		type = obj.getType();
		comment = obj.getComment();
		referencesTable = obj.getReferencesTable();

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isAi() {
		return ai;
	}

	public void setAi(boolean ai) {
		this.ai = ai;
	}

	public String getDefaultA() {
		return defaultA;
	}

	public void setDefaultA(String defaultA) {
		this.defaultA = defaultA;
	}

	public String getIndexA() {
		return indexA;
	}

	public void setIndexA(String indexA) {
		this.indexA = indexA;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isNullA() {
		return nullA;
	}

	public void setNullA(boolean nullA) {
		this.nullA = nullA;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReferencesTable() {
		return referencesTable;
	}

	public void setReferencesTable(String referencesTable) {
		this.referencesTable = referencesTable;
	}

}
