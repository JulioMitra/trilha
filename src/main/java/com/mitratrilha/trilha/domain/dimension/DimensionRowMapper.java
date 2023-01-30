package com.mitratrilha.trilha.domain.dimension;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DimensionRowMapper implements RowMapper<DimensionDetailMemeber> {
    @Override
    public DimensionDetailMemeber mapRow(ResultSet resultSet, int i) throws SQLException {
        DimensionDetailMemeber dimension = new DimensionDetailMemeber(
                resultSet.getString("name")
        );
        return dimension;
    }
}
