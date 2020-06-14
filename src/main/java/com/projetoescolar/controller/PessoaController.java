package com.projetoescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.projetoescolar.model.Aluno;
import com.projetoescolar.repository.AlunoRepository;

@Controller
public class PessoaController {

	@Autowired
	private AlunoRepository alunoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastroaluno")
	public String inicio() {
		return "cadastro/cadastroaluno";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/salvaraluno")
	public String salvar(Aluno aluno) {
		alunoRepository.save(aluno);
		return "cadastro/cadastroaluno";
	}

}
