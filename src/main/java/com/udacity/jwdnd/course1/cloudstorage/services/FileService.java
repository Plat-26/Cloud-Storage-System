package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    boolean filenameIsAvailable(String filename) {
        return fileMapper.getFileByName(filename) == null;
    }

    public File getFile(String name) {
        return fileMapper.getFileByName(name);
    }

    //TODO: PREVENT USERS FROM ADDING MULTIPLE Files
    public boolean addFile(MultipartFile multipartFile) throws IOException {

        if(filenameIsAvailable(multipartFile.getOriginalFilename())) {

            File file = new File(
                    null,
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    Long.toString(multipartFile.getSize()),
                    multipartFile.getBytes()
            );

            fileMapper.insertFile(file);
            return true;
        }
        return false;
    }

    ///todo:implement method in file mapper
    public List<File> getAllFiles() {
        return fileMapper.getAllFiles();
    }

    public boolean updateFile(File file) {
        File optionalFile = fileMapper.getFileById(file.getFileId());

        if(optionalFile != null) {
            if(!optionalFile.getFilename().equals(file.getFilename())) {
                if (!filenameIsAvailable(file.getFilename())) {
                    return false;
                }
            }

            fileMapper.updateFile(file);
            return true;
        }
        return false;
    }

    public boolean deleteFile(String filename) {
        File file = fileMapper.getFileByName(filename);
        if (file != null) {
            return fileMapper.deleteFile(file) > 0;
        }
        return false;
    }
}
