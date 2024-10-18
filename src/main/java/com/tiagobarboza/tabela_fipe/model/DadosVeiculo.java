package com.tiagobarboza.tabela_fipe.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosVeiculo(String codigo, String nome) {

}
