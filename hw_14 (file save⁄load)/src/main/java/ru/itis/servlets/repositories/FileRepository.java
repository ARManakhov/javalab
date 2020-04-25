package ru.itis.servlets.repositories;


import ru.itis.servlets.models.FileInfo;

import java.util.Optional;

public interface FileRepository extends CrudRepository<Long, FileInfo> {
    Optional<FileInfo> findByUrl(String url);
}
