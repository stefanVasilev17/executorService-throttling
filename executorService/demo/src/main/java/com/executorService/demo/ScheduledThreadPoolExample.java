package com.executorService.demo;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolExample {

    public static void main(String[] args) throws InterruptedException {

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
        Runnable task = () -> System.out.println("Executing Task at " + System.nanoTime());

        scheduler.schedule(task, 5, TimeUnit.SECONDS);

        scheduler.scheduleAtFixedRate(task, 2, 3, TimeUnit.SECONDS);

        scheduler.scheduleWithFixedDelay(task, 2, 3, TimeUnit.SECONDS);

        Thread.sleep(15000);

    }
}


