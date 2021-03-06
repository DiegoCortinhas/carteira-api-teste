package br.com.alura.carteira.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.alura.carteira.modelo.Usuario;



//@Repository - acesso aos dados da aplicação - para spring conhecer a classe e conseguir injetar EM 
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{

	Optional<Usuario> findByLogin(String login);
	
	@Query("SELECT u FROM Usuario u JOIN FETCH u.perfis WHERE u.id = :idUsuario")
	Optional<Usuario> carregarPorIdsComPerfis(Long idUsuario);
	
	
}
