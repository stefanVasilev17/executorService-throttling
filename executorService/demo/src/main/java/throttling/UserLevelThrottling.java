package throttling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class UserLevelThrottling {

    private static final int MAX_REQUESTS_PER_USER = 5;
    private static final Map<String, Integer> userRequestCounts = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        scheduler.scheduleAtFixedRate(userRequestCounts::clear, 1, 1, TimeUnit.MINUTES);

        for (int i = 0; i < 10; i++) {
            final String userId = "user" + (i % 3);
            new Thread(() -> processUserRequest(userId)).start();
        }
    }

    private static void processUserRequest(String userId) {
        int requestCount = userRequestCounts.getOrDefault(userId, 0);

        if (requestCount < MAX_REQUESTS_PER_USER) {
            userRequestCounts.put(userId, requestCount + 1);
            System.out.println("Processing request for " + userId);
        } else {
            System.out.println("Too many requests for " + userId);
        }
    }
}
