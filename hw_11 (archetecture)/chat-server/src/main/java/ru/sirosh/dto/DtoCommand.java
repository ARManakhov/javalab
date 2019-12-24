package ru.sirosh.dto;

public class DtoCommand implements Dto {
    String command;

    private DtoCommand() {
    }

    public DtoCommand(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
