package com.mitratrilha.trilha.domain.dimension;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DimensionView {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private List<DimensionView.Parents> parents;



    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Parents {
        private Long idTabelaPai;
        private Long idMembroPai;
    }

}
