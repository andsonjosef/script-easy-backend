package com.scripteasy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Schema implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	
	@OneToMany(mappedBy = "table", cascade = CascadeType.ALL)
	private List<Schema> tables = new ArrayList<>();
	
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="database_id")
	private DataBase database;

	public Schema() {
		
	}
	
	public Schema(long id, String name, DataBase dataBase) {
		super();
		this.id = id;
		this.name = name;
		this.database = dataBase;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Schema> getTables() {
		return tables;
	}

	public void setTables(List<Schema> tables) {
		this.tables = tables;
	}

	public DataBase getDatabase() {
		return database;
	}

	public void setDatabase(DataBase database) {
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
		Schema other = (Schema) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

	
}