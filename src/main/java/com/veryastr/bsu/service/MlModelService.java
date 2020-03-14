package com.veryastr.bsu.service;

import com.veryastr.bsu.dao.MlModelDao;
import com.veryastr.bsu.mapper.MlModelMapper;
import com.veryastr.bsu.model.MLModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MlModelService {

    private final MlModelDao mlModelDao;
    private final UserService userService;
    private final MlModelMapper mapper;

    public List<MLModel> getAllModelsForUser(long userId) {
        log.debug("Getting all models for user {}.", userId);
        List<MLModel> models = mapper.fromDtoList(mlModelDao.getAllModelsForUser(userId));
        for (MLModel model : models) {
            model.setUser(userService.getUserById(userId));
        }
        return models;
    }
}
