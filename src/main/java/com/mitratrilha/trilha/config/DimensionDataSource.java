package com.mitratrilha.trilha.config;

import com.mitratrilha.trilha.domain.dimension.Dimension;
import com.mitratrilha.trilha.domain.dimension.DimensionDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DimensionDataSource implements DimensionDao {
    private final JdbcTemplate jdbcTemplate;

    public DimensionDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int createDimension(Dimension dimension) {
        String sql = """
                INSERT INTO dimension(name)
                VALUES (?)
                """;
        return jdbcTemplate.update(sql, dimension.getName());
    }

    @Override
    public void createDimensionTeste(Dimension dimension) {
        if (dimension != null && dimension.getId() != null) {
            String sql = "CREATE TABLE IF NOT EXISTS dimension_" + dimension.getId() + "("
                         + "id SERIAL not null PRIMARY KEY,"
                         + "name varchar(100) not null,"
                         + "sonid int,"
                         + "CONSTRAINT fk_sonid_id FOREIGN KEY (sonid) REFERENCES dimension_" + dimension.getId() + " (id) ON DELETE SET NULL"
                         + ");";
            jdbcTemplate.execute(sql);
        }
        System.out.println("Valores nulos!");
    }

    @Override
    public List<Dimension> findDimensionMember(Long id) {
            String sql = "SELECT id, name FROM dimension_" + id + ";";
            List<Dimension> dimensions = jdbcTemplate.query(sql, (resultSet, i) -> {
                Dimension dimension = new Dimension();
                dimension.setId(resultSet.getLong("id"));
                dimension.setName(resultSet.getString("name"));
                return dimension;
            });
        return dimensions;
    }

    @Override
    public int createDimensionMember(Dimension dimension, Long id) {
        String sql = "INSERT INTO dimension_"+ id +"(name) VALUES (?);";
        return jdbcTemplate.update(sql, dimension.getName());

    }
}
