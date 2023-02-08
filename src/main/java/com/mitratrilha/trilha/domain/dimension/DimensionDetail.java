package com.mitratrilha.trilha.domain.dimension;

public record DimensionDetail(Long id, String name, Long sonid) {

    public DimensionDetail(Dimension dimension) {
        this(dimension.getId(), dimension.getName(), dimension.getSonid());
    }
}
