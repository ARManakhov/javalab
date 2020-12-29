package dev.sirosh.poshlopoehalo.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendTokenMail(String token, String email);
}
