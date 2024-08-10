package com.executorService.demo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorDemo {
    public static void main(String[] args) {
        // Create a SingleThreadExecutor
        ExecutorService executor = Executors.newSingleThreadExecutor();

            // Submit tasks to the executor
            executor.submit(() -> System.out.println("Task 1 executed by " + Thread.currentThread().getName()));

            executor.submit(() -> System.out.println("Task 2 executed by " + Thread.currentThread().getName()));

            executor.submit(() -> System.out.println("Task 3 executed by " + Thread.currentThread().getName()));

            // Shut down the executor service
            executor.shutdown();
        }
    }

