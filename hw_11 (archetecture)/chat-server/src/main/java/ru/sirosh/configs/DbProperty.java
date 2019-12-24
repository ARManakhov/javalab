package ru.sirosh.configs;

import java.util.Properties;

public class DbProperty {
    private Properties props;
    private String url;

    public Properties getProps() {
        return props;
    }


    public String getUrl() {
        return url;
    }



    public DbProperty(String url, String user, String password) {
        this.props = new Properties();
        props.setProperty("user", user);            //"postgres"
        props.setProperty("password", password);    //"postgres"
        this.url = url;                             //"jdbc:postgresql://localhost:5432/shop_db"
    }

}
