package ru.itis.servlets.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.itis.servlets.models.FileInfo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Component
public class FileRepositoryJdbcTemplateImpl implements FileRepository {

    //language=`SQL`
    private static final String SQL_SELECT_BY_URL = "select * from file_info where url = ?";
    //language=SQL
    private static final String SQL_SELECT_ALL = "select * from file_info";
    //language=SQL
    private static final String SQL_INSERT = "insert into file_info (originalFileName,size,type,ownerEmail) values (?,?,?,?)";

    private static final String SQL_UPDATE = "update file_info set (storageFileName,originalFileName,size,type,url,owneremail) = (?,?,?,?,?,?) where id=?";

    RowMapper<FileInfo> fileInfoRowMapper = (ResultSet rs, int id) -> FileInfo.builder()
            .id(rs.getLong("id"))
            .storageFileName(rs.getString("storageFileName"))
            .originalFileName(rs.getString("originalFileName"))
            .size(rs.getLong("size"))
            .type(rs.getString("type"))
            .url(rs.getString("url"))
            .ownerMail(rs.getString("owneremail"))
            .build();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Optional<FileInfo> find(Long aLong) {
        return Optional.empty();
    }

    @Override
    public Optional<FileInfo> findByUrl(String url) {
        List<FileInfo> fileInfos = jdbcTemplate.query(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_URL);
            statement.setString(1, url);
            return statement;
        }, fileInfoRowMapper);
        if (fileInfos.size() == 0) {
            return Optional.empty();
        } else {
            return Optional.of(fileInfos.get(0));
        }
    }

    @Override
    public List<FileInfo> findAll() {
        return null;
    }

    @Override
    public void save(FileInfo entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getOriginalFileName());
            statement.setLong(2, entity.getSize());
            statement.setString(3, entity.getType());
            statement.setString(4, entity.getOwnerMail());
            return statement;
        }, keyHolder);
        Long id = ((Long) keyHolder.getKeys().get("id"));
        entity.setId(id);
        entity.setStorageFileName(id.toString());
        entity.setUrl(id.toString());
        update(entity);
    }

    public void update(FileInfo entity) {
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL_UPDATE);

            statement.setString(1, entity.getStorageFileName());
            statement.setString(2, entity.getOriginalFileName());
            statement.setLong(3, entity.getSize());
            statement.setString(4, entity.getType());
            statement.setString(5, entity.getUrl());
            statement.setString(6, entity.getOwnerMail());
            statement.setLong(7, entity.getId());
            return statement;
        });
    }

    @Override
    public void delete(Long aLong) {

    }
}
