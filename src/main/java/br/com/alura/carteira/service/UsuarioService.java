package br.com.alura.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.carteira.dto.TransacaoDto;
import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.modelo.Transacao;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service // indicar para o Spring que é uma classe de serviço
public class UsuarioService {

	//Migracao do codigo responsavel pelas regras de negocio para essa classe
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		
		return usuarios.map(t -> modelMapper.map(t, UsuarioDto.class));
				
	}
	
	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		//geracao de senha aleatoria de 0 a 999999
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);
		
		//System.out.println(usuario);
		//System.out.println(usuario.getSenha());
		usuarioRepository.save(usuario);
		return modelMapper.map(usuario,UsuarioDto.class);
 
	}
	
}
