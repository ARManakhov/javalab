package ru.sirosh.protocol;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ru.sirosh.dto.Dto;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)

public class Response {
    private Dto data;

    public Dto getData() {
        return data;
    }

    private Response() {
    }

    private Response(Dto data) {
        this.data = data;
    }

    public static Response build(Dto data) {
        return new Response(data);
    }
}