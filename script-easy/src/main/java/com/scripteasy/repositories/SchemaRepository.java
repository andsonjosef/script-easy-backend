package com.scripteasy.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;

@Repository
public interface SchemaRepository extends JpaRepository<SchemaSE, Integer> {
	
	
	@Transactional(readOnly=true)
	Page<SchemaSE> findDistinctByNameContainingAndDatabaseIn(String name, DataBaseSE database, Pageable pageRequest);

}