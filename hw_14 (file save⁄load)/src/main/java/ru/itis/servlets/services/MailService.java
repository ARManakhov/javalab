package ru.itis.servlets.services;

import org.springframework.stereotype.Service;
import ru.itis.servlets.models.FileInfo;
@Service
public interface MailService {
    boolean sendFileUrl(FileInfo fileInfo);
}
