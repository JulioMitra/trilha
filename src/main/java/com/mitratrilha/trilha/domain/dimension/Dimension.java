package com.mitratrilha.trilha.domain.dimension;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Table(name = "dimension")
@Entity(name = "Dimension")
@Getter
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


    public void updateDimension(UpdateDimension dados) {
        if (dados.name() != null) {
            this.name = dados.name();
        }
            this.sonid = dados.sonid();
    }
}
