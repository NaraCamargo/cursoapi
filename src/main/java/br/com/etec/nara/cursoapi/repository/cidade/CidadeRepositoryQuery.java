package br.com.etec.nara.cursoapi.repository.cidade;

import br.com.etec.nara.cursoapi.model.Cidade;
import br.com.etec.nara.cursoapi.repository.filter.CidadeFilter;
import org.springframework.data.domain.Page;
import java.awt.print.Pageable;

public interface CidadeRepositoryQuery {

    public Page<Cidade> Filtrar(CidadeFilter cidadeFilter, Pageable pageable);


}
