package ru.itis.servlets.controllers;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import ru.itis.servlets.models.FileInfo;
import ru.itis.servlets.services.FileService;
import ru.itis.servlets.services.FileServiceImpl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Controller
public class FilesController {


    @Autowired
    FileService fileService;

    @Resource(name = "uploadsDir")
    String uploadsDir;

    @GetMapping("/file")
    public String getUploadFilePage() {
        return "file_upload";
    }

    @PostMapping("/file")
    public String uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("email") String email, Model model) {
        if (!multipartFile.isEmpty()) {
            Optional<FileInfo> optionalFileInfo = fileService.fileSave(multipartFile, email);
            if (optionalFileInfo.isPresent()) {
                model.addAttribute("fileInfo", optionalFileInfo.get());
            }
        }
        return "file_upload";
    }

    // localhost:8080/files/123809183093qsdas09df8af.jpeg

    @RequestMapping(value = "/files/{file-name:.+}", method = RequestMethod.GET)
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        Optional<FileInfo> optionalFileInfo = fileService.getFile(fileName);
        if (optionalFileInfo.isPresent()) {
            File file = new File(uploadsDir + "/" + optionalFileInfo.get().getStorageFileName());
            try {
                InputStream is = new FileInputStream(file);
                org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
                response.flushBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
            }
        } else {
            throw new ResponseStatusException(NOT_FOUND, "Unable to find resource");
        }
    }
}
