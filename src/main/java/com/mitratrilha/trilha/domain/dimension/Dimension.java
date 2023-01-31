package com.mitratrilha.trilha.domain.dimension;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "dimension")
@Entity(name = "Dimension")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Dimension {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long sonid;



    public Dimension(CreateDimension dados) {

        this.name = dados.name();
    }

    public Dimension(CreateDimensionTeste dados) {

        this.name = dados.name();
    }

    public Dimension(CreateDimensionMember dados) {
        this.name = dados.name();
    }


    public void updateDimension(UpdateDimension dados) {
        if (dados.name() != null) {
            this.name = dados.name();
        }
            this.sonid = dados.sonid();
    }
}
