import java.util.*;

class Process {
    int pid, at, bt, ct, tat, wt;
}

public class SJF_NonPreemptive {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        Process[] p = new Process[n];
        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].pid = i + 1;

            System.out.print("Enter Arrival Time for P" + (i + 1) + ": ");
            p[i].at = sc.nextInt();

            System.out.print("Enter Burst Time for P" + (i + 1) + ": ");
            p[i].bt = sc.nextInt();

            System.out.println();
        }

        // Sort by Arrival Time initially
        Arrays.sort(p, Comparator.comparingInt(x -> x.at));

        int time = 0, completed = 0;
        boolean[] isDone = new boolean[n];

        while (completed < n) {
            int idx = -1;
            int minBT = Integer.MAX_VALUE;

            // Select process with minimum burst time among arrived processes
            for (int i = 0; i < n; i++) {
                if (!isDone[i] && p[i].at <= time && p[i].bt < minBT) {
                    minBT = p[i].bt;
                    idx = i;
                }
            }

            // If no process has arrived yet, increase time
            if (idx == -1) {
                time++;
                continue;
            }

            // Compute Completion Time
            time += p[idx].bt;
            p[idx].ct = time;

            // Calculate TAT and WT
            p[idx].tat = p[idx].ct - p[idx].at;
            p[idx].wt = p[idx].tat - p[idx].bt;

            isDone[idx] = true;
            completed++;
        }

        // Display results
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process pr : p) {
            System.out.println(pr.pid + "\t" + pr.at + "\t" + pr.bt + "\t" +
                    pr.ct + "\t" + pr.tat + "\t" + pr.wt);
        }
        sc.close();
    }
}
