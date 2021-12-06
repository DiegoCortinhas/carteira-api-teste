package br.com.alura.carteira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.modelo.TipoTransacao;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@ExtendWith(MockitoExtension.class) //Junit antes de rodar os testes usa extensao do Mockito para criar os Mocks
class TransacaoServiceTest {
	
	@Mock
	private TransacaoRepository transacaoRepository;
	@Mock
	private UsuarioRepository usuarioRepository;
	
	@Mock
	private ModelMapper modelMapper;
	
	@InjectMocks  //da new na Service e injeta os Mocks criados anteriormente
	private TransacaoService service;
	
	private Usuario logado;
	
	@BeforeEach
	public void before() {
		this.logado = new Usuario("Rodrigo","rodrigo@email.com","123456");
		
	}
	
	private TransacaoFormDto criarTransacaoFormDto() {
		TransacaoFormDto formDto = new TransacaoFormDto(
				"ITSA4",
				new BigDecimal("10.45"),
				LocalDate.now(),
				200,
				TipoTransacao.COMPRA,
				1l
				);
		return formDto;
	}


	@Test
	void deveriaCadastrarUmaTransacao() {
		TransacaoFormDto formDto = criarTransacaoFormDto();
		
		Mockito
		.when(usuarioRepository.getById(formDto.getUsuarioId()))
		.thenReturn(logado);
		
		Transacao transacao = new Transacao(formDto.getTicker(),
				formDto.getPreco(),
				formDto.getQuantidade(),
				formDto.getData(), 
				formDto.getTipo(), 
				logado);
		
		Mockito
		.when(modelMapper.map(formDto, Transacao.class))
		.thenReturn(transacao);
		
		Mockito
		.when(modelMapper.map(transacao, TransacaoDto.class))
		.thenReturn(new TransacaoDto(
				null, 
				transacao.getTicker(), 
				transacao.getPreco(), 
				transacao.getQuantidade(), 
				transacao.getTipo()));
		
		TransacaoDto dto = service.cadastrar(formDto, logado);
		
		Mockito.verify(transacaoRepository).save(Mockito.any());
		
			
		//asserts do Junit para fazer as verificações
		//Mock simular comportamentos nos testes automatizados
		// Mock utilizado para simular a dependencia que tem dentro de uma classe
		assertEquals(formDto.getTicker(), dto.getTicker());
		assertEquals(formDto.getPreco(), dto.getPreco());
		assertEquals(formDto.getQuantidade(), dto.getQuantidade());
		assertEquals(formDto.getTipo(), dto.getTipo());
		
	}

	
	@Test
	void naoDeveriaCadastrarUmaTransacaoComUsuarioInexistente() {
		TransacaoFormDto formDto = new TransacaoFormDto(
				"ITSA4",
				new BigDecimal("10.45"),
				LocalDate.now(),
				200,
				TipoTransacao.COMPRA,
				999999l
				);
		
		Mockito.when(usuarioRepository.getById(formDto.getUsuarioId()))
		.thenThrow(EntityNotFoundException.class);
		
		assertThrows(IllegalArgumentException.class, () -> service.cadastrar(formDto,logado));
					
	}
}
