package com.mitratrilha.trilha.service;

import com.mitratrilha.trilha.domain.dimension.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DimensionService {


    private final DimensionDao dimensionDao;

    public DimensionService(DimensionDao dimensionDao) {
        this.dimensionDao = dimensionDao;
    }


    public void createDimension(Dimension dimension) {
        dimensionDao.createDimension(dimension);
    }

    public List<DimensionView> findDimensionMember(Long id) {
        return dimensionDao.findDimensionMember(id);
    }


    public List<Dimension> createDimensionMember(Dimension dimension, Long id) {
            dimensionDao.createDimensionMember(dimension, id);
        return null;
    }

    public List<Dimension> updateDimensionMember(Dimension dimension, Long id) {
        dimensionDao.updateDimensionMember(dimension, id);
        return null;
    }


    public List<DimensionView> findDimensionMemberById(Long id, Long idMember) {
        return dimensionDao.findDimensionMemberById(id, idMember);
    }


    public List<Dimension> deleteDimensionMember(Dimension idMember, Long id) {
        dimensionDao.deleteDimensionMember(idMember, id);
        return null;
    }

    public List<RelationMember> createRelationMember(RelationMember dimension, Long id) {
        dimensionDao.createRelationMember(dimension, id);
        return null;
    }

    public List<RelationMember> updateRelationMember(RelationMember dimension, Long id) {
        dimensionDao.updateRelationMember(dimension, id);
        return null;
    }

    public List<RelationMember> deleteRelationMember(RelationMember dimension, Long id) {
        dimensionDao.deleteRelationMember(dimension, id);
        return null;
    }

    public DimensionNode showRelationTree(Long id) {
        return dimensionDao.showRelationTree(id);
    }

    public DimensionNode showRelationMember(Long id) {
        return dimensionDao.showRelationMember(id);
    }

}

