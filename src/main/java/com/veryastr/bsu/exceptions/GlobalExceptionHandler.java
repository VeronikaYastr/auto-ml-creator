package com.veryastr.bsu.exceptions;

import com.veryastr.bsu.model.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.Collections;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({
            FileStorageException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public final ApiError handleBadRequestException(Exception ex, WebRequest request) {
        log.debug("Handling bad request exception {0}", ex);
        return new ApiError(Collections.singletonList(ex.getMessage()));
    }

    @ExceptionHandler({
            MlCreatorSparkException.class,
            MlCreatorFileNotFoundException.class
    })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public final ApiError handleInternalException(Exception ex, WebRequest request) {
        log.debug("Handling internal exception {0}", ex);
        return new ApiError(Collections.singletonList(ex.getMessage()));
    }
}
