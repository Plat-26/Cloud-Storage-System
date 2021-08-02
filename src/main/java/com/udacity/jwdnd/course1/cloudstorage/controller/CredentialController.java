package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }

    @GetMapping
    public String getCredentials(Model model) {
        model.addAttribute("credentials", credentialService.getAllCredentials());
        return "home";
    }

    public String credentialUpload(Credential credential, Model model) {
        String credentialStatus = null;

        if(credentialService.addCredential(credential)) {
            credentialStatus = "Saved credential successfully!";
        } else {
            credentialStatus = "Unable to save credential, please try again.";
        }
        model.addAttribute("credentialStatus", credentialStatus);
        model.addAttribute("credentials", credentialService.getAllCredentials());
        return "home";
    }
}
