package com.veryastr.bsu.dao;

import com.veryastr.bsu.dao.dto.UserDto;

import java.util.List;

public interface UserDao {
    int createUser(UserDto user);

    List<UserDto> getAllUsers();

    UserDto getUserById(long userId);

    UserDto login(UserDto user);
}
