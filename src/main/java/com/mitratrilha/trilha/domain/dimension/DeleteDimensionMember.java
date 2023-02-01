package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;

public record DeleteDimensionMember(

        @NotNull
        Long id

) {
}
