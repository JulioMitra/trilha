package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
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
