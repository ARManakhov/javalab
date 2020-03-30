package ru.itis.servlets.services;


import ru.itis.servlets.dto.SignUpDto;

public interface SignUpService {
    void signUp(SignUpDto form);
}
