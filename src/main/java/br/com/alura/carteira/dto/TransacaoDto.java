package br.com.alura.carteira.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.alura.carteira.modelo.TipoTransacao;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransacaoDto {
	
	private Long id;
	private String ticker;
	private BigDecimal preco;
	private Integer quantidade;
	private TipoTransacao tipo;
	
	
	/*
	 * public TransacaoDto(Transacao transacao) { 
	 * this.ticker=transacao.getTicker();
	 * this.preco = transacao.getPreco(); this.quantidade =
	 * transacao.getQuantidade(); this.tipo = transacao.getTipo(); 
	 * }
	 */
	
	
}
