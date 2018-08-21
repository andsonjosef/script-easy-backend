package com.scripteasy.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.scripteasy.domain.UserSE;

@Repository
public interface UserRepository extends JpaRepository<UserSE, Integer> {
	
	@Transactional(readOnly = true)
	UserSE findByEmail(String email);
}
