package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;

public record UpdateDimension(

        @NotNull
        Long id,
        String name,

        Long sonid

) {
}
