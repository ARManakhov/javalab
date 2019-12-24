package ru.sirosh.context;

import ru.sirosh.dto.Dto;
import ru.sirosh.protocol.Request;
import ru.sirosh.protocol.Response;

public interface ViewComponent {
    public String getComponentName();
    public Request execute(Response response);
}
