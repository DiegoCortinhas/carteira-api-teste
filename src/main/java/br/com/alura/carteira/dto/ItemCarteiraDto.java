package br.com.alura.carteira.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemCarteiraDto {
	private String nome;
	private Long quantidade;
	private double percentual;
	
}
