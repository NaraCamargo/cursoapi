package br.com.etec.nara.cursoapi.repository;

import br.com.etec.nara.cursoapi.model.Aluno;
import br.com.etec.nara.cursoapi.repository.aluno.AlunoRepositoryQuery;
import br.com.etec.nara.cursoapi.repository.filter.AlunoFilter;
import br.com.etec.nara.cursoapi.repository.projections.AlunoDTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long>, AlunoRepositoryQuery {
    Page<AlunoDTo> filtrar(AlunoFilter alunoFilter, Pageable pageable);
}
