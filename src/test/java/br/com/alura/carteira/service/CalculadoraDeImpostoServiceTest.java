package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;

class CalculadoraDeImpostoServiceTest {

	@Test
	void transacaoDoTipoTesteNaoDeveriaTerImposto() {
		
		Transacao transacao = new Transacao(
				120l,
				"BBSE3",
				new BigDecimal("30.00"),
				10,
				LocalDate.now(),
				TipoTransacao.COMPRA,
				new Usuario(1l,"Rafaela","rafa@email.com","123456")
				);

		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO,imposto);
		
	}
	
	@Test
	void transacaoDoTipoVendaComValorMenorDoQueVinteMilNaoDeveriaTerImposto() {
		
		Transacao transacao = new Transacao(
				120l,
				"BBSE3",
				new BigDecimal("30.00"),
				10,
				LocalDate.now(),
				TipoTransacao.VENDA,
				new Usuario(1l,"Rafaela","rafa@email.com","123456")
				);

		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(BigDecimal.ZERO,imposto);
		
	}
	
	@Test
	void deveriaCalcularImpostoDeTransacaoDoTipoVendaComValorMaiorQueVinteMil() {
		
		Transacao transacao = new Transacao(
				120l,
				"VALE3",
				new BigDecimal("1000.00"),
				30,
				LocalDate.now(),
				TipoTransacao.VENDA,
				new Usuario(1l,"Rafaela","rafa@email.com","123456")
				);

		CalculadoraDeImpostoService calculadora = new CalculadoraDeImpostoService();
		BigDecimal imposto = calculadora.calcular(transacao);
		
		assertEquals(new BigDecimal("4500.00"),imposto);
		
	}
	
}
