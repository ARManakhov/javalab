package ru.sirosh;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DBPropReader {
    private String propPath;
    private final String[] KEY_LIB = new String[]{"url","user","password"};
    public DBPropReader(String propPath){
        this.propPath = propPath;
    }
    public DBProperty read() throws FileNotFoundException {
        Map<String,String> stringMap= new HashMap<>();
        Scanner fileReader = new Scanner(new File(propPath));
        System.out.println("prop file is" + new File(propPath).getAbsolutePath());
        while (fileReader.hasNextLine()){
            String line = fileReader.nextLine();
            if (line.contains(":")){
                String key  = line.split(":")[0];
                stringMap.put(key, line.replace(key + ":",""));
            }else {
                throw new IllegalArgumentException("wrong prop file");
            }
        }

        for (String key : KEY_LIB) {
            if (!stringMap.containsKey(key)) {
                throw new IllegalArgumentException(key + "not found in db property file");
            }
        }

        return new DBProperty(stringMap.get(KEY_LIB[0]),stringMap.get(KEY_LIB[1]),stringMap.get(KEY_LIB[2]));
    }
}
