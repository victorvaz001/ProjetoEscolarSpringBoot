package com.projetoescolar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projetoescolar.model.Aluno;
import com.projetoescolar.repository.AlunoRepository;

@Controller
public class PessoaController {

	@Autowired
	private AlunoRepository alunoRepository;

	//Ao clicar no link /acessosistema
	@RequestMapping(method = RequestMethod.GET, value = "/acessosistema")
	public String inicio() {
		return "acesso/acessosistema";
	}
	
	//Metodo ao clicar em /cadastroaluno
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroaluno")
	public ModelAndView inicioCadastroAluno() {
		
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunoobj", new Aluno());
		 return modelAndView;
	}
	

	//ao clicar em salvar, via botao do formulario formulario
	@RequestMapping(method = RequestMethod.POST, value = "**/salvaraluno")
	public ModelAndView salvar(Aluno aluno) {
		
		alunoRepository.save(aluno);
		ModelAndView andView = new ModelAndView("cadastro/cadastroaluno");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		andView.addObject("alunoobj", new Aluno());
		return andView;
	}
	
	//Redireciona para cadestro de alunos com a lista de alunos
	@RequestMapping(method = RequestMethod.GET, value = "/listaAlunosCadastro")
	public ModelAndView alunos() {
		
		ModelAndView andView = new ModelAndView("cadastro/cadastroaluno");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		andView.addObject("alunoobj", new Aluno());
		return andView;
	}
	
	//redireciona para lsita customizada de alunos
	@RequestMapping(method = RequestMethod.GET, value = "/listaAlunos")
	public ModelAndView alunos2() {
		
		ModelAndView andView = new ModelAndView("cadastro/listaAlunos");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		andView.addObject("alunoobj", new Aluno());
		return andView;
	}
	
	
	@GetMapping("/editaraluno/{idaluno}")
	public ModelAndView editar(@PathVariable("idaluno") Long idaluno) {
		
		 Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		 
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunoobj", aluno.get());
		
		 return modelAndView;
	}
	
	@GetMapping("/removeraluno/{idaluno}")
	public ModelAndView remover(@PathVariable("idaluno") Long idaluno) {
		
		 alunoRepository.deleteById(idaluno);
		
	     ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunos", alunoRepository.findAll());
		 modelAndView.addObject("alunoobj", new Aluno());
		 
		 return modelAndView;
	}

}
