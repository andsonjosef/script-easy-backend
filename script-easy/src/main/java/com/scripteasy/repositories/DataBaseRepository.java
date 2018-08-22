package com.scripteasy.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.UserSE;

@Repository
public interface DataBaseRepository extends JpaRepository<DataBaseSE, Integer> {
	
	@Transactional(readOnly=true)
	Page<DataBaseSE> findByUser(UserSE user, Pageable pageRequest);

}
