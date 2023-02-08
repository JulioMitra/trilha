package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RelationMember {
    @NotNull
    private Long idTabelaFilho;

    private List<Parents> parents;

    public RelationMember(CreateRelationMember dados) {
        this.idTabelaFilho = dados.idTabelaFilho();
        this.parents = new ArrayList<>();
        for (RelationMember.Parents parentData : dados.parents()) {
            this.parents.add(new Parents(parentData.idMembroFilho, parentData.idMembroPai));
        }
    }

    public RelationMember(UpdateRelationMember dados) {
        this.idTabelaFilho = dados.idTabelaFilho();
        this.parents = new ArrayList<>();
        for (RelationMember.Parents parentData : dados.parents()) {
            this.parents.add(new Parents(parentData.idMembroFilho, parentData.idMembroPai));
        }
    }

    public RelationMember(DeleteRelationMember dados) {
        this.idTabelaFilho = dados.idTabelaFilho();
        this.parents = new ArrayList<>();
        for (RelationMember.Parents parentData : dados.parents()) {
            this.parents.add(new Parents(parentData.idMembroFilho, null));
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parents {
        private Long idMembroFilho;
        private Long idMembroPai;
    }
}
