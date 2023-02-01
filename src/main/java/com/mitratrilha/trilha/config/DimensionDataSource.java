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

//    @Override
//    public int createDimension(Dimension dimension) {
//        String sql = """
//                INSERT INTO dimension(name)
//                VALUES (?)
//                """;
//        return jdbcTemplate.update(sql, dimension.getName());
//    }

    @Override
    public void createDimension(Dimension dimension) {
            String sql = "CREATE TABLE IF NOT EXISTS dimension_" + dimension.getId() + "("
                         + "id int not null PRIMARY KEY,"
                         + "name varchar(100) not null,"
                         + "sonid int,"
                         + "CONSTRAINT fk_sonid_id FOREIGN KEY (sonid) REFERENCES dimension_" + dimension.getId() + " (id) ON DELETE SET NULL"
                         + ");";
            jdbcTemplate.execute(sql);
        }

    @Override
    public List<Dimension> findDimensionMember(Long id) {
            String sql = "SELECT id, name, sonid FROM dimension_" + id + ";";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            Dimension dimension = new Dimension();
            dimension.setId(resultSet.getLong("id"));
            dimension.setName(resultSet.getString("name"));
            dimension.setSonid(resultSet.getLong("sonid"));
            return dimension;
        });
    }

    @Override
    public int createDimensionMember(Dimension dimension, Long id) {
        String sql = "INSERT INTO dimension_"+ id +"(id, name) VALUES (?, ?);";
        return jdbcTemplate.update(sql, dimension.getId(), dimension.getName());
    }

    @Override
    public List<Dimension> findDimensionMemberByNameLast(Long id, String name) {
        String sql = "SELECT id, name FROM dimension_" + id + " WHERE name LIKE '"+name+"' ORDER BY id DESC LIMIT 1;";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            Dimension dimension = new Dimension();
            dimension.setId(resultSet.getLong("id"));
            dimension.setName(resultSet.getString("name"));
            return dimension;
        });
    }

    @Override
    public List<Dimension> findDimensionMemberById(Long id, Long idMember) {
        String sql = "SELECT id, name, sonid FROM dimension_" + id + " WHERE id = "+ idMember+";";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            Dimension dimension = new Dimension();
            dimension.setId(resultSet.getLong("id"));
            dimension.setName(resultSet.getString("name"));
            dimension.setSonid(resultSet.getLong("sonid"));
            return dimension;
        });
    }

    @Override
    public int updateDimensionMember(Dimension dimension, Long id) {
        if (dimension.getName() != null && dimension.getSonid() != null) {
            String sql = "UPDATE dimension_" + id + " SET name = ?, sonid = ? WHERE id = ?;";
            return jdbcTemplate.update(sql, dimension.getName(), dimension.getSonid(), dimension.getId());
        } else if (dimension.getName() != null) {
            String sql = "UPDATE dimension_" + id + " SET name = ? WHERE id = ?;";
            return jdbcTemplate.update(sql, dimension.getName(), dimension.getId());
        } else if (dimension.getSonid() != null) {
            String sql = "UPDATE dimension_" + id + " SET sonid = ? WHERE id = ?;";
            return jdbcTemplate.update(sql, dimension.getSonid(), dimension.getId());
        }
        return 0;
    }
}
