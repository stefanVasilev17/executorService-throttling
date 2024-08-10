package throttling;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class APILevelThrottling {

    private static final int MAX_REQUESTS_PER_MINUTE = 10;
    private static int requestCount = 0;
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static void main(String[] args) {
        scheduler.scheduleAtFixedRate(() -> requestCount = 0, 1, 1, TimeUnit.MINUTES);

        for (int i = 0; i < 15; i++) {
            new Thread(APILevelThrottling::processApiRequest).start();
        }

        scheduler.shutdown();
    }

    private static synchronized void processApiRequest() {
        if (requestCount < MAX_REQUESTS_PER_MINUTE) {
            requestCount++;
            System.out.println("Processing API request " + requestCount);
        } else {
            System.out.println("API request limit reached, please try again later.");
        }
    }
}
