package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/files")
    public String getHomePage(Model model) {
        model.addAttribute("files", fileService.getAllFiles());
        //TODO: DISPLAY LIST OF FILES
        return "home";
    }

    @PostMapping
    public String fileUpload(MultipartFile multipartFile, Model model) {
        String fileErrorMsg = null;

        try {
            fileService.addFile(multipartFile);
            fileErrorMsg = "Uploaded the file: " + multipartFile.getOriginalFilename() +" successfully";
        } catch (IOException ex) {
            fileErrorMsg = "Unable to upload the file: " + multipartFile.getOriginalFilename();
        }

        model.addAttribute("uploadStatus", fileErrorMsg);
        model.addAttribute("files", fileService.getAllFiles());
        return "home";
    }

}
