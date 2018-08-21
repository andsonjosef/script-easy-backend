package com.scripteasy.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scripteasy.domain.enums.Profile;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;
	private String email;

	@JsonIgnore
	private String password;

	@OneToMany(mappedBy = "dataBase", cascade = CascadeType.ALL)
	private List<DataBase> dataBases = new ArrayList<>();

	@ElementCollection
	@CollectionTable(name = "PHONE")
	private Set<String> phones = new HashSet<>();

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "PROFILE")
	private Set<Integer> profiles = new HashSet<>();

	public User() {
		addProfile(Profile.USER);

	}

	public User(long id, String name, String email, String tid, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		addProfile(Profile.USER);

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Profile> getProfiles() {
		return profiles.stream().map(x -> Profile.toEnum(x)).collect(Collectors.toSet());
	}

	public void addProfile(Profile profile) {
		profiles.add(profile.getCod());
	}

	public Set<String> getPhones() {
		return phones;
	}

	public void setPhones(Set<String> phones) {
		this.phones = phones;
	}

	public List<DataBase> getDataBases() {
		return dataBases;
	}

	public void setDataBases(List<DataBase> dataBases) {
		this.dataBases = dataBases;
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
		User other = (User) obj;
		if (id != other.id)
			return false;
		return true;
	}

	

}