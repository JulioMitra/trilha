package com.mitratrilha.trilha.config;

import com.mitratrilha.trilha.domain.dimension.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DimensionDataSource implements DimensionDao{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    public DimensionDataSource(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDimension(Dimension dimension) {
        String sql = "CREATE TABLE IF NOT EXISTS dimension_" + dimension.getId() + "("
                     + "id int not null PRIMARY KEY,"
                     + "name varchar(100) not null"
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

    @Override
    public DimensionNode showRelationTree(Long id) {
        // buscar registro no Banco e criar um nó com o id encontrado
        DimensionNode node = new DimensionNode(id, new ArrayList<>());

        // buscar oau do nó
        List<Long> parentIds = queryForParentIds(id);

        // para cada pai, criar um nó correspondente e adicionar a lista de pais do nó atual
        for (Long parentId : parentIds) {
            DimensionNode parent = showRelationTree(parentId);
            node.getParents().add(parent);
        }

        return node;
    }

    private List<Long> queryForParentIds(Long id){
        List<Long> parentIds = new ArrayList<>();
        String sql = "SELECT id FROM dimension WHERE sonid = ?";
        try  (Connection conn = dataSource.getConnection();
              PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                parentIds.add(rs.getLong("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return parentIds;
    }


    @Override
    public DimensionNode showRelationMember(Long id) {
        DimensionNode nodes = showRelationTree(id);
        String select = buildSelect(nodes);
        String sql = "SELECT " + select + " FROM dimension_" + id + " d" + id;

        // Adicionar um JOIN para cada nó filho
        for(DimensionNode parent: nodes.getParents()){
            sql += " JOIN dimension_" + parent.getId() + " d" + parent.getId() + " ON d" +
                   parent.getId() + ".id = d" + nodes.getId() + ".dimension_" + parent.getId();

            // Adicionar os JOINs para os nós filhos dos nós pais recursivamente
            sql += buildJoin(parent);
        }

        System.out.println(sql);
        return null;
    }

    private String buildSelect(DimensionNode node) {
        String select = "d"+ node.getId() + ".id, d" + node.getId() + ".name";

        for (DimensionNode filho : node.getParents()) {
            select += ", " + buildSelect(filho);
        }

        return select;
    }

    private String buildJoin(DimensionNode node) {
        String joinAdicional = "";

        for (DimensionNode filho : node.getParents()) {
            joinAdicional += " JOIN dimension_" + filho.getId() + " d" + filho.getId() + " ON d" +
                             filho.getId() + ".id = d" + node.getId() + ".dimension_" + filho.getId();
            joinAdicional += buildJoin(filho);
        }
        return joinAdicional;
    }


}
