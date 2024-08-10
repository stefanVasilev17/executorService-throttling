package throttling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeBasedThrottling {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        Runnable task = TimeBasedThrottling::performThrottledTask;
        scheduler.scheduleWithFixedDelay(task, 0, 2, TimeUnit.SECONDS);

        scheduler.schedule(scheduler::shutdown, 20, TimeUnit.SECONDS); // Run for 20 seconds
    }

    private static void performThrottledTask() {
        System.out.println("Performing throttled task on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate task work
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Task completed.");
    }
}

