package br.com.alura.jpa.testes;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class TesteMediaDiariaDasMovimentacoes {

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("alura");
		EntityManager em = emf.createEntityManager();

//		String jpqlMedia = "select avg(m.valor) from Movimentacao m group by day(m.data), month(m.data), year(m.data)";
//		TypedQuery<Double> queryMedia = em.createQuery(jpqlMedia, Double.class);
//		List<Double> mediaDoValorDasMovimentacoes = queryMedia.getResultList();
//		
//		for (Double media : mediaDoValorDasMovimentacoes) {
//			System.out.println("A Média das movimentações é: "+ media);
//		}
		
		String jpqlMedia = "select avg(m.valor), day(m.data), month(m.data) from Movimentacao m group by day(m.data), month(m.data), year(m.data)";
		Query queryMedia = em.createQuery(jpqlMedia);
		List<Object[]> mediasDosValoresDasMovimentacoes = queryMedia.getResultList();
		
		for (Object[] resultado : mediasDosValoresDasMovimentacoes) {
			System.out.println("A Média das movimentações do dia " + resultado[1] + "/" + resultado[2] + "  é: "+ resultado[0]);
		}
	}

}
