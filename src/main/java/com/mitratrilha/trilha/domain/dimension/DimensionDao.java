package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public interface DimensionDao {

    void createDimension(Dimension dimension);

    List<Dimension> findDimensionMember(Long id);

    int createDimensionMember(Dimension dimension, Long id);

    List<Dimension> findDimensionMemberByNameLast(Long id, String name);

    List<Dimension> findDimensionMemberById(Long id, Long idMembro);

    int updateDimensionMember(Dimension dimension, Long id);

    int deleteDimensionMember(Dimension idMembro, Long id);

    int createRelationMember(RelationMember id, Long id);
}
