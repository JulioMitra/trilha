package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateDimensionMember(

        @NotNull
        Long id,

        @NotBlank
        String name

) {
}
