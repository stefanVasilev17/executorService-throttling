package throttling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ApplicationLevelThrottling {

    private static final int MAX_CONCURRENT_REQUESTS = 5;
    private static final Semaphore semaphore = new Semaphore(MAX_CONCURRENT_REQUESTS);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                try {
                    semaphore.acquire();
                    processRequest();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    semaphore.release();
                }
            });
        }

        executorService.shutdown();
    }

    private static void processRequest() {
        System.out.println("Processing request on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate request processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Request processed.");
    }
}
