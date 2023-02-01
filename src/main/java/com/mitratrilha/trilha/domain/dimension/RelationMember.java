package com.mitratrilha.trilha.domain.dimension;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Objects;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class RelationMember {

        @NotNull
       private Long idTabelaFilho;

        @NotNull
       private Long idMembroFilho;

        @NotNull
       private Long id;
        public RelationMember(CreateRelationMember dados) {
            this.idTabelaFilho = dados.idTabelaFilho();
            this.idMembroFilho = dados.idMembroFilho();
            this.id = dados.id();
        }
}
