package ru.sirosh;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class SingleDownloader extends Thread {
    private String url;
    private String name;
    private String savePath = null;

    SingleDownloader(String url) {
        this.url = url;
        String[] buff = url.split("/");
        name = buff[buff.length - 1];
    }

    SingleDownloader(String url, String savePath) {
        this.savePath = savePath;
        this.url = url;
        String[] buff = url.split("/");
        name = buff[buff.length - 1];
        if (savePath != null) {
            File savefile = new File(savePath);
            if (!savefile.exists()){
                new File(savePath).mkdir();
            }
            name = savePath + File.separator + name;
        }
    }

    @Override
    public void run() {
        try {
            BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
            name = new AntiDouble(name).getName();
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


}
