package com.mitratrilha.trilha.service;

import com.mitratrilha.trilha.domain.dimension.Dimension;
import com.mitratrilha.trilha.domain.dimension.DimensionDao;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
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

    public List<Dimension> findDimensionMember(Long id) {
        if (id != null) {
            return dimensionDao.findDimensionMember(id);
        }
        System.out.println("Valor nulo camada serviço!");
        return null;
    }


    public List<Dimension> createDimensionMembers(Dimension dimension, Long id) {
            dimensionDao.createDimensionMember(dimension, id);
        return null;
    }

    public List<Dimension> updateDimensionMembers(Dimension dimension, Long id) {
        dimensionDao.updateDimensionMember(dimension, id);
        return null;
    }

    public List<Dimension> findDimensionMemberByNameLast(Long id, String name) {
        if (id != null) {
            return dimensionDao.findDimensionMemberByNameLast(id, name);
        }
        System.out.println("Valor nulo camada serviço!");
        return null;
    }

    public List<Dimension> findDimensionMemberById(Long id, Long idMember) {
        if (id != null) {
            return dimensionDao.findDimensionMemberById(id, idMember);
        }
        System.out.println("Valor nulo camada serviço!");
        return null;
    }

}

