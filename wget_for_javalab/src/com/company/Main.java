package com.company;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        System.out.println("downloading " + args.length + " files");
        for (int i = 0; i < args.length; i++) {
            System.out.println(i+1 + ": " + args[i]);
        }
        for (String arg:args) {
            new Downloader(arg).start();
        }
   }
}
