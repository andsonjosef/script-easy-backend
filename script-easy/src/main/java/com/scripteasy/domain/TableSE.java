package com.scripteasy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class TableSE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	@OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
	private List<AttributeSE> attributes = new ArrayList<>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "schema_id")
	private SchemaSE schema;

	public TableSE() {

	}

	public TableSE(Integer id, String name, SchemaSE schema) {
		super();
		this.id = id;
		this.name = name;
		this.schema = schema;
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

	@JsonIgnore
	public List<AttributeSE> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeSE> attributes) {
		this.attributes = attributes;
	}

	public SchemaSE getSchema() {
		return schema;
	}

	public void setSchema(SchemaSE schema) {
		this.schema = schema;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableSE other = (TableSE) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
