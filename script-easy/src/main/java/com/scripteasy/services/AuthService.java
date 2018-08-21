package com.scripteasy.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.scripteasy.domain.UserSE;
import com.scripteasy.repositories.UserRepository;
import com.scripteasy.services.excpetion.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder be;

	@Autowired
	EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		UserSE user = userRepository.findByEmail(email);
		if (user == null) {
			throw new ObjectNotFoundException("Email not found");
		}

		String newPass = newPassword();
		user.setPassword(be.encode(newPass));
		userRepository.save(user);
		emailService.sendNewPasswordEmail(user, newPass);

	}

	private String newPassword() {
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {
			return (char) (rand.nextInt(10) + 48);
		} else if (opt == 1) {
			return (char) (rand.nextInt(26) + 65);
		} else if (opt == 2) {
			return (char) (rand.nextInt(26) + 97);
		}
		return 0;
	}
}
