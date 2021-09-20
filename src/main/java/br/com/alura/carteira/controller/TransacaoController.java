package br.com.alura.carteira.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;


@RestController   //avisa ao Spring que essa classe é um controller
@RequestMapping("/transacoes")  //indicando que essa classe vai responder as requisiçoes que tiverem o endereço de /transacoes 
public class TransacaoController {
	
	private List<Transacao> transacoes = new ArrayList<>();
	
	//nome metodo de acordo com o objetivo
	// espera uma pagina de resposta
	@GetMapping  //se requisicao for tipo get entra em listar()
	//@ResponseBody devolver o que esta no metodo no corpo da resposta e nao devolver a pagina como o metodo, ja implementado no RestController
	public List<Transacao> listar() {
		
		//return new Transacao("VALE03", LocalDate.now(), new BigDecimal("81"),100,TipoTransacao.COMPRA);
		return transacoes;
	}
	
	@PostMapping
	public void cadastrar (@RequestBody Transacao transacao) {
		transacoes.add(transacao);
	}

}
