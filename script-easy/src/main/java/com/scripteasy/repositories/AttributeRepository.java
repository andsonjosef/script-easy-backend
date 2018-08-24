package com.scripteasy.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.TableSE;
import com.scripteasy.domain.AttributeSE;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeSE, Integer> {
	
	
	@Transactional(readOnly=true)
	Page<AttributeSE> findDistinctByNameContainingAndTableIn(String name, TableSE table, Pageable pageRequest);

}
