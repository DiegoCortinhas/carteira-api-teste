package br.com.alura.carteira.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.alura.carteira.dto.ItemCarteiraDto;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;

@ExtendWith(SpringExtension.class)
@DataJpaTest  //traz tudo que é necessário para lidar com o BD, por padrao nao usa o BD da aplicacao
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
class TransacaoRepositoryTest {

	@Autowired
	private TransacaoRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	void deveriaRetornarRelatorioDeCarteiraDeInvestimentos() {
		
		Usuario usuario = new Usuario("Rafaela", "rafa@email.com", "123456");
		em.persist(usuario);
		
		Transacao t1 = new Transacao("ITSA4", 
				new BigDecimal("10.00"),
				50, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t1);
		
		Transacao t2 = new Transacao("BBSE3", 
				new BigDecimal("22.80"),
				80, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t2);
		
		Transacao t3 = new Transacao("EGIE3", 
				new BigDecimal("40.00"),
				25, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t3);
		
		Transacao t4 = new Transacao("ITSA4", 
				new BigDecimal("11.20"),
				40, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t4);
		
		Transacao t5 = new Transacao("SAPR4", 
				new BigDecimal("04.02"),
				120, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t5);
		
		/*Transacao t6 = new Transacao("PRIO3", 
				new BigDecimal("28.50"),
				100, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t6);
		
		Transacao t7 = new Transacao("SHUL4", 
				new BigDecimal("17.50"),
				100, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t7);
		
		Transacao t8 = new Transacao("PETR4", 
				new BigDecimal("40.20"),
				200, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t8);*/
		
		List<ItemCarteiraDto> relatorio = repository.relatorioCarteiraDeInvestimentos();
		Assertions
		.assertThat(relatorio)
		.hasSize(4)
		.extracting(ItemCarteiraDto::getTicker,ItemCarteiraDto::getQuantidade,ItemCarteiraDto::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("ITSA4",90l,new BigDecimal("28.57")),
				Assertions.tuple("BBSE3",80l,new BigDecimal("25.40")),
				Assertions.tuple("EGIE3",25l,new BigDecimal("7.94")),
				Assertions.tuple("SAPR4",120l,new BigDecimal("38.10"))
				/*Assertions.tuple("PRIO3",100l,0.13986),
				Assertions.tuple("SHUL4",100l,0.13986),
				Assertions.tuple("PETR4",200l,0.27972)*/
				);
		
		//assertEquals(7, relatorio.size());
		//assertNotNull(relatorio);
		//assertTrue(relatorio.isEmpty());
	}
	
	
	@Test
	void deveriaRetornarRelatorioDeCarteiraDeInvestimentosConsiderandoVendas() {
		
		Usuario usuario = new Usuario("Rafaela", "rafa@email.com", "123456");
		em.persist(usuario);
		
		Transacao t1 = new Transacao("ITSA4", 
				new BigDecimal("10.00"),
				50, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t1);
		
		Transacao t2 = new Transacao("BBSE3", 
				new BigDecimal("22.80"),
				80, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t2);
		
		Transacao t3 = new Transacao("EGIE3", 
				new BigDecimal("40.00"),
				25, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t3);
		
		Transacao t4 = new Transacao("ITSA4", 
				new BigDecimal("11.20"),
				40, 
				LocalDate.now(), 
				TipoTransacao.VENDA, 
				usuario);
		em.persist(t4);
		
		Transacao t5 = new Transacao("SAPR4", 
				new BigDecimal("04.02"),
				120, 
				LocalDate.now(), 
				TipoTransacao.COMPRA, 
				usuario);
		em.persist(t5);
		
		/*
		 * Transacao t6 = new Transacao("PRIO3", new BigDecimal("28.50"), 100,
		 * LocalDate.now(), TipoTransacao.COMPRA, usuario); em.persist(t6);
		 * 
		 * Transacao t7 = new Transacao("SHUL4", new BigDecimal("17.50"), 100,
		 * LocalDate.now(), TipoTransacao.COMPRA, usuario); em.persist(t7);
		 * 
		 * Transacao t8 = new Transacao("PETR4", new BigDecimal("40.20"), 200,
		 * LocalDate.now(), TipoTransacao.COMPRA, usuario); em.persist(t8);
		 */
		
		List<ItemCarteiraDto> relatorio = repository.relatorioCarteiraDeInvestimentos();
		Assertions
		.assertThat(relatorio)
		.hasSize(4)
		.extracting(ItemCarteiraDto::getTicker,ItemCarteiraDto::getQuantidade,ItemCarteiraDto::getPercentual)
		.containsExactlyInAnyOrder(
				Assertions.tuple("ITSA4",10l,new BigDecimal("4.26")),
				Assertions.tuple("BBSE3",80l,new BigDecimal("34.04")),
				Assertions.tuple("EGIE3",25l,new BigDecimal("10.64")),
				Assertions.tuple("SAPR4",120l,new BigDecimal("51.06"))
				/*Assertions.tuple("PRIO3",100l,0.15748),
				Assertions.tuple("SHUL4",100l,0.15748),
				Assertions.tuple("PETR4",200l,0.31496)*/
				);
	}
	
}
