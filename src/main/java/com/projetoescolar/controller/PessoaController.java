package com.projetoescolar.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.projetoescolar.model.Aluno;
import com.projetoescolar.model.Cursos;
import com.projetoescolar.model.Telefone;
import com.projetoescolar.repository.AlunoRepository;
import com.projetoescolar.repository.CursoRepository;
import com.projetoescolar.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private CursoRepository cursoRepository;

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
	@GetMapping("/detalhealuno/{idaluno}")
	public ModelAndView detalhesAluno(@PathVariable("idaluno") Long idaluno) {
		
		Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		 
		 ModelAndView modelAndView =  new ModelAndView("cadastro/detelhesaluno");
		 modelAndView.addObject("alunoobj", aluno.get());
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		 modelAndView.addObject("cursoobj", aluno.get().getCurso());
		 
		 return modelAndView;
	}
	
	//Metodo ao clicar no link Aluno e no botao novo aluno e link Alunos
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroaluno")
	public ModelAndView inicioCadastroAluno() {
		
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunoobj", new Aluno());
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		 return modelAndView;
	}
	

	//ao clicar em salvar, via botao do formulario
	@RequestMapping(method = RequestMethod.POST, value = "**/salvaraluno")
	public ModelAndView salvar(@Valid Aluno aluno, BindingResult bindingResult) {
		
		if(bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroaluno");
			Iterable<Aluno> alunoIt  = alunoRepository.findAll();
			modelAndView.addObject("alunos", alunoIt);
			modelAndView.addObject("alunoobj", aluno);
			
			List<String> msg = new ArrayList<String>(); /*Mostrar as validaçoes*/
			for(ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage()); /*vem das anotações do model Aluno @NotEmpety e outras*/
			}
			
			modelAndView.addObject("msg", msg);
			modelAndView.addObject("cursos", cursoRepository.findAll());
			return modelAndView;
		}
		
		
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
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		
		 return modelAndView;
	}
	
	//carrega o objeto pai
	@GetMapping("/telefones/{idaluno}")
	public ModelAndView telefones(@PathVariable("idaluno") Long idaluno) {
		
		 Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		 
		 ModelAndView modelAndView =  new ModelAndView("cadastro/telefones");
		 modelAndView.addObject("alunoobj", aluno.get());
		 modelAndView.addObject("telefones", telefoneRepository.getTelefones(idaluno));
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		 modelAndView.addObject("cursoobj", aluno.get().getCurso());
		
		 return modelAndView;
	}
	
	@PostMapping("**/addfonePessoa/{alunoid}")
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("alunoid") Long alunoid) {
		
		Aluno aluno = alunoRepository.findById(alunoid).get();
		
		if(telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {
			
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("alunoobj", aluno);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(alunoid));
			
			List<String> msg = new ArrayList<String>(); /*Mostrar as validaçoes*/
			if(telefone.getNumero().isEmpty()) {
				msg.add("Número deve ser inforfamado");
			}
			
			if (telefone.getTipo().isEmpty()) {
				msg.add("Tipo deve ser informado");
				
			}
			
			modelAndView.addObject("msg", msg);
			return modelAndView;
			
		}
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
		
		telefoneRepository.save(telefone);
		
		
		telefone.setAluno(aluno);
		
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
	     modelAndView.addObject("cursoobj", aluno.getCurso().getNome());
	     modelAndView.addObject("telefones", telefoneRepository.getTelefones(aluno.getId()));
		 return modelAndView;
	}

}
