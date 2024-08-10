package throttling;

import java.util.HashMap;
import java.util.Map;

public class GeoBasedThrottling {

    private static final int MAX_REQUESTS_PER_REGION = 3;
    private static final Map<String, Integer> regionRequestCount = new HashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            final String region = "region" + (i % 2); // Simulate two regions
            new Thread(() -> processRequestByRegion(region)).start();
        }
    }

    private static synchronized void processRequestByRegion(String region) {
        int requestCount = regionRequestCount.getOrDefault(region, 0);

        if (requestCount < MAX_REQUESTS_PER_REGION) {
            regionRequestCount.put(region, requestCount + 1);
            System.out.println("Processing request for " + region);
        } else {
            System.out.println("Request limit reached for " + region);
        }
    }
}
