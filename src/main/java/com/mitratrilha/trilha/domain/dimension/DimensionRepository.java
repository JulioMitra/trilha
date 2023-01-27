package com.mitratrilha.trilha.domain.dimension;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface DimensionRepository extends JpaRepository<Dimension, Long> {

    List<Dimension> findAllById(Long i);

    List<Dimension> findAllBySonid(Long sonid);
}
