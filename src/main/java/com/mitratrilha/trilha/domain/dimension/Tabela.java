package com.mitratrilha.trilha.domain.dimension;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tabela {
    private Map<String, Object> colunas = new LinkedHashMap<>();

    public void addColuna(String nome, Object valor) {
        colunas.put(nome, valor);
    }

}
