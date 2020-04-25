package ru.itis.servlets.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.servlets.models.FileInfo;

import java.util.Optional;

@Service
public interface FileService {
    Optional<FileInfo>  getFile(String url);
    Optional<FileInfo> fileSave(MultipartFile multipartFile, String email);
}
