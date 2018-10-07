package com.scripteasy.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.scripteasy.domain.DataBaseSE;
import com.scripteasy.domain.SchemaSE;

@Repository
public interface SchemaRepository extends JpaRepository<SchemaSE, Integer> {
	
	
	@Transactional(readOnly=true)
	Page<SchemaSE> findDistinctByNameContainingAndDatabaseIn(String name, List<DataBaseSE> bases, Pageable pageRequest);
	
	/*@Transactional(readOnly = true)
	@Query("SELECT obj FROM SchemaSE s WHERE obj.database.id = :databaseId ORDER BY obj.name")
	public List<SchemaSE> findSchemas(@Param("databaseId") Integer database_id);*/
	
	@Transactional(readOnly=true)
	Page<SchemaSE> findByDatabase(DataBaseSE base, Pageable pageRequest);
	
	 @Transactional(readOnly = true)
	@Query("SELECT sch FROM SchemaSE sch, DataBaseSE db WHERE sch.database.id = db.id and sch.database.id = :databaseId and db.user.id = :userId ORDER BY sch.name")
	public List<SchemaSE> findSchemas(@Param("databaseId") Integer database_id, @Param("userId") Integer user_id);
	 	
	 	/*@Query("SELECT DISTINCT obj FROM SchemaSE obj db INNER JOIN obj.databases cat WHERE obj.name LIKE %:name% AND cat IN :databases")
		Page<SchemaSE> search(@Param("name") String name, @Param("databases") List<DataBaseSE> databases, Pageable pageRequest); */


		@Transactional(readOnly = true)
		SchemaSE findByNameContainingAndDatabaseIn(String name,DataBaseSE dataBase);
}
