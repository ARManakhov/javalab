package ru.sirosh;

import ru.sirosh.context.ApplicationContextReflectionBased;
import ru.sirosh.context.Component;

public class TryContext {
    public static void main(String[] args) {
        ApplicationContextReflectionBased acrb = new ApplicationContextReflectionBased();
        System.out.println(acrb.getComponent(Component.class, "logInService"));

    }
}
