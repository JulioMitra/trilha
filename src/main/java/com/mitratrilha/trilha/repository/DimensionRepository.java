package com.mitratrilha.trilha.repository;

import com.mitratrilha.trilha.domain.dimension.Dimension;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

public interface DimensionRepository extends JpaRepository<Dimension, Long>{

    List<Dimension> findAllById(Long i);

    List<Dimension> findAllBySonid(Long sonid);

}
