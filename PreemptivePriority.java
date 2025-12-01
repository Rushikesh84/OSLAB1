import java.util.*;

class Process {
    int pid, arrival, burst, priority;
    int remaining, finish, turn, wait;

    Process(int pid, int arrival, int burst, int priority) {
        this.pid = pid;
        this.arrival = arrival;
        this.burst = burst;
        this.remaining = burst;
        this.priority = priority;
    }
}

public class PreemptivePriority {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter Arrival, Burst, Priority for P" + (i+1) + ": ");
            int at = sc.nextInt();
            int bt = sc.nextInt();
            int pr = sc.nextInt();
            p[i] = new Process(i+1, at, bt, pr);
        }

        int completed = 0, time = 0;

        while (completed != n) {
            int idx = -1;
            int bestPriority = Integer.MAX_VALUE;

            // Find process with highest priority at this time
            for (int i = 0; i < n; i++) {
                if (p[i].arrival <= time && p[i].remaining > 0) {
                    if (p[i].priority < bestPriority) {
                        bestPriority = p[i].priority;
                        idx = i;
                    }
                }
            }

            if (idx != -1) {
                // Execute the highest priority process for 1 unit
                p[idx].remaining--;

                // If finished
                if (p[idx].remaining == 0) {
                    p[idx].finish = time + 1;
                    p[idx].turn = p[idx].finish - p[idx].arrival;
                    p[idx].wait = p[idx].turn - p[idx].burst;
                    completed++;
                }
            }
            time++;
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tPR\tFT\tTAT\tWT");
        for (Process pr : p) {
            System.out.println(pr.pid + "\t" + pr.arrival + "\t" + pr.burst + "\t" +
                    pr.priority + "\t" + pr.finish + "\t" + pr.turn + "\t" + pr.wait);
        }
    }
}

