package ru.sirosh;

public class MultiplePathException extends Exception {
    MultiplePathException(){
        super("Multiple path variants");
    }
}
