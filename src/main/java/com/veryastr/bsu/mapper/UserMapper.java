package com.veryastr.bsu.mapper;

import com.veryastr.bsu.dao.dto.UserDto;
import com.veryastr.bsu.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    UserDto toDto(User user);

    User fromDto(UserDto dto);

    List<UserDto> toDtoList(List<User> user);

    List<User> fromDtoList(List<UserDto> dto);
}
