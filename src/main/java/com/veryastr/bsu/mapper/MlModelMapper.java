package com.veryastr.bsu.mapper;

import com.veryastr.bsu.dao.dto.MLModelDto;
import com.veryastr.bsu.model.MLModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface MlModelMapper {
    @Mapping(target = "type", expression = "java(model.getMlModelType().getValue())")
    MLModelDto toDto(MLModel model);

    @Mapping(target = "mlModelType", source = "type")
    MLModel fromDto(MLModelDto dto);

    List<MLModelDto> toDtoList(List<MLModel> user);

    List<MLModel> fromDtoList(List<MLModelDto> dto);
}
