package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final CredentialService credentialService;
    private final FileService fileService;
    private final NoteService noteService;
    private final UserService userService;

    public HomeController(CredentialService credentialService, FileService fileService, NoteService noteService, UserService userService) {
        this.credentialService = credentialService;
        this.fileService = fileService;
        this.noteService = noteService;
        this.userService = userService;
    }

    @GetMapping
    public String getHomeView(Authentication authentication, Credential credential, Note note, File file, Model model) {
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.getFilesByUser(userId));
        model.addAttribute("notes", noteService.getNoteByUser(userId));
        model.addAttribute("credentials", credentialService.getCredentialsByUser(userId));
        return "home";
    }
}

