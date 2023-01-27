package com.mitratrilha.trilha.domain.dimension;

import org.springframework.context.annotation.Bean;

import java.util.List;

public record DimensionDetail(Long id, String name, Long sonid) {

    public DimensionDetail(Dimension dimension) {
        this(dimension.getId(), dimension.getName(), dimension.getSonid());
    }
}
