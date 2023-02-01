package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;

public record CreateRelationMember(

        @NotNull
        Long idTabelaFilho,

        @NotNull
        Long idMembroFilho,

        @NotNull
        Long id

) {
}
