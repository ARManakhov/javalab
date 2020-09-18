package dev.sirosh.documentConstructor;

import dev.sirosh.model.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface DocumentConstructor {
    void construct(User user, String filename) throws IOException;
}
