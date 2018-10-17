package com.scripteasy.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.SchemaSE;
import com.scripteasy.domain.TableSE;

@Repository
public interface TableRepository extends JpaRepository<TableSE, Integer> {
	
	
	@Transactional(readOnly=true)
	Page<TableSE> findDistinctByNameContainingAndSchemaIn(String name, SchemaSE schema, Pageable pageRequest);

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM TableSE obj WHERE obj.schema.id = :schemaId ORDER BY obj.name")
	public List<TableSE> findTables(@Param("schemaId") Integer table_id);
	
	@Transactional(readOnly=true)
	Page<TableSE> findBySchema(SchemaSE schema, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	TableSE findByNameContainingAndSchemaIn(String name,SchemaSE schema);
}
