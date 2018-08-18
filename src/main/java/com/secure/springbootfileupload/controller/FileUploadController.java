package com.secure.springbootfileupload.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class FileUploadController {

    private static String UPLOAD_PATH = "F:\\Java\\SpringBoot\\spring-boot-fileupload\\FileStorage\\";


    @GetMapping("/upload")
    public ModelAndView upload() {
        return new ModelAndView("upload");
    }

    @PostMapping("/upload")
    public ModelAndView fileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        if (file.isEmpty()) {
            return new ModelAndView("status", "message", "Please select a file and try again");
        }

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_PATH + file.getOriginalFilename());
        Files.write(path, bytes);

        return new ModelAndView("status", "message", "File Uploaded successfully");
    }
}
