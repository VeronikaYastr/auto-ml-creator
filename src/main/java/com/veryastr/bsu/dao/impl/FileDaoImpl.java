package com.veryastr.bsu.dao.impl;

import com.veryastr.bsu.dao.FileDao;
import com.veryastr.bsu.dao.dto.FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class FileDaoImpl implements FileDao {

    private final String INSERT_FILE = "INSERT INTO DATA (filename, downloadUri) VALUES (:filename, :downloadUri)";
    private final String UPDATE_FILE = "UPDATE DATA SET modelId=(:modelId) WHERE filename=(:filename)";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public int createFile(FileDto fileDto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("filename", fileDto.getFilename())
                .addValue("downloadUri", fileDto.getDownloadUri());

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(INSERT_FILE, parameters, keyHolder, new String[]{"id"});
        return (int) Optional.ofNullable(keyHolder.getKey()).orElse(-1);
    }

    @Override
    public int updateFile(FileDto fileDto) {
        MapSqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("filename", fileDto.getFilename())
                .addValue("modelId", fileDto.getModelId());

        return jdbcTemplate.update(UPDATE_FILE, parameters);
    }
}
