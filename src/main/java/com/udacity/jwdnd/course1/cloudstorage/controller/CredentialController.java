package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }


    @PostMapping
    public ModelAndView credentialUpload(Authentication authentication, Credential credential, Model model) {
        ModelAndView result = new ModelAndView();
        String status = null;
        int userId = userService.getUser(authentication.getName()).getUserId();
        credential.setUserId(userId);

        if(credential.getCredentialId() != null) {
            if(credentialService.updateCredential(credential)) {
                result.addObject("success", true);
                status = "Credential updated successfully";
            } else {
                result.addObject("errorMsg", true);
                status = "Unable to update credential, please try again.";
            }
        } else {
            if(credentialService.addCredential(credential)) {
                result.addObject("success", true);
                status = "Credential save successfully";
            } else {
                result.addObject("errorMsg", true);
                status = "Unable to save credential, please try again.";
            }
        }
        result.setViewName("result");
        result.addObject("message", status);
        return result;
    }

    @GetMapping("/delete/{credentialId}")
    public ModelAndView deleteNote(@PathVariable int credentialId) {
        ModelAndView result = new ModelAndView();
        String status = null;

        boolean deleted = credentialService.deleteCredential(credentialId);
        if(deleted) {
            result.addObject("success", true);
            status = "Credential deleted successfully";
        } else {
            result.addObject("errorMsg", true);
            status = "Unable to delete credential, please try again";
        }

        result.setViewName("result");
        result.addObject("message", status);
        return result;
    }
}
