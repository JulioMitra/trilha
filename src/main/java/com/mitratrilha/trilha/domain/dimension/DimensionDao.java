package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public interface DimensionDao {
    int createDimension(Dimension dimension);

    void createDimensionTeste(Dimension dimension);

    List<Dimension> findDimensionMember(Long id);

    int createDimensionMember(Dimension dimension, Long id);
}
