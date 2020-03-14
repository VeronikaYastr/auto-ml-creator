package com.veryastr.bsu.dao.impl;

import com.veryastr.bsu.dao.MlModelDao;
import com.veryastr.bsu.dao.dto.MLModelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class MlModelDaoImpl implements MlModelDao {

    private final String SELECT_ALL_MODELS = "SELECT * FROM MODELS WHERE userId=(:userId)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<MLModelDto> getAllModelsForUser(long userId) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("userId", userId);

        return jdbcTemplate.query(SELECT_ALL_MODELS, parameters, (rs, rowNum) ->
                new MLModelDto()
                        .setId(rs.getInt("id"))
                        .setCreatedAt(rs.getDate("createdAt").toLocalDate())
                        .setModifiedAt(rs.getDate("modifiedAt").toLocalDate())
                        .setDescription(rs.getString("description"))
                        .setTitle(rs.getString("title"))
                        .setType(rs.getString("type"))
        );
    }

}
