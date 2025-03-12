import java.util.concurrent.Semaphore;

public class FloorBathroom {
    private final int BATHROOM_STALLS = 6; // Fixed number of stalls
    private final int NUM_EMPLOYEES = 100; // Total number of employees/students
    private final Semaphore stallsSemaphore = new Semaphore(BATHROOM_STALLS, true);

    // Simulate a person using the bathroom
    private void useBathroom(String personName) {
        try {
            System.out.println(personName + " is waiting to use a stall.");
            stallsSemaphore.acquire(); // Acquire a stall
            System.out.println(personName + " is using a stall.");
            Thread.sleep((long) (Math.random() * 3000)); // Simulate using the stall random time between 0 and 3 seconds
            System.out.println(personName + " has finished using the stall.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            stallsSemaphore.release(); // Release the stall
        }
    }

    // Main simulation
    public static void main(String[] args) {
        FloorBathroom floorBathroom = new FloorBathroom();

        // Create and start threads for 100 employees/students
        Thread[] people = new Thread[floorBathroom.NUM_EMPLOYEES];
        for (int i = 0; i < floorBathroom.NUM_EMPLOYEES; i++) {
            String personName = "Person " + (i + 1);
            people[i] = new Thread(() -> floorBathroom.useBathroom(personName));
            people[i].start();
        }

        // Wait for all threads to finish
        for (int i = 0; i < floorBathroom.NUM_EMPLOYEES; i++) {
            try {
                people[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("All employees/students have finished using the bathroom.");
    }
}

