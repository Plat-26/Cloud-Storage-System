package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    public ModelAndView fileUpload(@RequestParam("fileUpload") MultipartFile file, Authentication auth) {
        ModelAndView result = new ModelAndView();
        String status = null;

        try {
            if(fileService.addFile(file, auth.getName())) {
                result.addObject("success", true);
                status = "Uploaded the file: " + file.getOriginalFilename() +" successfully";
            }
        } catch (IOException ex) {
            result.addObject("errorMsg", true);
            status = "Unable to upload the file: " + file.getOriginalFilename();
        }
        result.setViewName("result");
        result.addObject("message", status);
        return result;
    }

    @GetMapping("/delete/{fileId}")
    public ModelAndView deleteFile(@PathVariable int fileId) {
        ModelAndView result = new ModelAndView();
        String status = "";

        if(fileService.deleteFile(fileId)) {
            result.addObject("success", true);
            status = "File deleted";
        } else {
            result.addObject("errorMsg", true);
            status = "Unable to delete file";
        }
        result.setViewName("result");
        result.addObject("message", status);
        return result;
    }

    @GetMapping("/download/{fileId}")
    public void downloadFile(@PathVariable int fileId, HttpServletResponse response) throws IOException {
        File file = fileService.getFileById(fileId);

        response.setContentType(file.getContentType());
        response.setContentLength(Integer.parseInt(file.getFileSize()));
        String headerKey = "Content-View";
        String headerValue = "file:" + file.getFilename();
        response.setHeader(headerKey, headerValue);
        response.getOutputStream().write(file.getFileData());
    }
}
