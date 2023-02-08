package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public record DeleteRelationMember(

        Long idTabelaFilho,
        List<RelationMember.Parents> parents
) {
}
