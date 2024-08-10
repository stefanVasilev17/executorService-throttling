package com.executorService.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebPageDownloader {

    public static void main(String[] args) throws
            IllegalArgumentException {
        //Create a list of URLs
        List<String> urls = List.of(
                "https://www.example.com",
                "https://www.wikipedia.org",
                "https://www.github.com"
        );

        //Create an ExecutorService with a fixed thread pool

        ExecutorService executor = Executors.newFixedThreadPool(3);
            //Create a list to hold Future objects
            List<Future<String>> futures = new ArrayList<>();

            //Submit tasks to the ExecutorService
            for (String url : urls) {
                Callable<String> task = () -> downloadWebPage(url);
                futures.add(executor.submit(task));
            }

            //Retrieve and print the results
            for (Future<String> future : futures) {
                try {
                    String content = future.get();
                    System.out.println("Downloaded content: " + content.substring(0, 100) + "...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //Shutdown the ExecutorService
            executor.shutdown();
        }


    // Method to download the content of a web page
    public static String downloadWebPage(String urlString) throws Exception {
        StringBuilder content = new StringBuilder();
        URL url = new URL(urlString);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        return content.toString();
    }
}

