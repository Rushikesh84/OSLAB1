import java.util.*;

public class OptimalPageReplacement {

    // Function to find the page to replace
    static int predict(int[] pages, List<Integer> frame, int currentIndex) {
        int res = -1, farthest = currentIndex;

        for (int i = 0; i < frame.size(); i++) {
            int j;
            for (j = currentIndex; j < pages.length; j++) {
                if (frame.get(i) == pages[j]) {
                    if (j > farthest) {
                        farthest = j;
                        res = i;
                    }
                    break;
                }
            }
            if (j == pages.length) {
                return i; // page not used again
            }
        }

        return (res == -1) ? 0 : res;
    }

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

        List<Integer> frame = new ArrayList<>();
        int pageFaults = 0;

        for (int i = 0; i < n; i++) {
            int page = pages[i];

            if (!frame.contains(page)) {
                if (frame.size() < capacity) {
                    frame.add(page); // empty space available
                } else {
                    int indexToReplace = predict(pages, frame, i + 1);
                    frame.set(indexToReplace, page); // replace page
                }
                pageFaults++;
            }

            // Print current frame state
            System.out.println("Frame after page " + page + ": " + frame);
        }

        System.out.println("\nTotal Page Faults = " + pageFaults);
        sc.close();
    }
}

