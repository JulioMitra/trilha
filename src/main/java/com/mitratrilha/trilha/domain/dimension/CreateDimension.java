package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotBlank;

public record CreateDimension(

        @NotBlank
        String name

) {
}
