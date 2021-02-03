package br.com.alura.jpa.testes;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class TesteSomaEMediaDasMovimentacoes {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
		EntityManager em = emf.createEntityManager();
		
		String jpqlSoma = "select sum(m.valor) from Movimentacao m";
		TypedQuery<BigDecimal> querySoma = em.createQuery(jpqlSoma, BigDecimal.class);
		BigDecimal somaDoValorDasMovimentacoes = querySoma.getSingleResult();
		
		String jpqlMedia = "select avg(m.valor) from Movimentacao m";
		TypedQuery<Double> queryMedia = em.createQuery(jpqlMedia, Double.class);
		Double mediaDoValorDasMovimentacoes = queryMedia.getSingleResult();
		
		System.out.println("A soma das movimentações é: "+ somaDoValorDasMovimentacoes);
		System.out.println("A Média das movimentações é: "+ mediaDoValorDasMovimentacoes);
	}

}
