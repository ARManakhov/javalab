package ru.itis.html.generator;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Input {
    String type;
    String name;
    String placeholder;
}
