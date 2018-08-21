package com.scripteasy.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Attribute implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private boolean ai;
	private String defaultA;
	private String index;
	private String name;
	private boolean nullA;
	private Integer size;
	private String type;
	private String comment;
	private String ReferencesTable;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "table_id")
	private Table table;

	public Attribute() {

	}

	public Attribute(long id, boolean ai, String defaultA, String index, String name, boolean nullA, Integer size,
			String type, String comment, String referencesTable, Table table) {
		super();
		this.id = id;
		this.ai = ai;
		this.defaultA = defaultA;
		this.index = index;
		this.name = name;
		this.nullA = nullA;
		this.size = size;
		this.type = type;
		this.comment = comment;
		ReferencesTable = referencesTable;
		this.table = table;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
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
		return ReferencesTable;
	}

	public void setReferencesTable(String referencesTable) {
		ReferencesTable = referencesTable;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
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
		Attribute other = (Attribute) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
