package ru.itis.html.generator;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TemplateProcessor {
    private final Configuration configuration;
    private static final String TEMPLATE = "form_template.ftl";
    public static final String OUTPUT = "generated_form.html";

    @SneakyThrows
    public TemplateProcessor() {
        configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration.setDirectoryForTemplateLoading(new File("src/main/resources/"));
    }

    @SneakyThrows
    public void processForm(Form form) {
        Template template = configuration.getTemplate(TEMPLATE);
        Map<String, Object> data = new HashMap<>();
        data.put("form", form);
        template.process(data, new FileWriter(LocalDate.now().toString() + ": " + OUTPUT));
    }
}
