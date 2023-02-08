package com.mitratrilha.trilha.domain.dimension;

import java.util.List;

public record UpdateRelationMember(

        Long idTabelaFilho,
        List<RelationMember.Parents> parents
) {
}
