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

	


}
