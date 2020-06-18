package com.projetoescolar.repository;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.projetoescolar.model.Cursos;

@Repository
@Transactional
public interface CursoRepository extends CrudRepository<Cursos, Long>{

	
	
}
