package br.com.alura.carteira.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import ch.qos.logback.core.joran.spi.NoAutoStart;


//@Repository -> acesso aos dados da aplicação - para spring conhecer a classe e conseguir injetar EM 
public interface TransacaoRepository extends JpaRepository<Transacao, Long>{
	
	
	
	/*
	@Autowired
	private EntityManager em;

	//Metodo recebe Transacao e insere no BD
	public void salvar (Transacao transacao) {
		em.persist(transacao);
	}
	
	//Fazer select no BD
	public List<Transacao> listar(){
		return em.createQuery("select t from Transacao t",Transacao.class)
				 .getResultList();
	}
	*/
}