package ru.sirosh;

public class MultiplePathException extends Exception {
    MultiplePathException(){
        super("Wrong path parameter");
    }
}
