package com.scripteasy.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class AttributeSE implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private boolean ai;
	private String defaultA;
	private String index;
	private String name;
	private boolean nullA;
	private Integer size;
	private String type;
	private String comment;
	private String referencesTable;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "table_id")
	private TableSE table;

	public AttributeSE() {

	}

	public AttributeSE(Integer id, boolean ai, String defaultA, String index, String name, boolean nullA, Integer size,
			String type, String comment, String referencesTable, TableSE table) {
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
		this.referencesTable = referencesTable;
		this.table = table;
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
		return referencesTable;
	}

	public void setReferencesTable(String referencesTable) {
		this.referencesTable = referencesTable;
	}

	public TableSE getTable() {
		return table;
	}

	public void setTable(TableSE table) {
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
		AttributeSE other = (AttributeSE) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
