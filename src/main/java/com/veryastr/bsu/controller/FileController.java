package com.veryastr.bsu.controller;

import com.veryastr.bsu.model.UploadedFile;
import com.veryastr.bsu.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final FileStorageService fileStorageService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<UploadedFile> createUser(@RequestParam MultipartFile file) {
        String filename = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files")
                .path("/download/")
                .path(filename)
                .toUriString();

        fileStorageService.saveFileInfoToDatabase(filename, fileDownloadUri);

        return new ResponseEntity<>(new UploadedFile()
                .setFileDownloadUri(fileDownloadUri)
                .setFilename(filename)
                .setFileSize(file.getSize())
                .setFileType(file.getContentType()), HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileStorageService.loadFileAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}

