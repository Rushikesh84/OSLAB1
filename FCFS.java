
import java.util.Scanner;

public class FCFS {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] bt = new int[n]; // Burst Time
        int[] at = new int[n]; // Arrival Time
        int[] ft = new int[n]; // Finish Time
        int[] tat = new int[n]; // Turnaround Time
        int[] wt = new int[n]; // Waiting Time

        System.out.println("\nEnter Arrival Time and Burst Time:");

        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " Arrival Time: ");
            at[i] = sc.nextInt();
            System.out.print("Process " + (i + 1) + " Burst Time: ");
            bt[i] = sc.nextInt();
        }

        // Compute Finish Time
        ft[0] = at[0] + bt[0]; // for first process

        for (int i = 1; i < n; i++) {
            if (at[i] > ft[i - 1]) {
                // CPU is idle, process starts at its AT
                ft[i] = at[i] + bt[i];
            } else {
                // Process waits till CPU becomes free
                ft[i] = ft[i - 1] + bt[i];
            }
        }

        // Compute Turnaround Time and Waiting Time
        for (int i = 0; i < n; i++) {
            tat[i] = ft[i] - at[i];
            wt[i] = tat[i] - bt[i];
        }

        // Display Results
        System.out.println("\nFCFS Scheduling Results:");
        System.out.println("Process\tAT\tBT\tFT\tTAT\tWT");

        for (int i = 0; i < n; i++) {
            System.out.println("P" + (i + 1) + "\t" + at[i] + "\t" + bt[i] + "\t" +
                    ft[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        sc.close();
    }
}