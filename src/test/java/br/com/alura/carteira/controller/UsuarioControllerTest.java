package br.com.alura.carteira.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest  //Spring carrega todas as classes do projeto
@AutoConfigureMockMvc  //configurar o Mock MVC
@ActiveProfiles("test")
@Transactional  //para fazer o rollback ao final de cada teste
class UsuarioControllerTest {
	
	@Autowired
	private MockMvc mvc;  //Classe do Spring para simular requisicoes http
	
	@Test
	void naoDeveriaCadastrarUsuarioComDadosIncompletos() throws Exception {
		String json = "{}";     //JSON incompleto - sem levar nome ou login
		
		mvc
		.perform(post("/usuarios")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void deveriaCadastrarUsuarioComDadosCompletos() throws Exception {
		String json = "{\"nome\":\"fulano\",\"login\":\"fulano@email.com\"}";     //JSON incompleto - sem levar nome ou login
		
		mvc
		.perform(post("/usuarios")
		.contentType(MediaType.APPLICATION_JSON)
		.content(json))
		.andExpect(status().isCreated())
		.andExpect(header().exists("Location"))
		.andExpect(content().json(json));
		
	}
	
}
