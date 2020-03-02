package ru.sirosh.configs;

public class ArgumentProperty {
    private String ip;
    private String port;

    public ArgumentProperty(String ip, String port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }
}
