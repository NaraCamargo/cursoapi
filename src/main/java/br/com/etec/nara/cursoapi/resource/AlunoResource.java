package br.com.etec.nara.cursoapi.resource;

import br.com.etec.nara.cursoapi.model.Aluno;
import br.com.etec.nara.cursoapi.repository.AlunoRepository;
import br.com.etec.nara.cursoapi.repository.filter.AlunoFilter;
import br.com.etec.nara.cursoapi.repository.projections.AlunoDTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoResource {

    @Autowired
    private AlunoRepository alunoRepository;

    @GetMapping("/todos")
    public List<Aluno> listarTodosAlunos() {return alunoRepository.findAll();}

    @GetMapping()
    public Page<AlunoDTo> pesquisar(AlunoFilter alunoFilter, Pageable pageable) {
        return alunoRepository.filtrar(alunoFilter, pageable);
    }
}
