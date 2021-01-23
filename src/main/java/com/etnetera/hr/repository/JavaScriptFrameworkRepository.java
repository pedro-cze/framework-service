package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.entity.JavaScriptFramework;

import java.util.List;
import java.util.Optional;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera, Petr Kadlec
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    List<JavaScriptFramework> findAll();

    Optional<JavaScriptFramework> findByName(String name);

}
