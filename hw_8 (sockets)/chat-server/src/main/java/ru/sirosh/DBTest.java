package ru.sirosh;

import ru.sirosh.Models.User;
import ru.sirosh.Repositories.UsersRepositoryJdbcImpl;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

public class DBTest{
    public static void main(String[] args) throws SQLException, FileNotFoundException {
        DBPropReader pr = new DBPropReader("db.properties");
        Connection connection = new DBConnection(pr.read()).connect();
        User user = new User("sirossssssssh","supersecretpassword");
        new UsersRepositoryJdbcImpl(connection).save(user);
        System.out.println(user.id);
        System.out.println("success");
    }
}
