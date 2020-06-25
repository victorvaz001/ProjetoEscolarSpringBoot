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
import com.projetoescolar.model.Disciplina;
import com.projetoescolar.model.Professor;
import com.projetoescolar.model.StatusDisciplinaAluno;
import com.projetoescolar.model.Telefone;
import com.projetoescolar.model.TelefoneProfessor;
import com.projetoescolar.repository.AlunoRepository;
import com.projetoescolar.repository.CursoRepository;
import com.projetoescolar.repository.DisciplinaRepository;
import com.projetoescolar.repository.ProfessorRepository;
import com.projetoescolar.repository.TelefoneProfessorRepository;
import com.projetoescolar.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	@Autowired
	private DisciplinaRepository disciplinaRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private TelefoneProfessorRepository telefoneProfessorRepository;
	

	//Ao clicar no link(Cad.Aluno) será redirecionado para /acessosistema no index
	@RequestMapping(method = RequestMethod.GET, value = "/acessosistema")
	public ModelAndView inicio() {
		
		ModelAndView andView = new ModelAndView("acesso/acessosistema");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		andView.addObject("alunoobj", new Aluno());
		
		  Long contAlunosobj = alunoRepository.count();
		  andView.addObject("contAlunosobj", contAlunosobj);
		
		 Long contProfessoresobj = professorRepository.count();
		 andView.addObject("contProfessoresobj", contProfessoresobj);
		 
		 Long contCursosobj = cursoRepository.count();
		 andView.addObject("contCursosobj", contCursosobj);
		
		return andView;
	}
	
	//Metodo ao clicar no bota info da lista de alunos
	@GetMapping("/detalhealuno/{idaluno}")
	public ModelAndView detalhesAluno(@PathVariable("idaluno") Long idaluno) {
		
		Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		 
		 ModelAndView modelAndView =  new ModelAndView("detalhes/detalhesaluno");
		 modelAndView.addObject("alunoobj", aluno.get());
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		 modelAndView.addObject("cursoobj", aluno.get().getCurso());
		 modelAndView.addObject("disciplinas", disciplinaRepository.getDisiciplina(idaluno));
		 
		 return modelAndView;
	}
	
	@GetMapping("detalheprofessor/{idprofessor}")
	public ModelAndView detalheProfessor(@PathVariable("idprofessor") Long idprofessor) {
		
		Optional<Professor> professor = professorRepository.findById(idprofessor);
		
		ModelAndView modelAndView = new ModelAndView("detalhes/detalhesprofessor");
		modelAndView.addObject("professorobj", professor.get());
		
		return modelAndView;
		
	}
	
	//Metodo ao clicar no botao novo aluno
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroaluno")
	public ModelAndView inicioCadastroAluno() {
		
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunoobj", new Aluno());
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		 return modelAndView;
	}
	
	//Ao clicar no link Alunos no menu inicial a direita ou Link Aluno pagina inicial
	@RequestMapping(method = RequestMethod.GET, value = "/listadeAlunos")
	public ModelAndView inicioListaAlunos() {
		
		ModelAndView andView = new ModelAndView("listas/listaAlunos");
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		
		return andView;
	}
	
	
	//Ao clicar no link Professores no menu inicial a direita
	@RequestMapping(method = RequestMethod.GET, value = "/listadeProfessores")
	public ModelAndView inicioListaProfessores() {
		
		ModelAndView andView = new ModelAndView("listas/listadeProfessores");
		Iterable<Professor> professorIt  = professorRepository.findAll();
		andView.addObject("professores", professorIt);
		andView.addObject("professorobj", new Professor());
		
		return andView;
	}
	

	
	//Metodo ao clicar no link Aluno e no botao novo aluno e link Alunos
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroprofessor")
	public ModelAndView inicioCadastroProfessor() {
		
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroprofessor");
		 modelAndView.addObject("professorobj", new Professor());
		 //modelAndView.addObject("cursos", cursoRepository.findAll());
		 return modelAndView;
	}
	

	//ao clicar em salvar, via botao do formulario / POST
	@RequestMapping(method = RequestMethod.POST, value = "**/salvaraluno")
	public ModelAndView salvarAluno(@Valid Aluno aluno, BindingResult bindingResult) {
		
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
		
		ModelAndView andView = new ModelAndView("listas/listaAlunos");
		
		List<String> msgSave = new  ArrayList<>();
		List<String> msgEdit = new  ArrayList<>();
		
		if(aluno.getId() == null) {
			msgSave.add("Aluno salvo com sucesso!");
			andView.addObject("msgSave", msgSave);
			
		}else {
			msgEdit.add("Aluno editado com sucesso!");
			andView.addObject("msgEdit", msgEdit);
		}
		
		
		alunoRepository.save(aluno);
		Long contAlunosobj = alunoRepository.count();
		Long contProfessoresobj = professorRepository.count();
		
		
		Iterable<Aluno> alunoIt  = alunoRepository.findAll();
		andView.addObject("alunos", alunoIt);
		andView.addObject("alunoobj", new Aluno());
		andView.addObject("contAlunosobj", contAlunosobj);
		andView.addObject("contProfessoresobj", contProfessoresobj);
		
		
		
		
		return andView;
	}
	
	
	@GetMapping("/editaraluno/{idaluno}")
	public ModelAndView editarAluno(@PathVariable("idaluno") Long idaluno) {
		
		 Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		 
		 ModelAndView modelAndView =  new ModelAndView("cadastro/cadastroaluno");
		 modelAndView.addObject("alunoobj", aluno.get());
		 modelAndView.addObject("cursos", cursoRepository.findAll());
		
		 return modelAndView;
	}
	
	@GetMapping("/removeraluno/{idaluno}")
	public ModelAndView removerAluno(@PathVariable("idaluno") Long idaluno) {
		
		 alunoRepository.deleteById(idaluno);
		
	     ModelAndView modelAndView =  new ModelAndView("listas/listaAlunos");
		 modelAndView.addObject("alunos", alunoRepository.findAll());
		 modelAndView.addObject("alunoobj", new Aluno());
		 
		 Long contAlunosobj = alunoRepository.count();
		 modelAndView.addObject("contAlunosobj", contAlunosobj);
		 
			List<String> msgDelete = new ArrayList<String>();
		 
			msgDelete.add("Aluno removido com sucesso!");
			modelAndView.addObject("msgDelete", msgDelete);
		 
		 return modelAndView;
	}
	
	@GetMapping("/removerprofessor/{idprofessor}")
	public ModelAndView removerProfessor(@PathVariable("idprofessor") Long idprofessor) {
		
		professorRepository.deleteById(idprofessor);
		
		
		List<String> msgDelete = new ArrayList<String>();
		
		ModelAndView modelAndView = new ModelAndView("listas/listadeProfessores");
		modelAndView.addObject("professores", professorRepository.findAll());
		modelAndView.addObject("professorobj", new Professor());
		
		msgDelete.add("Professor removido com sucesso!");
		modelAndView.addObject("msgDelete", msgDelete);
		
		return modelAndView;
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value="**/salvarprofessor")
	public ModelAndView salvarProfessor(@Valid Professor professor) {
		
		List<String> msgSave = new ArrayList<String>();
		List<String> msgEdit = new ArrayList<String>();
		
		
		ModelAndView modelAndView = new ModelAndView("listas/listadeProfessores");
		
		
		if(professor.getId() == null) {
			msgSave.add("Professor salvo com sucesso!");
			modelAndView.addObject("msgSave", msgSave);
			
		}else {
			msgEdit.add("Professor editado com sucesso!");
			modelAndView.addObject("msgEdit", msgEdit);
		}

		professorRepository.save(professor);
		Iterable<Professor> professorIt = professorRepository.findAll();
		modelAndView.addObject("professores", professorIt);
		modelAndView.addObject("cursos", cursoRepository.findAll());
		modelAndView.addObject("professorobj", new Professor());
		
	
		return modelAndView;
		
	}
	
	
	@GetMapping("/editarprofessor/{idprofessor}")
	public ModelAndView editarProfesor(@PathVariable("idprofessor") Long idprofessor) {
		
		Optional<Professor> professor = professorRepository.findById(idprofessor);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroprofessor");
		modelAndView.addObject("professorobj", professor.get());
		
		
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
	
	@GetMapping("/telefonesProf/{idprofessor}")
	public ModelAndView telefonesProfessor(@PathVariable("idprofessor") Long idprofessor) {
		
		Optional<Professor> professor = professorRepository.findById(idprofessor);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefonesProfessor");
		
		modelAndView.addObject("professorobj", professor.get());
		modelAndView.addObject("telefones", telefoneProfessorRepository.getTelefoneProf(idprofessor));
		
		return modelAndView;
		
	}
	
	
	@PostMapping("**/addfonePessoa/{alunoid}")
	public ModelAndView addfonePessoa(Telefone telefone, @PathVariable("alunoid") Long alunoid) {
		
		Aluno aluno = alunoRepository.findById(alunoid).get();
		
		List<String> msg = new ArrayList<String>(); /*Mostrar as validaçoes*/
		if(telefone != null && telefone.getNumero().isEmpty() || telefone.getTipo().isEmpty()) {
				
			ModelAndView modelAndView = new ModelAndView("cadastro/telefones");
			modelAndView.addObject("alunoobj", aluno);
			modelAndView.addObject("telefones", telefoneRepository.getTelefones(alunoid));
			
			
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
		msg.add("Telefone cadastrado com sucesso!");
		modelAndView.addObject("msg", msg);
		
		telefone.setAluno(aluno);
		
		modelAndView.addObject("alunoobj", aluno);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(alunoid));
		return modelAndView;
	}
	
	@PostMapping("**/addfoneProfessor/{professorid}")
	public ModelAndView addfoneProfessor(TelefoneProfessor telefone, @PathVariable("professorid") Long professorid) {
		
		Professor professor = professorRepository.findById(professorid).get();
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefonesProfessor");
		
		List<String> msg = new ArrayList<String>();
		
		telefoneProfessorRepository.save(telefone);
		msg.add("Telefone salvo com sucesso");
		modelAndView.addObject("msg", msg);
		
		telefone.setProfessor(professor);
		modelAndView.addObject("professorobj", professor);
		modelAndView.addObject("telefones", telefoneProfessorRepository.getTelefoneProf(professorid));
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
	     
	     List<String> msgDelete = new ArrayList<String>();
		
		 msgDelete.add("Telefone removido com sucesso");
		 modelAndView.addObject("msgDelete", msgDelete);
	     
		 return modelAndView;
	}
	
	@GetMapping("/removertelefoneProf/{idtelefone}")
	public ModelAndView removertelefoneProf(@PathVariable("idtelefone") Long idtelefone) {
		
		Professor professor = telefoneProfessorRepository.findById(idtelefone).get().getProfessor();
		
		telefoneProfessorRepository.deleteById(idtelefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefonesProfessor");
		modelAndView.addObject("professorobj", professor);
		modelAndView.addObject("telefones", telefoneProfessorRepository.getTelefoneProf(professor.getId()));
		
		List<String> msgDelete = new ArrayList<String>();
		
		msgDelete.add("Telefone removido com sucesso");
		modelAndView.addObject("msgDelete", msgDelete);
		
		return modelAndView;
		
	}
	
	@GetMapping("/disciplinas/{idaluno}")
	public ModelAndView disciplinas(@PathVariable ("idaluno") Long idaluno) {
		
		Optional<Aluno> aluno = alunoRepository.findById(idaluno);
		
		ModelAndView modelAndView =  new ModelAndView("cadastro/disciplinas");
		 modelAndView.addObject("alunoobj", aluno.get());
		modelAndView.addObject("disciplinas", disciplinaRepository.getDisiciplina(idaluno));
		modelAndView.addObject("cursos", cursoRepository.findAll());
		
		 return modelAndView;
	}
	
	
	@PostMapping("**/adddisciplinaaluno/{alunoid}")
	public ModelAndView adddisciplinaaluno(Disciplina disiciplina, @PathVariable("alunoid") Long alunoid) {
		
		Aluno aluno = alunoRepository.findById(alunoid).get();
		List<String> msg = new ArrayList<String>(); /*Mostrar as validaçoes*/
		if(disiciplina != null && disiciplina.getNota1() >= 100 || disiciplina.getNota2() >=100 ) {
			
			
			ModelAndView modelAndView = new ModelAndView("cadastro/disciplinas");
			modelAndView.addObject("alunoobj", aluno);
			modelAndView.addObject("disciplinas", disciplinaRepository.getDisiciplina(alunoid));
			
		
		
		if(disiciplina.getNota1() >= 100 || disiciplina.getNota2() >= 100) {
			msg.add("Nota 1 e 2 nao podem ultrapassar os 100 pontos");
		}
		
		modelAndView.addObject("msg", msg);
		return modelAndView;
			
		}
		
		double total = disiciplina.getNota1() + disiciplina.getNota2();
		StatusDisciplinaAluno result;
		double media;
		
		media = total / 2;
		
		

			if (media >= 30.0 || media <= 0.0) {
				if (media != 0.0) {
					if (media >= 50.00) {
						result = StatusDisciplinaAluno.APROVADO;
					} else {
						result = StatusDisciplinaAluno.RECUPERAÇÃO;
					}

				} else {
					result = StatusDisciplinaAluno.SEGUNDA_CHAMADA;
				}
			} else {
				result = StatusDisciplinaAluno.REPROVADO;
			}
			
			
		ModelAndView modelAndView = new ModelAndView("cadastro/disciplinas");

		disiciplina.setTotal(total);
		disiciplina.setMedia(media);
		disiciplina.setStatusDisciplinaAluno(result);
		
		disciplinaRepository.save(disiciplina);
		msg.add("Disciplina cadastrada com sucesso!");
		modelAndView.addObject("msg", msg);
		
		disiciplina.setAluno(aluno);
		
		modelAndView.addObject("alunoobj", aluno);
		modelAndView.addObject("disciplinas", disciplinaRepository.getDisiciplina(alunoid));
		
		Long contCursos = cursoRepository.count();
		System.out.println("Total de cursos disponiveis = " + contCursos);
	
		
		return modelAndView;
		
	}
	
	@GetMapping("/removerdisciplina/{iddiciplina}")
	public ModelAndView removerdisciplina(@PathVariable ("iddiciplina") Long iddiciplina) {
		
		Aluno aluno = disciplinaRepository.findById(iddiciplina).get().getAluno();
		disciplinaRepository.deleteById(iddiciplina);
		
		
		ModelAndView modelAndView =  new ModelAndView("cadastro/disciplinas");
		modelAndView.addObject("alunoobj", aluno);
		modelAndView.addObject("cursoobj", aluno.getCurso().getNome());
		modelAndView.addObject("disciplinas", disciplinaRepository.getDisiciplina(aluno.getId()));
		     
		return modelAndView;
		
	}

}
