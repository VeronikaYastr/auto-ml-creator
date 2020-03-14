package com.veryastr.bsu.model;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Data
@Accessors(chain = true)
public class User {
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String email;
    @NotNull
    private String password;
}
