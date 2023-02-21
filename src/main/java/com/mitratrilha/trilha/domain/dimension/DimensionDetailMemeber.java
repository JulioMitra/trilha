package com.mitratrilha.trilha.domain.dimension;

public record DimensionDetailMemeber(Long id, String name) {

    public DimensionDetailMemeber(Dimension dimension) {
        this(dimension.getId(), dimension.getName());
    }

}
