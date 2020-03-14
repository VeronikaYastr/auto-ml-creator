package com.veryastr.bsu.dao.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Accessors(chain = true)
public class MLModelDto {
    private int id;
    private int userId;
    private LocalDate createdAt;
    private LocalDate modifiedAt;
    private String type;
    private String title;
    private String description;
}
