package com.executorService.demo;

import java.util.concurrent.*;

public class CallableFutureExample {

    public static void main(String[] args) {
        // Create an ExecutorService with a thread pool of one thread
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Create a Callable task that returns a string after a delay
        Callable<String> callableTask = () -> {
            Thread.sleep(2000);
            return "THIS IS THE RESULT!";
        };

        // Submit the Callable task to the executorService and get a Future object
        Future<String> future = executorService.submit(callableTask);

        // Main thread can do some other work here
        System.out.println("Main thread is doing some work...");

        try {
            // Get the result of the Callable task (blocking call)
            String result = future.get();
            System.out.println("Result from Callable: " + result);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            // Shutdown the executor service to free up resources
            executorService.shutdown();
        }
    }
}

