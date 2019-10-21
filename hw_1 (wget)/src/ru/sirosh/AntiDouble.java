package ru.sirosh;

import java.io.File;

public class AntiDouble {
    private String filePath;
    AntiDouble(String filePath){
        this.filePath = filePath;
    }

    public String getName(){
        return antiDouble(filePath);
    }


    private String antiDouble(String path){
        if (new File(path).exists()){
            return antiDouble(path,1);
        }else{
            return path;
        }
    }

    private String antiDouble(String name, int i){
        String[] buff = name.split("\\.");
        StringBuilder newname = new StringBuilder();
        if (buff.length > 1){
            for (int j = 0; j < buff.length-2; j++) {
                newname.append(buff[j]).append(".");
            } newname.append(buff[buff.length-2]).append(" (").append(i).append(")").append(buff[buff.length-1]);
        }else {
            newname.append(buff[0]).append(" (").append(i).append(")");
        }
        if (new File(newname.toString()).exists()){
            return antiDouble(name,++i);
        }else{
            return newname.toString();
        }
    }
}
