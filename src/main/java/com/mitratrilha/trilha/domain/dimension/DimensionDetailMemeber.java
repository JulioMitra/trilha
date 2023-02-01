package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public record DimensionDetailMemeber(Long id, String name, Long sonid) {

    public DimensionDetailMemeber(Dimension dimension) {
        this(dimension.getId(), dimension.getName(), dimension.getSonid());
    }

}
