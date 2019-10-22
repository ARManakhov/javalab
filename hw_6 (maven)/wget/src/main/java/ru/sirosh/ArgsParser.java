package ru.sirosh;

import java.util.ArrayList;
import java.util.List;

public class ArgsParser {
    private static ArgsParser instance;

    public static ArgsParser getInstance() {
        if (instance == null) {
            instance = new ArgsParser();
        }
        return instance;
    }

    private ArgsParser() {
    }

    public Config parse(String[] args) throws ParameterException, MultiplePathException {
        String path = null;
        List<String> urls = new ArrayList<>();
        boolean haspath = false;
        for (int i = 0; i < args.length; i++) {
            if (path == null && args[i].equals("-path") ) {
                path = args[i + 1];
                if (i + 1 == args.length) {
                    throw new ParameterException();
                }
                i++;
            } else {
                if (path == null){
                    urls.add(args[i]);
                }else {
                    throw new MultiplePathException();
                }
            }
        }

        return new Config(path, urls.toArray(new String[urls.size()]));
    }
}
