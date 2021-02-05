package br.com.alura.jpa.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import br.com.alura.jpa.modelo.Movimentacao;

public class TesteSomaEMediaDasMovimentacoes {

	public static void main(String[] args) {

//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
//		EntityManager em = emf.createEntityManager();
		
//		String jpqlSoma = "select sum(m.valor) from Movimentacao m";
//		TypedQuery<BigDecimal> querySoma = em.createQuery(jpqlSoma, BigDecimal.class);
//		BigDecimal somaDoValorDasMovimentacoes = querySoma.getSingleResult();
		
		
//		String jpqlMedia = "select avg(m.valor) from Movimentacao m";
//		TypedQuery<Double> queryMedia = em.createQuery(jpqlMedia, Double.class);
//		Double mediaDoValorDasMovimentacoes = queryMedia.getSingleResult();
		
//		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
//		EntityManager em = emf.createEntityManager();
//		MovimentacaoDao dao = new MovimentacaoDao(em);
//		BigDecimal somaDoValorDasMovimentacoes = dao.getSomaDasMovimentacoes();
//		Double mediaDoValorDasMovimentacoes = dao.getMediaDasMovimentacoes();
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
		EntityManager em = emf.createEntityManager();
		
		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<BigDecimal> query = builder.createQuery(BigDecimal.class);
		Root<Movimentacao> root = query.from(Movimentacao.class);
		Expression<BigDecimal> sum = builder.sum(root.get("valor"));
		query.select(sum);
		TypedQuery<BigDecimal> typedQuery = em.createQuery(query);
		BigDecimal somaDoValorDasMovimentacoes = typedQuery.getSingleResult();
		
		CriteriaBuilder builderMedia = em.getCriteriaBuilder();
		CriteriaQuery<Double> queryMedia = builder.createQuery(Double.class);
		Root<Movimentacao> rootMedia = queryMedia.from(Movimentacao.class);
		Expression<Double> avg = builderMedia.avg(rootMedia.get("valor"));
		queryMedia.select(avg);
		TypedQuery<Double> typedQueryMedia = em.createQuery(queryMedia);
		Double mediaDoValorDasMovimentacoes = typedQueryMedia.getSingleResult();
		
		System.out.println("A soma das movimentações é: "+ somaDoValorDasMovimentacoes);
		System.out.println("A Média das movimentações é: "+ mediaDoValorDasMovimentacoes);
	}

}
