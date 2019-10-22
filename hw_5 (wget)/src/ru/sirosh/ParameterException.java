package ru.sirosh;

public class ParameterException extends Exception {
    ParameterException(){
        super("Wrong path parameter");
    }
}
