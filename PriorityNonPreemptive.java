import java.util.*;

class Process {
    int pid, at, bt, pri, ct, tat, wt;
}

public class PriorityNonPreemptive {
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

            System.out.print("Enter Priority for P" + (i + 1) + " (lower value = higher priority): ");
            p[i].pri = sc.nextInt();

            System.out.println();
        }

        // Sort initially by arrival time
        Arrays.sort(p, Comparator.comparingInt(x -> x.at));

        int time = 0, completed = 0;
        boolean[] done = new boolean[n];

        while (completed < n) {
            int idx = -1;
            int bestPri = Integer.MAX_VALUE;

            // Select process with highest priority among arrived + not completed
            for (int i = 0; i < n; i++) {
                if (!done[i] && p[i].at <= time && p[i].pri < bestPri) {
                    bestPri = p[i].pri;
                    idx = i;
                }
            }

            // If no process has arrived, move time forward
            if (idx == -1) {
                time++;
                continue;
            }

            // Execute the chosen process (non-preemptive)
            time += p[idx].bt;

            // Completion Time
            p[idx].ct = time;

            // Mark completed
            done[idx] = true;
            completed++;
        }

        // Compute TAT and WT
        for (Process pr : p) {
            pr.tat = pr.ct - pr.at;
            pr.wt = pr.tat - pr.bt;
        }

        // Output Result
        System.out.println("\nPID\tAT\tBT\tPR\tCT\tTAT\tWT");
        for (Process pr : p) {
            System.out.println(pr.pid + "\t" + pr.at + "\t" + pr.bt + "\t" +
                               pr.pri + "\t" + pr.ct + "\t" + pr.tat + "\t" + pr.wt);
        }
    }
}

