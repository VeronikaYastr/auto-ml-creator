package com.veryastr.bsu.exceptions;

public class MlCreatorFileNotFoundException extends RuntimeException {
    public MlCreatorFileNotFoundException(String message) {
        super(message);
    }

    public MlCreatorFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
