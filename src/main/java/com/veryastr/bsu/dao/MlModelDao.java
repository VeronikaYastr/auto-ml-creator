package com.veryastr.bsu.dao;

import com.veryastr.bsu.dao.dto.MLModelDto;

import java.util.List;

public interface MlModelDao {
    List<MLModelDto> getAllModelsForUser(long userId);
}
