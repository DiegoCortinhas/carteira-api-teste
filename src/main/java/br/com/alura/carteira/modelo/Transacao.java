package br.com.alura.carteira.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;



public class Transacao {

	private String ticker;
	private BigDecimal preco;
	private int quantidade;
	private LocalDate data;
	
	// @JsonIgnore anotação para jamais mostrar esse atributo no JSON
	private TipoTransacao tipo;
	
	
	public Transacao() {

	}

	@Override
	public String toString() {
		return "Transacao [ticker=" + ticker + ", preco=" + preco + ", quantidade=" + quantidade + ", data=" + data
				+ ", tipo=" + tipo + "]";
	}

	public Transacao(String ticker,LocalDate data, BigDecimal preco, int quantidade, TipoTransacao tipo) {
		this.ticker = ticker;
		this.preco = preco;
		this.quantidade = quantidade;
		this.data = data;
		this.tipo = tipo;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public void setTipo(TipoTransacao tipo) {
		this.tipo = tipo;
	}

}
