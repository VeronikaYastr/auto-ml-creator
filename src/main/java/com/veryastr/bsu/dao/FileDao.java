package com.veryastr.bsu.dao;

import com.veryastr.bsu.dao.dto.FileDto;

public interface FileDao {
    int createFile(FileDto fileDto);

    int updateFile(FileDto fileDto);
}
