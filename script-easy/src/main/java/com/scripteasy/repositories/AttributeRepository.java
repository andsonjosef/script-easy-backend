package com.scripteasy.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.AttributeSE;
import com.scripteasy.domain.TableSE;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeSE, Integer> {
	
	
	@Transactional(readOnly=true)
	Page<AttributeSE> findDistinctByNameContainingAndTableIn(String name, TableSE table, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	@Query("SELECT obj FROM AttributeSE obj WHERE obj.table.id = :tableId ORDER BY obj.name")
	public List<AttributeSE> findAttributes(@Param("tableId") Integer table_id);
	
	@Transactional(readOnly=true)
	Page<AttributeSE> findByTable(TableSE schema, Pageable pageRequest);
	
	@Transactional(readOnly = true)
	AttributeSE findByNameContainingAndTableIn(String name, TableSE table);
	
	@Transactional(readOnly = true)
	AttributeSE findByIndexAContainingAndTableIn(String index, TableSE table);
	
	@Query("SELECT DISTINCT tab FROM TableSE tab, AttributeSE att WHERE tab.schema.id = :schemaId and att.indexA LIKE %:index% ORDER BY tab.name")
	public List<TableSE> findReferences(@Param("schemaId") Integer schema_id, @Param("index") String index );

}
