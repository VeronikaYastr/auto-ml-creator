package com.veryastr.bsu.service;

import com.veryastr.bsu.config.FileStorageConfig;
import com.veryastr.bsu.dao.FileDao;
import com.veryastr.bsu.dao.dto.FileDto;
import com.veryastr.bsu.exceptions.FileStorageException;
import com.veryastr.bsu.exceptions.MlCreatorFileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Slf4j
public class FileStorageService {

    private final Path fileStorageLocation;
    private final FileDao fileDao;

    @Autowired
    public FileStorageService(FileStorageConfig config, FileDao fileDao) {
        this.fileStorageLocation = Paths.get(config.getUploadDir()).toAbsolutePath().normalize();
        this.fileDao = fileDao;
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String filename = "";

        if (file.getOriginalFilename() != null) {
            int i = file.getOriginalFilename().lastIndexOf('.');
            String name = file.getOriginalFilename().substring(0, i);
            filename = StringUtils.cleanPath(file.getOriginalFilename().replace(name,name.concat("-").concat(String.valueOf(System.currentTimeMillis()))));
        } else {
            return filename;
        }

        try {
            if (filename.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid characters: " + filename);
            }

            Path targetLocation = fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            log.info("File {} is uploaded.", filename);
            return filename;
        } catch (IOException e) {
            throw new FileStorageException("Cannot store the file: " + filename, e);
        }
    }

    public String getFullFilename(String filename) {
        return fileStorageLocation.resolve(filename).toAbsolutePath().normalize().toString();
    }

    public void saveFileInfoToDatabase(String filename, String fileDownloadUri) {
        fileDao.createFile(new FileDto().setFilename(filename).setDownloadUri(fileDownloadUri));
    }

    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                log.info("Resource {} for downloading is created.", resource.getFilename());
                return resource;
            } else {
                throw new MlCreatorFileNotFoundException("File not found " + filename);
            }
        } catch (MalformedURLException ex) {
            throw new MlCreatorFileNotFoundException("File not found " + filename, ex);
        }
    }
}
