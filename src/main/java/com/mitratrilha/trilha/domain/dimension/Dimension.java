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
        this.id = dados.id();
        this.name = dados.name();
    }

    public Dimension(UpdateDimensionMember dados) {
        if(dados.name() != null) {
            this.name = dados.name();
        } else if (dados.sonid() != null) {
            this.sonid = dados.sonid();
        }
        this.id = dados.id();
    }

    public Dimension(DeleteDimensionMember dados) {
        this.id = dados.id();
    }


    public void updateDimension(UpdateDimension dados) {
        if (dados.name() != null) {
            this.name = dados.name();
        }
            this.sonid = dados.sonid();
    }

}
