package throttling;

import com.google.common.util.concurrent.RateLimiter;

public class NetworkLevelThrottling {

    private static final RateLimiter rateLimiter = RateLimiter.create(2.0); // 2 permits per second

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(NetworkLevelThrottling::sendDataOverNetwork).start();
        }
    }

    private static void sendDataOverNetwork() {
        rateLimiter.acquire(); // Acquire a permit before sending data
        System.out.println("Sending data over the network on thread " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000); // Simulate data transmission
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Data sent.");
    }
}
