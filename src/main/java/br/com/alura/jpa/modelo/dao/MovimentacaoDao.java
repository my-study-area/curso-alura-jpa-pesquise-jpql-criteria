package br.com.alura.jpa.modelo.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.alura.jpa.modelo.MediaComData;

public class MovimentacaoDao {
	
	private EntityManager em;
	
	public MovimentacaoDao(EntityManager em) {
		this.em = em;
	}

	public List<MediaComData> getMediaDiariaDasMovimentacoes() {
		String jpqlMedia = "select new br.com.alura.jpa.modelo.MediaComData(avg(m.valor), day(m.data), month(m.data)) from Movimentacao m group by day(m.data), month(m.data), year(m.data)";
		TypedQuery<MediaComData> queryMedia = em.createQuery(jpqlMedia, MediaComData.class);
		return queryMedia.getResultList();
	}
	
	public BigDecimal getSomaDasMovimentacoes() {
		String jpqlSoma = "select sum(m.valor) from Movimentacao m";
		TypedQuery<BigDecimal> querySoma = em.createQuery(jpqlSoma, BigDecimal.class);
		return querySoma.getSingleResult();
	}
	
	public Double getMediaDasMovimentacoes() {
		String jpqlMedia = "select avg(m.valor) from Movimentacao m";
		TypedQuery<Double> queryMedia = em.createQuery(jpqlMedia, Double.class);
		return  queryMedia.getSingleResult();
	}

}
