package com.veryastr.bsu.controller;

import com.veryastr.bsu.model.MLModel;
import com.veryastr.bsu.service.MlModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ml-models")
@RequiredArgsConstructor
@Slf4j
public class MLModelController {

    private final MlModelService mlModelService;

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<List<MLModel>> getUsersList(@PathVariable long userId) {
        List<MLModel> models = mlModelService.getAllModelsForUser(userId);
        log.debug("All models: {}.", models);
        return new ResponseEntity<>(models, HttpStatus.OK);
    }
}

