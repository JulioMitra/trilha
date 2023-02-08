package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public interface DimensionDao {

    void createDimension(Dimension dimension);

    List<DimensionView> findDimensionMember(Long id);

    int createDimensionMember(Dimension dimension, Long id);

    List<DimensionView> findDimensionMemberById(Long id, Long idMembro);

    int updateDimensionMember(Dimension dimension, Long id);

    int deleteDimensionMember(Dimension idMembro, Long id);

    int createRelationMember(RelationMember relationMember, Long id);

    int updateRelationMember(RelationMember relationMember, Long id);

    int deleteRelationMember(RelationMember relationMember, Long id);
}
