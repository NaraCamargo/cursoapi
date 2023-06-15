package br.com.etec.nara.cursoapi.repository.aluno;

import br.com.etec.nara.cursoapi.repository.filter.AlunoFilter;
import br.com.etec.nara.cursoapi.repository.projections.AlunoDTo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AlunoRepositoryQuery {
    public Page<AlunoDTo> Filtrar(AlunoFilter alunoFilter, Pageable pageable);
}
