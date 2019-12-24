package ru.sirosh.dto;

public class DtoCommandd implements Dto {
    String command;

    private DtoCommandd() {
    }

    public DtoCommandd(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
