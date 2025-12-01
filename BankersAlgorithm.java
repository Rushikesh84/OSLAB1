import java.util.*;

public class BankersAlgorithm {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes and resources
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter number of resources: ");
        int m = sc.nextInt();

        int[][] max = new int[n][m];
        int[][] allocation = new int[n][m];
        int[][] need = new int[n][m];
        int[] available = new int[m];

        // Input Allocation Matrix
        System.out.println("Enter Allocation Matrix:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                allocation[i][j] = sc.nextInt();

        // Input Maximum Matrix
        System.out.println("Enter Maximum Matrix:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                max[i][j] = sc.nextInt();

        // Input Available Resources
        System.out.println("Enter Available Resources:");
        for (int j = 0; j < m; j++)
            available[j] = sc.nextInt();

        // Calculate Need Matrix
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                need[i][j] = max[i][j] - allocation[i][j];

        // Initialize finish array and safe sequence
        boolean[] finish = new boolean[n];
        int[] safeSeq = new int[n];
        int count = 0;

        while (count < n) {
            boolean found = false;
            for (int p = 0; p < n; p++) {
                if (!finish[p]) {
                    int j;
                    for (j = 0; j < m; j++) {
                        if (need[p][j] > available[j])
                            break;
                    }
                    if (j == m) { // All resources needed are available
                        for (int k = 0; k < m; k++)
                            available[k] += allocation[p][k];
                        safeSeq[count++] = p;
                        finish[p] = true;
                        found = true;
                        System.out.println("Process P" + p + " is executed.");
                    }
                }
            }
            if (!found) {
                break; // No process could be executed
            }
        }

        // Check if safe sequence exists
        boolean safe = true;
        for (boolean f : finish) {
            if (!f) {
                safe = false;
                break;
            }
        }

        if (safe) {
            System.out.print("\nSystem is in a safe state.\nSafe Sequence: ");
            for (int i = 0; i < n; i++)
                System.out.print("P" + safeSeq[i] + (i < n - 1 ? " -> " : ""));
        } else {
            System.out.println("\nSystem is NOT in a safe state. Deadlock may occur.");
        }

        sc.close();
    }
}

