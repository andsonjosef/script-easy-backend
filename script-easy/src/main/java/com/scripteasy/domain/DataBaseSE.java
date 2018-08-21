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
public class DataBaseSE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	@OneToMany(mappedBy = "database", cascade = CascadeType.ALL)
	private List<SchemaSE> schemas = new ArrayList<>();

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserSE user;

	public DataBaseSE() {

	}

	public DataBaseSE(long id, String name, UserSE user) {
		super();
		this.id = id;
		this.name = name;
		this.user = user;
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

	public UserSE getUser() {
		return user;
	}

	public void setUser(UserSE user) {
		this.user = user;
	}

	public List<SchemaSE> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<SchemaSE> schemas) {
		this.schemas = schemas;
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
		DataBaseSE other = (DataBaseSE) obj;
		if (id != other.id)
			return false;
		return true;
	}
}