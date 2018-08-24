package com.scripteasy.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;

@Repository
public interface TableRepository extends JpaRepository<TableSE, Integer> {
	
	
	@Transactional(readOnly=true)
	Page<TableSE> findDistinctByNameContainingAndSchemaIn(String name, SchemaSE schema, Pageable pageRequest);

}
