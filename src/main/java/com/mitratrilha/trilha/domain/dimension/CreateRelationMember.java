package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public record CreateRelationMember(

        Long idTabelaFilho,
        List<RelationMember.Parents> parents
) {
}
