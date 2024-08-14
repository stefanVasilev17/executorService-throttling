package com.executorService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class WebPageDownloader {

    public static void main(String[] args) throws
            IllegalArgumentException {
        List<String> urls = List.of(
                "https://www.example.com",
                "https://www.wikipedia.org",
                "https://www.github.com"
        );


        ExecutorService executor = Executors.newFixedThreadPool(3);
            List<Future<String>> futures = new ArrayList<>();

            for (String url : urls) {
                Callable<String> task = () -> downloadWebPage(url);
                futures.add(executor.submit(task));
            }

            for (Future<String> future : futures) {
                try {
                    String content = future.get();
                    System.out.println("Downloaded content: " + content.substring(0, 100) + "...");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            executor.shutdown();
        }


    /** Method to download the content of a web page **/

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

