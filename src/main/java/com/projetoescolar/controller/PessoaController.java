package com.projetoescolar.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projetoescolar.model.Aluno;
import com.projetoescolar.model.Telefone;
import com.projetoescolar.repository.AlunoRepository;
import com.projetoescolar.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	//Ao clicar no link(Cad.Aluno) /acessosistema no index
	@RequestMapping(method = RequestMethod.GET, value = "/acessosistema")
	public ModelAndView inicio() {
		
		ModelAndView andView = new ModelAndView("acesso/acessosistema");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		andView.addObject("alunoobj", new Aluno());
		
		return andView;
	}
	
	//Metodo ao clicar no link Aluno e no botao novo aluno e link Alunos
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroaluno")
	public ModelAndView inicioCadastroAluno() {
		
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunoobj", new Aluno());
		 return modelAndView;
	}
	

	//ao clicar em salvar, via botao do formulario
	@RequestMapping(method = RequestMethod.POST, value = "**/salvaraluno")
	public ModelAndView salvar(Aluno aluno) {
		
		alunoRepository.save(aluno);
		ModelAndView andView = new ModelAndView("acesso/acessosistema");
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
	
	//carrega o objeto pai
	@GetMapping("/telefones/{idaluno}")
	public ModelAndView telefones(@PathVariable("idaluno") Long idaluno) {
		
		 Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		 
		 ModelAndView modelAndView =  new ModelAndView("cadastro/telefones");
		 modelAndView.addObject("alunoobj", aluno.get());
		 modelAndView.addObject("telefones", telefoneRepository.getTelefones(idaluno));
		
		 return modelAndView;
	}
	
	@PostMapping("**/addfonePessoa/{alunoid}")
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("alunoid") Long alunoid) {
		
		Aluno aluno = alunoRepository.findById(alunoid).get();
		telefone.setAluno(aluno);
		
		telefoneRepository.save(telefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		modelAndView.addObject("alunoobj", aluno);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(alunoid));
		
		return modelAndView;
	}
	
	@GetMapping("/removeraluno/{idaluno}")
	public ModelAndView removerPessoa(@PathVariable("idaluno") Long idaluno) {
		
		 alunoRepository.deleteById(idaluno);
		
	     ModelAndView modelAndView =  new ModelAndView("acesso/acessosistema");
		 modelAndView.addObject("alunos", alunoRepository.findAll());
		 modelAndView.addObject("alunoobj", new Aluno());
		 
		 return modelAndView;
	}
	
	@GetMapping("/removertelefone/{idtelefone}")
	public ModelAndView removerTelefone(@PathVariable("idtelefone") Long idtelefone) {
		
		Aluno aluno = telefoneRepository.findById(idtelefone).get().getAluno();
		
		telefoneRepository.deleteById(idtelefone);
		
	     ModelAndView modelAndView =  new ModelAndView("cadastro/telefones");
	     modelAndView.addObject("alunoobj", aluno);
	     modelAndView.addObject("telefones", telefoneRepository.getTelefones(aluno.getId()));
		 return modelAndView;
	}

}
