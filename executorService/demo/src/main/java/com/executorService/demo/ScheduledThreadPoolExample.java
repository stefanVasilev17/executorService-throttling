package com.executorService.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {
        // Create a ScheduledThreadPoolExecutor with 2 core threads

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        // Task to be scheduled
        Runnable task = () -> System.out.println("Executing Task at " + System.nanoTime());

        // Schedule the task to run after a 5-second delay
        scheduler.schedule(task, 5, TimeUnit.SECONDS);

        // Schedule the task to run every 3 seconds, with an initial delay of 2 seconds
        scheduler.scheduleAtFixedRate(task, 2, 3, TimeUnit.SECONDS);

        // Schedule the task to run 3 seconds after the previous execution completes
        scheduler.scheduleWithFixedDelay(task, 2, 3, TimeUnit.SECONDS);

        // Let the scheduler run for a while to observe the tasks being executed
        Thread.sleep(15000);

    }
}


