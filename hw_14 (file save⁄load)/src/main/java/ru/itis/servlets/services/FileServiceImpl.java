package ru.itis.servlets.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.servlets.models.FileInfo;
import ru.itis.servlets.repositories.FileRepository;

import javax.annotation.Resource;
import java.io.*;
import java.util.Optional;

@Service
public class FileServiceImpl implements FileService {

    @Resource(name = "uploadsDir")
    String uploadsDir;

    @Autowired
    FileRepository fileRepository;

    @Autowired
    MailService mailService;

    @Override
    public Optional<FileInfo> getFile(String url) {
        return fileRepository.findByUrl(url);
    }

    @Override
    public Optional<FileInfo> fileSave(MultipartFile multipartFile, String email) {
        FileInfo fileInfo = FileInfo.builder().ownerMail(email).size(multipartFile.getSize()).type(multipartFile.getContentType()).originalFileName(multipartFile.getOriginalFilename()).build();
        fileRepository.save(fileInfo);
        try {
            byte[] bytes = multipartFile.getBytes();
            File systemFile = new File(uploadsDir + "/"  + fileInfo.getStorageFileName());
            System.out.println(systemFile.getCanonicalPath());
            BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(systemFile));
            System.out.println(buffStream);
            buffStream.write(bytes);
            buffStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
        mailService.sendFileUrl(fileInfo);
        return Optional.of(fileInfo);
    }

}
