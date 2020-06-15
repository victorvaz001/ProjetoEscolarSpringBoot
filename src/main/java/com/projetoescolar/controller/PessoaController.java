package com.projetoescolar.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projetoescolar.model.Aluno;
import com.projetoescolar.repository.AlunoRepository;

@Controller
public class PessoaController {

	@Autowired
	private AlunoRepository alunoRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/acessosistema")
	public String inicio() {
		return "acesso/acessosistema";
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroaluno")
	public ModelAndView iniciosistema() {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastroaluno");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		
		return andView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/salvaraluno")
	public ModelAndView salvar(Aluno aluno) {
		alunoRepository.save(aluno);
		
		ModelAndView andView = new ModelAndView("cadastro/cadastroaluno");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		
		return andView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/listaAlunos")
	public ModelAndView alunos() {
		
		ModelAndView andView = new ModelAndView("cadastro/listaAlunos");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		return andView;
	}

}
