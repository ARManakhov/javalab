package ru.sirosh;

public class MultipleDownloader {
    Config config;
    public MultipleDownloader(Config config){
        this.config=config;
    }
    public void download(){

        for (String url: config.urls) {
            new SingleDownloader(url,config.savePath).start();
        }
    }
}
