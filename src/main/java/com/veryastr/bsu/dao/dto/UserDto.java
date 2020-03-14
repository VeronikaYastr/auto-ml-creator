package com.veryastr.bsu.dao.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Date;

@Data
@Accessors(chain = true)
public class UserDto {
    private int id;
    private String username;
    private String password;
    private String email;
}
