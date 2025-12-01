import java.util.*;

public class LRUPageReplacement {

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

        // Input number of frames
        System.out.print("Enter number of frames: ");
        int capacity = sc.nextInt();

        // LRU uses LinkedHashMap (ordered by access)
        LinkedHashMap<Integer, Integer> frame = new LinkedHashMap<>(capacity, 0.75f, true);
        int pageFaults = 0;

        for (int page : pages) {

            // If page is not in frame -> page fault
            if (!frame.containsKey(page)) {
                // If frame is full, remove LRU page (first entry)
                if (frame.size() == capacity) {
                    int lruPage = frame.entrySet().iterator().next().getKey();
                    frame.remove(lruPage);
                }
                frame.put(page, 1); // insert new page
                pageFaults++;
            } else {
                frame.get(page); // Access to update LRU order
            }

            // Print current frame state
            System.out.println("Frame after page " + page + ": " + frame.keySet());
        }

        System.out.println("\nTotal Page Faults = " + pageFaults);
        sc.close();
    }
}
