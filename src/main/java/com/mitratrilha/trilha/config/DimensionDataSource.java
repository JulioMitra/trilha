package com.mitratrilha.trilha.config;

import com.mitratrilha.trilha.domain.dimension.Dimension;
import com.mitratrilha.trilha.domain.dimension.DimensionDao;
import com.mitratrilha.trilha.domain.dimension.DimensionView;
import com.mitratrilha.trilha.domain.dimension.RelationMember;
import org.flywaydb.core.internal.util.JsonUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
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
                     + ");";
        jdbcTemplate.execute(sql);
    }

    @Override
    public List<DimensionView> findDimensionMember(Long id) {
        String sql = "SELECT * FROM dimension_" + id + ";";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            DimensionView dimensionView = new DimensionView();
            dimensionView.setId(resultSet.getLong("id"));
            dimensionView.setName(resultSet.getString("name"));
            List<DimensionView.Parents> parentIds = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int j = 1; j <= columnCount; j++) {
                String columnName = metaData.getColumnName(j);
                if (columnName.startsWith("dimension_")) {
                    String dimensionId = columnName.replace("dimension_", "");
                    Long parentId = resultSet.getLong(columnName);
                    if (resultSet.wasNull()) {
                        parentId = null;
                    }
                    parentIds.add(new DimensionView.Parents(Long.parseLong(dimensionId), parentId));
                }
            }
            dimensionView.setParents(parentIds);
            return dimensionView;
        });
    }

    @Override
    public int createDimensionMember(Dimension dimension, Long id) {
        String sql = "INSERT INTO dimension_" + id + "(id, name) VALUES (?, ?);";
        return jdbcTemplate.update(sql, dimension.getId(), dimension.getName());
    }

    @Override
    public List<DimensionView> findDimensionMemberById(Long id, Long idMember) {
        String sql = "SELECT * FROM dimension_" + id + " WHERE id = " + idMember + ";";
        return jdbcTemplate.query(sql, (resultSet, i) -> {
            DimensionView dimensionView = new DimensionView();
            dimensionView.setId(resultSet.getLong("id"));
            dimensionView.setName(resultSet.getString("name"));
            List<DimensionView.Parents> parentIds = new ArrayList<>();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int j = 1; j <= columnCount; j++) {
                String columnName = metaData.getColumnName(j);
                if (columnName.startsWith("dimension_")) {
                    String dimensionId = columnName.replace("dimension_", "");
                    Long parentId = resultSet.getLong(columnName);
                    if (resultSet.wasNull()) {
                        parentId = null;
                    }
                    parentIds.add(new DimensionView.Parents(Long.parseLong(dimensionId), parentId));
                }
            }
            dimensionView.setParents(parentIds);
            return dimensionView;
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

    @Override
    public int deleteDimensionMember(Dimension dimension, Long id) {
        String sql = "DELETE FROM dimension_" + id + " WHERE id = " + dimension.getId() + ";";
        return jdbcTemplate.update(sql);
    }


    @Override
    public int createRelationMember(RelationMember relationMember, Long idPai) {
        String sql = "ALTER TABLE dimension_" + relationMember.getIdTabelaFilho() + " ADD COLUMN IF NOT EXISTS dimension_" + idPai
                     + " INT;";
        jdbcTemplate.update(sql);
        String sql2 = "UPDATE dimension SET sonid = ? WHERE id = ?;";
        jdbcTemplate.update(sql2, relationMember.getIdTabelaFilho(), idPai);
        for (int i= 0; i < relationMember.getParents().size(); i++) {
            String sql3 =
                    "UPDATE dimension_" + relationMember.getIdTabelaFilho() + " SET dimension_" + idPai
                    + " = " + relationMember.getParents().get(i).getIdMembroPai()
                    + " WHERE id =" + relationMember.getParents().get(i).getIdMembroFilho() + ";";
            jdbcTemplate.update(sql3);
        }
        return relationMember.getParents().size();
    }

    @Override
    public int updateRelationMember(RelationMember relationMember, Long idPai) {
        String sql = "UPDATE dimension SET sonid = ? WHERE id = ?;";
        jdbcTemplate.update(sql, relationMember.getIdTabelaFilho(), idPai);
        for (int i= 0; i < relationMember.getParents().size(); i++) {
            String sql2 =
                    "UPDATE dimension_" + relationMember.getIdTabelaFilho() + " SET dimension_" + idPai
                    + " = " + relationMember.getParents().get(i).getIdMembroPai()
                    + " WHERE id =" + relationMember.getParents().get(i).getIdMembroFilho() + ";";
            jdbcTemplate.update(sql2);
        }
        return relationMember.getParents().size();
    }

    @Override
    public int deleteRelationMember(RelationMember relationMember, Long idPai) {
        for (int i= 0; i < relationMember.getParents().size(); i++) {
            String sql =
                    "UPDATE dimension_" + relationMember.getIdTabelaFilho() + " SET dimension_" + idPai
                    + " = " + relationMember.getParents().get(i).getIdMembroPai()
                    + " WHERE id =" + relationMember.getParents().get(i).getIdMembroFilho() + ";";
            jdbcTemplate.update(sql);
        }
        String sql2 = "SELECT COUNT(dimension_"+ idPai + ") FROM dimension_" + relationMember.getIdTabelaFilho() +";";
        int countColuna = jdbcTemplate.queryForObject(sql2, Integer.class);
        if (countColuna == 0) {
            String sql3 ="ALTER TABLE dimension_" + relationMember.getIdTabelaFilho()
                         + " DROP COLUMN dimension_"+ idPai +";";
            jdbcTemplate.update(sql3);
            String sql4 = "UPDATE dimension SET sonid = ? WHERE id = ?;";
            jdbcTemplate.update(sql4, null, idPai);
        }
        return countColuna;
    }
}
