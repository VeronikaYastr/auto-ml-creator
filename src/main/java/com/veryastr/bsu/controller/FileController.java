package com.veryastr.bsu.controller;

import com.veryastr.bsu.model.UploadedFile;
import com.veryastr.bsu.service.FileStorageService;
import com.veryastr.bsu.service.SparkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileStorageService fileStorageService;
    private final SparkService sparkService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<UploadedFile> createUser(@RequestParam MultipartFile file) {
        String filename = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files")
                .path("/download/")
                .path(filename)
                .toUriString();

        Dataset<Row> rows = sparkService.readFileIntoDataset(filename);
        UUID datasetId = sparkService.saveDatasetToCache(rows);
        List<List<String>> firstLines = rows.limit(10).collectAsList().stream().map(x -> Arrays.asList(x.mkString(",").split(","))).collect(Collectors.toList());

        fileStorageService.saveFileInfoToDatabase(filename, fileDownloadUri);

        return new ResponseEntity<>(new UploadedFile()
                .setFileDownloadUri(fileDownloadUri)
                .setFilename(filename)
                .setFileSize(file.getSize())
                .setFileType(file.getContentType())
                .setDatasetId(datasetId)
                .setColumnNames(rows.columns())
                .setFirstLines(firstLines), HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileStorageService.loadFileAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

