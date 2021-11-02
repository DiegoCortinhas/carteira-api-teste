package br.com.alura.carteira.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	public List<UsuarioDto> listar() {
		List <Usuario> usuarios = usuarioRepository.findAll();
		
		return usuarios
				.stream()
				.map(t -> modelMapper.map(t, UsuarioDto.class))
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void cadastrar(UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		//geracao de senha aleatoria de 0 a 999999
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(senha);
		
		//System.out.println(usuario);
		//System.out.println(usuario.getSenha());
		usuarioRepository.save(usuario);
 
	}
	
}
