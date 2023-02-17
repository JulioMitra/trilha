package com.mitratrilha.trilha.domain.dimension;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class DimensionNode {

    private Long id;

    private List<DimensionNode> parents;

}
