package com.mitratrilha.trilha.service;

import com.mitratrilha.trilha.domain.dimension.Dimension;
import com.mitratrilha.trilha.domain.dimension.DimensionDao;
import com.mitratrilha.trilha.domain.dimension.DimensionDetailMemeber;
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
        int result = dimensionDao.createDimension(dimension);
        if (result != 1) {
            throw new IllegalIdentifierException("oops something went wrong!");
        }
    }

    public void createDimensionTeste(Dimension dimension) {
        dimensionDao.createDimensionTeste(dimension);
    }

    public List<Dimension> findDimensionMember(Long id) {
        if (id != null) {
            return dimensionDao.findDimensionMember(id);
        }
        System.out.println("Valor nulo camada serviço!");
        return null;
    }

    public List<Dimension> createDimensionMembers(Dimension dimension, Long id) {
        System.out.println("Valores no Service: "+dimension.getId()+" name"+dimension.getName());
            dimensionDao.createDimensionMember(dimension, id);
        return null;
    }

    public List<Dimension> findDimensionMemberByNameLast(Long id, String name) {
        if (id != null) {
            return dimensionDao.findDimensionMemberByNameLast(id, name);
        }
        System.out.println("Valor nulo camada serviço!");
        return null;
    }

}

