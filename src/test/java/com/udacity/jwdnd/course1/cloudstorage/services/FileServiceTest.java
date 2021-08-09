package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FileMapper mock_fileMapper;
    @Mock
    private UserService mock_userService;
    @InjectMocks
    private FileService fileService;



    @Test
    void getFilesByUser() {
        fileService.getFilesByUser(1);
        verify(mock_fileMapper, atMostOnce()).getFilesByUser(1);
    }

    @Test
    void getFileById() {
        fileService.getFileById(1);
        verify(mock_fileMapper, atMostOnce()).getFileById(1);
    }

    @Test
    void addFile() throws IOException {
        MockMultipartFile multipartFile = new MockMultipartFile(
                "SomeFile", null, null, (byte[]) null
        );
        File file = new File();
        String username = "user";
        when(mock_userService.getUser(username).getUserId()).thenReturn(1);

        fileService.addFile(multipartFile, username);
        verify(mock_userService, atMostOnce()).getUser(username).getUserId();
        verify(mock_fileMapper, atMostOnce()).insertFile(file);
    }

    @Test
    void getAllFiles() {
        fileService.getAllFiles();
        verify(mock_fileMapper, atMostOnce()).getAllFiles();
    }

    @Test
    void test_deleteFile_sad_path() {
        fileService.deleteFile(1);
        verify(mock_fileMapper).getFileById(1);
    }


    @Test
    void test_deleteFile_happy_path() {
        when(mock_fileMapper.getFileById(1)).thenReturn(new File());

        fileService.deleteFile(1);
        verify(mock_fileMapper).getFileById(1);
        verify(mock_fileMapper).deleteFile(1);
    }
}