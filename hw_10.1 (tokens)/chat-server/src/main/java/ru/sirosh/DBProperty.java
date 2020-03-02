package ru.sirosh;

import java.util.Properties;

public class DBProperty {
    Properties props;
    String url;

    public DBProperty(String url, String user, String password) {
        this.props = new Properties();
        props.setProperty("user", user);            //"postgres"
        props.setProperty("password", password);    //"postgres"
        this.url = url;                             //"jdbc:postgresql://localhost:5432/shop_db"
    }

}
