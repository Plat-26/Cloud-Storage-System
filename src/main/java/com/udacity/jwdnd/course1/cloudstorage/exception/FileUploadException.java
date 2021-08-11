package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class FileUploadException {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleFileSizeLimitExceededException(MaxUploadSizeExceededException ex, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView result = new ModelAndView();
        result.setViewName("result");
        String status = "File is too large, file size limit is 5MB";
        result.addObject("errorMsg", true);
        result.addObject("message", status);
        return result;
    }
}
