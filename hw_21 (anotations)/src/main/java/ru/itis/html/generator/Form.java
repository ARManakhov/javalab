package ru.itis.html.generator;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Form {
    String method;
    String action;
    List<Input> inputs;
}
