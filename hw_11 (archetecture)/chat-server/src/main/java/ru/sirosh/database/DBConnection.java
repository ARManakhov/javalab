package ru.sirosh.database;

import ru.sirosh.configs.DbProperty;

import java.sql.*;

public class DBConnection {
    private DbProperty dbprop;
    public  DBConnection(DbProperty dbprop){
        this.dbprop = dbprop;
    }
    public Connection connect() throws SQLException {
        Connection conn = DriverManager.getConnection(dbprop.getUrl(), dbprop.getProps());
        return conn;
    }
}
