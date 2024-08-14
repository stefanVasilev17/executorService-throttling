package com.executorService.demo;

import java.util.concurrent.*;

public class CallableFutureExample {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Callable<String> callableTask = () -> {
            Thread.sleep(2000);
            return "THIS IS THE RESULT!";
        };

        Future<String> future = executorService.submit(callableTask);
        System.out.println("Main thread is doing some work...");

        try {
            String result = future.get();
            System.out.println("Result from Callable: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }
}

