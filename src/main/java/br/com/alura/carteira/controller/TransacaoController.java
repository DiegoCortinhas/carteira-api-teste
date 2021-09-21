package br.com.alura.carteira.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.modelo.Transacao;

@RestController // avisa ao Spring que essa classe é um controller
@RequestMapping("/transacoes") // indicando que essa classe vai responder as requisiçoes que tiverem o endereço
								// de /transacoes
public class TransacaoController {

	private List<Transacao> transacoes = new ArrayList<>();
	private ModelMapper modelMapper = new ModelMapper();
	
	// nome metodo de acordo com o objetivo
	// espera uma pagina de resposta
	@GetMapping // se requisicao for tipo get entra em listar()
	// @ResponseBody devolver o que esta no metodo no corpo da resposta e nao
	// devolver a pagina como o metodo, ja implementado no RestController
	public List<TransacaoDto> listar() {
		
		/* Para Java 7
		 * List<TransacaoDto> transacoesDto = new ArrayList<>();
		 * 
		 * for (Transacao transacao : transacoes) { TransacaoDto dto = new
		 * TransacaoDto(); dto.setTicker(transacao.getTicker());
		 * dto.setPreco(transacao.getPreco());
		 * dto.setQuantidade(transacao.getQuantidade());
		 * dto.setTipo(transacao.getTipo());
		 * 
		 * transacoesDto.add(dto); }
		 */
		
		
		return transacoes
				.stream()
				.map(t -> modelMapper.map(t, TransacaoDto.class))
				.collect(Collectors.toList());
		
		//Para Java 8
		//return transacoes.stream().map(TransacaoDto::new).collect(Collectors.toList());
		
		// return new Transacao("VALE03", LocalDate.now(), new
		// BigDecimal("81"),100,TipoTransacao.COMPRA);
		// return transacoesDto;
	}

	@PostMapping
	public void cadastrar(@RequestBody TransacaoFormDto dto) {
		Transacao transacao = modelMapper.map(dto, Transacao.class);
		transacoes.add(transacao);
	
	}

}
