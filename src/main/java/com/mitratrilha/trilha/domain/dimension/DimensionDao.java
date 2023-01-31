package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public interface DimensionDao {
    int createDimension(Dimension dimension);

    void createDimensionTeste(Dimension dimension);

    List<Dimension> findDimensionMember(Long id);

    List<Dimension> createDimensionMember(Dimension dimension, Long id);

    List<Dimension> findDimensionMemberByNameLast(Long id, String name);
}
