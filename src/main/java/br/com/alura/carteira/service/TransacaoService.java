package br.com.alura.carteira.service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import br.com.alura.carteira.dto.AtualizacaoTransacaoFormDto;
import br.com.alura.carteira.dto.TransacaoDetalhadaDto;
import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.TransacaoFormDto;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.TransacaoRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service
public class TransacaoService {
	
	@Autowired
	private TransacaoRepository transacaoRepository;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CalculadoraDeImpostoService calculadoraDeImpostoService;
	
	public Page<TransacaoDto> listar(Pageable paginacao, Usuario usuario){
		return transacaoRepository.
				findAllByUsuario(paginacao,usuario)
				.map(t -> modelMapper.map(t, TransacaoDto.class));
		
		/*
		 * List<TransacaoDto> transacoesDto = new ArrayList<>();
		 * 
		 * transacoes.forEach(transacao -> { BigDecimal imposto =
		 * calculadoraDeImpostoService.calcular(transacao); TransacaoDto dto =
		 * modelMapper.map(transacao, TransacaoDto.class); dto.setImposto(imposto);
		 * transacoesDto.add(dto); });
		 * 
		 * return new PageImpl<TransacaoDto>(transacoesDto, transacoes.getPageable(),
		 * transacoes.getTotalElements());
		 */
		
		
		
	
	}
	
	@Transactional
	public TransacaoDto cadastrar(TransacaoFormDto dto, Usuario logado) {
		
		//throw new NullPointerException("Testando Erro 500");
		
		Long idUsuario = dto.getUsuarioId();
		
		try {
			Usuario usuario = usuarioRepository.getById(idUsuario);
			
			if(!usuario.equals(logado)) {
				lancarErroAcessoNegado();
			}
			
			Transacao transacao = modelMapper.map(dto, Transacao.class);
			transacao.setId(null);
			transacao.setUsuario(usuario);
			BigDecimal imposto = calculadoraDeImpostoService.calcular(transacao);
			transacao.setImposto(imposto);
			
			transacaoRepository.save(transacao);
			
			return modelMapper.map(transacao,TransacaoDto.class);
		
		} catch (EntityNotFoundException e) {
			throw new IllegalArgumentException("Usu??rio Inexistente!");
		}
		
		
		//return new TransacaoDto();
	}

	@Transactional
	public TransacaoDto atualizar(AtualizacaoTransacaoFormDto dto, Usuario logado) {
		Transacao transacao = transacaoRepository.getById(dto.getId());
		
		if (!transacao.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		
		transacao.atualizarInformacoes(dto.getTicker(),dto.getData(),dto.getPreco(),dto.getQuantidade(),dto.getTipo());
		return modelMapper.map(transacao,TransacaoDto.class);
	}

	@Transactional
	public void remover(Long id, Usuario logado) {
		Transacao transacao = transacaoRepository.getById(id);
				
		if (!transacao.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		transacaoRepository.deleteById(id);
	}

	public TransacaoDetalhadaDto detalhar(Long id, Usuario logado) {
		
		Transacao transacao = transacaoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException());
		
		if (!transacao.pertenceAoUsuario(logado)) {
			lancarErroAcessoNegado();
		}
		return modelMapper.map(transacao,TransacaoDetalhadaDto.class);
	}
	
	private void lancarErroAcessoNegado() {
		throw new AccessDeniedException("Acesso Negado!");
	}
	
}
