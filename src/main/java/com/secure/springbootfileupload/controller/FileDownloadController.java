package com.secure.springbootfileupload.controller;

import com.secure.springbootfileupload.exception.FileServiceException;
import com.secure.springbootfileupload.exception.ResourceNotFoundException;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;

@RestController
@RequestMapping("/download")
public class FileDownloadController {

    private static String UPLOAD_PATH = "F:\\Java\\SpringBoot\\spring-boot-fileupload\\FileStorage\\";

    @GetMapping("/error")
    public String error() throws ResourceNotFoundException {
        if (null == null) {
            throw new ResourceNotFoundException("File not found");
        }
        return "Done";
    }

    @GetMapping("/error1")
    public String error1() throws Exception {
        if (null == null) {
            throw new Exception("Internal Error");
        }
        return "Done";
    }

    @RequestMapping("/file/{fileName:.+}")
    public void downloadFile(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("fileName") String fileName) throws IOException, ResourceNotFoundException {

        File file = new File(UPLOAD_PATH + fileName);

        if (!file.exists()) {
            throw new ResourceNotFoundException("File not found : "+file.getAbsolutePath());
        }

        if (file.exists()) {
            String mimeType = URLConnection.guessContentTypeFromName(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);

            /**
             * In a regular HTTP response, the Content-Disposition response header is a
             * header indicating if the content is expected to be displayed inline in the
             * browser, that is, as a Web page or as part of a Web page, or as an
             * attachment, that is downloaded and saved locally.
             *
             */

            /**
             * Here we have mentioned it to show inline
             */
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));

            //Here we have mentioned it to show as attachment
            //response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

            response.setContentLength((int) file.length());

            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            FileCopyUtils.copy(inputStream, response.getOutputStream());
        }

    }
}
