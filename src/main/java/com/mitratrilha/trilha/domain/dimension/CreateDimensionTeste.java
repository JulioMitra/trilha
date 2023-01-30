package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotBlank;

public record CreateDimensionTeste(

        @NotBlank
        String name

) {
}
