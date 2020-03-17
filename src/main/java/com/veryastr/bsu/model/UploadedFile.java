package com.veryastr.bsu.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.UUID;

@Data
@Accessors(chain = true)
public class UploadedFile {
    String filename;
    String fileType;
    String fileDownloadUri;
    Long fileSize;
    UUID datasetId;
    String[] columnNames;
    List<List<String>> firstLines;
}
