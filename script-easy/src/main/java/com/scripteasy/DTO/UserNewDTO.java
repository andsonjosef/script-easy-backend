package com.scripteasy.DTO;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.scripteasy.services.validation.UserInsert;

@UserInsert
public class UserNewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "Required Field")
	@Length(min = 5, max = 120, message = "The size must to be between 5 and 120 characters")
	private String name;

	@NotEmpty(message = "Required Field")
	private String email;

	@NotEmpty(message = "Required Field")
	private String password;

	@NotEmpty(message = "Required Field")
	private String phone1;
	private String phone2;
	private String phone3;


	public UserNewDTO() {

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

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}


}