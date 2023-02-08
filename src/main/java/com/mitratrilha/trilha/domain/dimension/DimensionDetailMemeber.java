package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public record DimensionDetailMemeber(Long id, String name) {

    public DimensionDetailMemeber(Dimension dimension) {
        this(dimension.getId(), dimension.getName());
    }

}
