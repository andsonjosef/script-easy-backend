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
public class SchemaSE implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@OneToMany(mappedBy = "schema", cascade = CascadeType.ALL)
	private List<TableSE> tables = new ArrayList<>();
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="database_id")
	private DataBaseSE database;

	public SchemaSE() {
		
	}
	
	public SchemaSE(Integer id, String name, DataBaseSE dataBase) {
		super();
		this.id = id;
		this.name = name;
		this.database = dataBase;
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

	
	public List<TableSE> getTables() {
		return tables;
	}

	public void setTables(List<TableSE> tables) {
		this.tables = tables;
	}

	public DataBaseSE getDatabase() {
		return database;
	}

	public void setDatabase(DataBaseSE database) {
		this.database = database;
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
		SchemaSE other = (SchemaSE) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
}
