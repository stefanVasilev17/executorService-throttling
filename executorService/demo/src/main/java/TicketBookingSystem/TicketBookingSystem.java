package TicketBookingSystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketBookingSystem {
    private final AtomicInteger availableTickets;
    private final Semaphore semaphore;

    public TicketBookingSystem(int initialTicketCount, int maxConcurrentBookings) {
        this.availableTickets = new AtomicInteger(initialTicketCount);
        this.semaphore = new Semaphore(maxConcurrentBookings);
    }

    public void bookTicket() {
        try {
            semaphore.acquire();

            int remainingTickets;
            do {
                remainingTickets = availableTickets.get();
                if (remainingTickets <= 0) {
                    System.out.println(Thread.currentThread().getName() + ": No tickets left to book.");
                    return;
                }
            } while (!availableTickets.compareAndSet(remainingTickets, remainingTickets - 1));

            System.out.println(Thread.currentThread().getName() + " successfully booked a ticket. Tickets left: " + availableTickets.get());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        final int totalTickets = 10;
        final int maxConcurrentBookings = 3; // Throttle to allow only 3 concurrent bookings
        TicketBookingSystem bookingSystem = new TicketBookingSystem(totalTickets, maxConcurrentBookings);

        ExecutorService executorService = Executors.newCachedThreadPool();

        Runnable bookTask = () -> {
            for (int i = 0; i < 5; i++) {
                bookingSystem.bookTicket();
                try {
                    Thread.sleep((int)(Math.random() * 100)); // Simulate some delay in booking
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };

        executorService.execute(bookTask);
        executorService.execute(bookTask);
        executorService.execute(bookTask);
        executorService.execute(bookTask); // Additional threads to simulate a burst
        executorService.execute(bookTask);

        executorService.shutdown();

        try {
            // Wait for all tasks to complete
            if (!executorService.awaitTermination(1, TimeUnit.MINUTES)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("Booking process completed. Remaining tickets: " + bookingSystem.availableTickets.get());
    }
}
