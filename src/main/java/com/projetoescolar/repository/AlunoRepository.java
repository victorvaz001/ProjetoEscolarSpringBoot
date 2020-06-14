package com.projetoescolar.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projetoescolar.model.Aluno;


@Repository
@Transactional
public interface AlunoRepository extends JpaRepository<Aluno, Long> {

}
