package com.veryastr.bsu.mapper;

import com.veryastr.bsu.dao.dto.UserDto;
import com.veryastr.bsu.model.User;
import com.veryastr.bsu.model.UserWithPassword;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDto toDto(UserWithPassword user);

    UserWithPassword fromDtoToUserWithPassword(UserDto dto);

    User fromDtoToUser(UserDto dto);

    List<UserDto> toDtoList(List<UserWithPassword> user);

    List<UserWithPassword> fromDtoList(List<UserDto> dto);
}
