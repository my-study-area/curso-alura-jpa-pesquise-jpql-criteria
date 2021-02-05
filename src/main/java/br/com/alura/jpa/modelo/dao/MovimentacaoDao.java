package br.com.alura.jpa.modelo.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.alura.jpa.modelo.MediaComData;
import br.com.alura.jpa.modelo.Movimentacao;

public class MovimentacaoDao {
	
	private EntityManager em;
	
	public MovimentacaoDao(EntityManager em) {
		super();
		this.em = em;
	}

	public List<MediaComData> getMediaDiariaDasMovimentacoes() {
//		String jpqlMedia = "select new br.com.alura.jpa.modelo.MediaComData(avg(m.valor), day(m.data), month(m.data)) from Movimentacao m group by day(m.data), month(m.data), year(m.data)";
		TypedQuery<MediaComData> queryMedia = em.createNamedQuery("mediaDiariaMovimentacoes", MediaComData.class);
		return queryMedia.getResultList();
	}
	
	public BigDecimal getSomaDasMovimentacoes() {
//		String jpqlSoma = "select sum(m.valor) from Movimentacao m";
		TypedQuery<BigDecimal> querySoma = em.createNamedQuery("somaDoValorDasMovimentacoes", BigDecimal.class);
		return querySoma.getSingleResult();
	}
	
	public Double getMediaDasMovimentacoes() {
//		String jpqlMedia = "select avg(m.valor) from Movimentacao m";
		TypedQuery<Double> queryMedia = em.createNamedQuery("mediaDoValorDasMovimentacoes", Double.class);
		return  queryMedia.getSingleResult();
	}
	
	public List<Movimentacao> getMovimentacaoFiltradaPorData(Integer dia, Integer mes, Integer ano) {
		/* Exemplo sem o uso de CriteriaQuery
		 * 
		 * 
		String jpql = "selec m from Movimentacao m ";
		
		if (dia != null ) {
			jpql += "where day(m.data) ";
		}
		
		if (mes != null ) {
			jpql += "and month(m.data) ";
		}
		
		if (ano != null ) {
			jpql += "and year(m.data)";
		}
		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		return query.getResultList();
		*/
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> query = builder.createQuery(Movimentacao.class);
		Root<Movimentacao> root = query.from(Movimentacao.class);
		List<Predicate> predicates = new ArrayList<>();
		
		if (dia != null ) {
			//day(m.data)
			Expression<Integer> expression = builder.function("day", Integer.class, root.get("data"));
			Predicate predicate = builder.equal(expression, dia);
			predicates.add(predicate);
		}
		
		if (mes != null ) {
			//month(m.data)
			Expression<Integer> expression = builder.function("month", Integer.class, root.get("data"));
			Predicate predicate = builder.equal(expression, mes);
			predicates.add(predicate);
		}
		
		if (ano != null ) {
			//year(m.data)
			Expression<Integer> expression = builder.function("year", Integer.class, root.get("data"));
			Predicate predicate = builder.equal(expression, ano);
			predicates.add(predicate);
		}
		
		query.where(predicates.toArray(new Predicate[0]));
		TypedQuery<Movimentacao> typedQuery = em.createQuery(query);
		
		return typedQuery.getResultList();
	}

}
