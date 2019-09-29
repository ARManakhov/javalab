package com.company;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader extends Thread {
    private String url;
    private String name;

    Downloader(String url){
        this.url = url;
        String[] buff = url.split("/");
        name = buff[buff.length-1];
    }

    @Override
    public void run() {
        try  {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            name = antiDouble(name);
            FileOutputStream out = new FileOutputStream(name);
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                out.write(dataBuffer, 0, bytesRead);
            }
            System.out.println("downloaded " + url + " as " + name);
        } catch (FileNotFoundException e) {
            System.out.println("wrong url " + url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String antiDouble(String name){
        if (new File(name).exists()){
        return antiDouble(name,1);
        }else{
            return name;
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
