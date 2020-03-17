package com.veryastr.bsu.service;

import com.veryastr.bsu.dao.UserDao;
import com.veryastr.bsu.dao.dto.UserDto;
import com.veryastr.bsu.mapper.UserMapper;
import com.veryastr.bsu.model.Id;
import com.veryastr.bsu.model.User;
import com.veryastr.bsu.model.UserWithPassword;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;
    private final UserMapper mapper;

    public Id createUser(UserWithPassword user) {
        UserDto userDto = mapper.toDto(user);
        log.debug("Creating new user <{}>", user.getUsername());
        int createdId = userDao.createUser(userDto);
        return new Id(createdId);
    }

    public UserWithPassword login(UserWithPassword user) {
        log.debug("Login user <email={}>", user.getEmail());
        return mapper.fromDtoToUserWithPassword(userDao.login(mapper.toDto(user)));
    }

    public List<UserWithPassword> getAllUsers() {
        log.debug("Getting all users.");
        return mapper.fromDtoList(userDao.getAllUsers());
    }

    public User getUserById(long userId) {
        return mapper.fromDtoToUser(userDao.getUserById(userId));
    }
}
