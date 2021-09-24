package br.com.alura.carteira.modelo;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter 
@ToString(exclude = {"data", "quantidade", "tipo"})
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

	private String ticker;
	private BigDecimal preco;
	private int quantidade;
	private LocalDate data;
	
	// @JsonIgnore anotação para jamais mostrar esse atributo no JSON
	private TipoTransacao tipo;
		
}
