package com.veryastr.bsu.dao.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class FileDto {
    private int id;
    private int modelId;
    private String filename;
    private String downloadUri;
}
