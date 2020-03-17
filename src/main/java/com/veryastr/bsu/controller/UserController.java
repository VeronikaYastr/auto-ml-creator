package com.veryastr.bsu.controller;

import com.veryastr.bsu.model.Id;
import com.veryastr.bsu.model.UserWithPassword;
import com.veryastr.bsu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    private final UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<Id> createUser(@RequestBody UserWithPassword user) {
        try {
            Id created = userService.createUser(user);
            log.debug("User <id={}> created.", created.getId());
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (Exception ex) {
            log.debug("Exception while creating new user: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Can't create user.", ex);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<UserWithPassword>> getUsersList() {
        try {
            List<UserWithPassword> users = userService.getAllUsers();
            log.debug("All users: {}.", users);
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception ex) {
            log.debug("Exception while getting all users: {}.", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Getting all users failed.", ex);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Id> login(@RequestBody UserWithPassword user) {
        try {
            UserWithPassword loggedUser = userService.login(user);
            log.debug("User {} successfully logged in.", loggedUser.getUsername());
            Id result = new Id(loggedUser.getId());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            log.debug("Login failed: {}", ex.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Login failed.", ex);
        }
    }
}

