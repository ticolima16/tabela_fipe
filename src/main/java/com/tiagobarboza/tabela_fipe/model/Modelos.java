package com.tiagobarboza.tabela_fipe.model;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Modelos(List<DadosVeiculo> modelos) {

}
