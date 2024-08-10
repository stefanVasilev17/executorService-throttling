package throttling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ResourceLevelThrottling {

    private static final int MAX_CONCURRENT_DB_CONNECTIONS = 3;
    private static final Semaphore dbSemaphore = new Semaphore(MAX_CONCURRENT_DB_CONNECTIONS);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    dbSemaphore.acquire();
                    accessDatabase();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    dbSemaphore.release();
                }
            });
        }

        executorService.shutdown();
    }

    private static void accessDatabase() {
        System.out.println("Accessing database on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate database access
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Database access completed.");
    }
}
