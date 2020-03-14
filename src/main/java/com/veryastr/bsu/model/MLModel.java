package com.veryastr.bsu.model;

import com.veryastr.bsu.model.enums.MLModelType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class MLModel {
    private int id;
    private MLModelType mlModelType;
    private String title;
    private String description;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private User user;
}
