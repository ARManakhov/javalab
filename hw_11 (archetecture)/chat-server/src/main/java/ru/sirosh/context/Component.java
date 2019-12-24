package ru.sirosh.context;

import ru.sirosh.dto.Dto;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;

public interface Component {
    public String getComponentName();
    public Dto execute(Request req);
}