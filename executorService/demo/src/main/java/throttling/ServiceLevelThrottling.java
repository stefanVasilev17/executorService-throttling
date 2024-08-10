package throttling;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ServiceLevelThrottling {

    private static final int MAX_CONCURRENT_SERVICE_REQUESTS = 4;
    private static final BlockingQueue<Runnable> serviceQueue = new LinkedBlockingQueue<>(MAX_CONCURRENT_SERVICE_REQUESTS);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(MAX_CONCURRENT_SERVICE_REQUESTS);

        for (int i = 0; i < 10; i++) {
            final int taskId = i;
            serviceQueue.offer(() -> {
                processServiceRequest(taskId);
                serviceQueue.poll();
            });
        }

        while (!serviceQueue.isEmpty()) {
            executorService.submit(serviceQueue.poll());
        }

        executorService.shutdown();
    }

    private static void processServiceRequest(int taskId) {
        System.out.println("Processing service request " + taskId + " on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(2000); // Simulate service processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Service request " + taskId + " completed.");
    }
}
