package com.veryastr.bsu.model;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UploadedFile {
    String filename;
    String fileType;
    String fileDownloadUri;
    Long fileSize;
}
