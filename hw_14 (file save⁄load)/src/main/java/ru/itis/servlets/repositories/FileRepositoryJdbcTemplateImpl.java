package ru.itis.servlets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.servlets.models.FileInfo;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryJdbcTemplateImpl implements FileRepository {

    //language=`SQL`
    private static final String SQL_SELECT_BY_ID = "select * from FileInfo where id = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from FileInfo";
    //language=SQL
    private static final String SQL_INSERT = "insert into FileInfo(storageFileNamem,originalFileName,size,type,url) values (?,?,?,?,?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<FileInfo> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<FileInfo> findAll() {
        return null;
    }

    @Override
    public void save(FileInfo entity) {

    }

    @Override
    public void delete(Long aLong) {

    }
}
