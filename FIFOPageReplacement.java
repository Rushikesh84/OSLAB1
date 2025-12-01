import java.util.*;

public class FIFOPageReplacement {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input reference string
        System.out.print("Enter number of pages in reference string: ");
        int n = sc.nextInt();

        int[] pages = new int[n];
        System.out.println("Enter the reference string:");
        for (int i = 0; i < n; i++) {
            pages[i] = sc.nextInt();
        }

        // Input frame size
        System.out.print("Enter number of frames: ");
        int capacity = sc.nextInt();

        // FIFO Implementation
        Queue<Integer> frame = new LinkedList<>();
        int pageFaults = 0;

        for (int i = 0; i < n; i++) {
            int page = pages[i];

            // If page not present → page fault
            if (!frame.contains(page)) {
                // If frame is full, remove oldest (FIFO)
                if (frame.size() == capacity) {
                    frame.poll(); // remove front
                }
                frame.add(page); // insert new page
                pageFaults++;
            }

            // Print current frame state
            System.out.print("Frame after accessing page " + page + ": ");
            System.out.println(frame);
        }

        System.out.println("\nTotal Page Faults = " + pageFaults);

        sc.close();
    }
}
