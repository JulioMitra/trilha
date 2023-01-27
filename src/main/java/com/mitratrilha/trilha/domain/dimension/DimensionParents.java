package com.mitratrilha.trilha.domain.dimension;


import com.fasterxml.jackson.annotation.JsonManagedReference;


import java.util.List;



public record DimensionParents(
    Long id,
    String name,
    Long sonid,
    List<DimensionParents> parents) {

    public DimensionParents(Dimension dim, List<DimensionParents> parents) {
        this(dim.getId(), dim.getName(), dim.getSonid(), parents);
    }



}

