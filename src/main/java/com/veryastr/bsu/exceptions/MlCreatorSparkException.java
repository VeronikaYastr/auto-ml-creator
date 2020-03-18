package com.veryastr.bsu.exceptions;

public class MlCreatorSparkException extends RuntimeException {
    public MlCreatorSparkException(String message) {
        super(message);
    }

    public MlCreatorSparkException(String message, Throwable cause) {
        super(message, cause);
    }
}
