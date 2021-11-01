package br.com.alura.carteira;

import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;

public class TestandoLombok {
	public static void main(String[] args) {
		Transacao t = new Transacao();
		//Transacao t2 = new Transacao("XPTO1",new BigDecimal(119.20),100,LocalDate.now(),TipoTransacao.COMPRA);
		t.setPreco(new BigDecimal("100.99"));
		
		System.out.println(t.getPreco());
		System.out.println(t);
	}
	
}
