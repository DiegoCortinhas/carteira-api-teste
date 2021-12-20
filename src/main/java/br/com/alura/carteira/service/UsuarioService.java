package br.com.alura.carteira.service;

import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.alura.carteira.dto.UsuarioDto;
import br.com.alura.carteira.dto.UsuarioFormDto;
import br.com.alura.carteira.infra.EnviadorDeEmail;
import br.com.alura.carteira.modelo.Perfil;
import br.com.alura.carteira.modelo.Usuario;
import br.com.alura.carteira.repository.PerfilRepository;
import br.com.alura.carteira.repository.UsuarioRepository;

@Service // indicar para o Spring que é uma classe de serviço
public class UsuarioService {

	//Migracao do codigo responsavel pelas regras de negocio para essa classe
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PerfilRepository perfilRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private EnviadorDeEmail enviadorDeEmail;
	

	public Page<UsuarioDto> listar(Pageable paginacao) {
		Page<Usuario> usuarios = usuarioRepository.findAll(paginacao);
		
		return usuarios.map(t -> modelMapper.map(t, UsuarioDto.class));
				
	}
	
	@Transactional
	public UsuarioDto cadastrar(UsuarioFormDto dto) {
		Usuario usuario = modelMapper.map(dto, Usuario.class);
		
		Perfil perfil = perfilRepository.getById(dto.getPerfilId());
		usuario.adicionarPerfil(perfil);
		
		//System.out.println("id do usuario é: " + usuario.getId());
		
		//geracao de senha aleatoria de 0 a 999999
		String senha = new Random().nextInt(999999) + "";
		usuario.setSenha(bCryptPasswordEncoder.encode(senha));
		
		usuario.setId(null);
		
		usuarioRepository.save(usuario);
		
		String destinatario = usuario.getEmail();
		String assunto = "Carteira - Bem Vindo(a) " + usuario.getNome();
		String mensagem = String.format("Olá %s!\n\n"
				+ "Seguem os seus dados de acesso ao sistema Carteira:"
				+ "\nLogin:%s"
				+ "\nSenha:%s", 
				usuario.getNome(),usuario.getLogin(),senha);
		
		enviadorDeEmail.enviarEmail(destinatario , assunto, mensagem);
		
		return modelMapper.map(usuario,UsuarioDto.class);
 
	}
	
}
