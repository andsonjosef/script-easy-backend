package com.scripteasy.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.scripteasy.security.UserSS;

public class UserSService {
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}

	}

}
