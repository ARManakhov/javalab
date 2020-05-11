package ru.itis.security.token;

public interface GetTokenService {
    String getToken(String username, String password) throws Exception;
}
