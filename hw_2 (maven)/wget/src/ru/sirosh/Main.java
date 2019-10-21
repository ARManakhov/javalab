package ru.sirosh;

public class Main {

    public static void main(String[] args) throws ParameterException, MultiplePathException {
        MultipleDownloader md = new MultipleDownloader(ArgsParser.getInstance().parse(args));
        md.download();
   }
}
