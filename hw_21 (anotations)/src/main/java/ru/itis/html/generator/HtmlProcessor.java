package ru.itis.html.generator;


import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.Objects.nonNull;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"ru.itis.html.generator.HtmlForm"})
public class HtmlProcessor extends AbstractProcessor {

    private TemplateProcessor templateProcessor = null;

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element formElement : annotatedElements) {
            List<? extends Element> enclosedElements = formElement.getEnclosedElements();
            HtmlForm htmlFormAnotation = formElement.getAnnotation(HtmlForm.class);
            if (nonNull(htmlFormAnotation)) {
                ArrayList<Input> inputs = new ArrayList<>();
                Form form = Form.builder()
                        .action(htmlFormAnotation.action())
                        .method(htmlFormAnotation.method())
                        .inputs(inputs)
                        .build();
                for (Element inputElement : enclosedElements) {
                    HtmlInput inputAnnotation = inputElement.getAnnotation(HtmlInput.class);
                    if (nonNull(inputAnnotation)) {
                        inputs.add(Input.builder()
                                .name(inputAnnotation.name())
                                .placeholder(inputAnnotation.placeholder())
                                .type(inputAnnotation.type())
                                .build());
                    }
                }
                templateProcessor.processForm(form);
            }
        }
        return true;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        templateProcessor = new TemplateProcessor();
    }
}
