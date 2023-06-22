package br.com.etec.nara.cursoapi.repository.aluno;

import br.com.etec.nara.cursoapi.model.Aluno;
import br.com.etec.nara.cursoapi.repository.filter.AlunoFilter;
import br.com.etec.nara.cursoapi.repository.projections.AlunoDTo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepositoryImpl implements AlunoRepositoryQuery{

    @PersistenceContext
    private EntityManager manager;

    @Override
    public Page<AlunoDTo> filtrar(AlunoFilter alunoFilter, Pageable pageable){

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<AlunoDTo> criteria = builder.createQuery(AlunoDTo.class);
        Root<Aluno> root = criteria.from(Aluno.class);

        criteria.select(builder.construct(AlunoDTo.class
        , root.get("id")
        , root.get("nomealuno")
        , root.get("cidade").get("nomecidade")
        , root.get("cidade").get("uf")
        , root.get("curso").get("nomecurso")
        ));

        Predicate[] predicates = criarRestricoes(alunoFilter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("nomealuno")));

        TypedQuery<AlunoDTo> query = manager.createQuery(criteria);
        adicionarRestricoesDePaginacao(query, pageable);

        return new PageImpl<>(query.getResultList(), pageable, total(alunoFilter));

    }

    private Predicate[] criarRestricoes(AlunoFilter alunoFilter, CriteriaBuilder builder, Root<Aluno> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isEmpty(alunoFilter.getNomealuno())){
            predicates.add(builder.like(builder.lower(root.get("nomealuno")),
                    "%" + alunoFilter.getNomealuno().toLowerCase() + "%"));
        }
        if (!StringUtils.isEmpty(alunoFilter.getNomecidade())){
            predicates.add(builder.like(builder.lower(root.get("cidade").get("nomecidade")),
                    "%" + alunoFilter.getNomecidade().toLowerCase() + "%" ));
        }

        if (!StringUtils.isEmpty(alunoFilter.getUf())){
            predicates.add(builder.equal(builder.lower(root.get("cidade").get("uf")),
                    alunoFilter.getUf().toLowerCase()));
        }
        if (!StringUtils.isEmpty(alunoFilter.getUf())){
            predicates.add(builder.like(builder.lower(root.get("curso").get("nomecurso")),
                    "%" + alunoFilter.getNomecurso().toLowerCase() + "%" ));
        }

        return predicates.toArray(new Predicate[predicates.size()]);
    }

    private void adicionarRestricoesDePaginacao(TypedQuery<?> query, Pageable pageable) {
        int paginaAtual = pageable.getPageNumber();
        int totalRegistrosPorPagina = pageable.getPageSize();
        int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

        query.setFirstResult(primeiroRegistroDaPagina);
        query.setMaxResults(totalRegistrosPorPagina);
    }

    private Long total(AlunoFilter alunoFilter) {

        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<Aluno> root = criteria.from(Aluno.class);

        Predicate[] predicates = criarRestricoes(alunoFilter, builder, root);
        criteria.where(predicates);
        criteria.orderBy(builder.asc(root.get("nomealuno")));

        criteria.select(builder.count(root));

        return manager.createQuery(criteria).getSingleResult();

    }

}
