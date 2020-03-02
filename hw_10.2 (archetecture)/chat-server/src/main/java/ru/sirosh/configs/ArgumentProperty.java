package ru.sirosh.configs;

public class ArgumentProperty {
    private String dbPropPath;
    private String serverPort;

    public String getDbPropPath() {
        return dbPropPath;
    }

    public void setDbPropPath(String dbPropPath) {
        this.dbPropPath = dbPropPath;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public ArgumentProperty(String dbPropPath, String serverPort) {
        this.dbPropPath = dbPropPath;
        this.serverPort = serverPort;
    }
}
