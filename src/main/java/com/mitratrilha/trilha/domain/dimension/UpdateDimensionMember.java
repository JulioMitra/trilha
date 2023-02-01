package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;

public record UpdateDimensionMember(

        @NotNull
        Long id,
        String name,
        Long sonid

) {
}
