import java.util.*;

class Process {
    int pid, at, bt, ct, tat, wt, rem;
}

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter Time Quantum: ");
        int tq = sc.nextInt();

        Process[] p = new Process[n];

        for (int i = 0; i < n; i++) {
            p[i] = new Process();
            p[i].pid = i + 1;

            System.out.print("Enter Arrival Time for P" + (i + 1) + ": ");
            p[i].at = sc.nextInt();

            System.out.print("Enter Burst Time for P" + (i + 1) + ": ");
            p[i].bt = sc.nextInt();

            p[i].rem = p[i].bt; // remaining time
            System.out.println();
        }

        // Sort processes by arrival time
        Arrays.sort(p, Comparator.comparingInt(x -> x.at));

        Queue<Integer> q = new LinkedList<>();

        int time = 0, completed = 0;
        boolean[] inQueue = new boolean[n];

        // Add first arriving process
        q.add(0);
        inQueue[0] = true;
        time = p[0].at; // start from earliest arrival time

        while (completed < n) {
            int idx = q.poll();
            inQueue[idx] = false;

            // Execute current process for min(quantum, remaining time)
            int exec = Math.min(tq, p[idx].rem);
            p[idx].rem -= exec;
            time += exec;

            // Add newly arrived processes to queue
            for (int i = 0; i < n; i++) {
                if (!inQueue[i] && p[i].rem > 0 && p[i].at <= time) {
                    q.add(i);
                    inQueue[i] = true;
                }
            }

            // If process not finished, push back to queue
            if (p[idx].rem > 0) {
                q.add(idx);
                inQueue[idx] = true;
            } else {
                // Process finished
                p[idx].ct = time;
                completed++;
            }

            // If queue becomes empty but some processes are left, jump time to next arrival
            if (q.isEmpty() && completed < n) {
                for (int i = 0; i < n; i++) {
                    if (p[i].rem > 0) {
                        q.add(i);
                        inQueue[i] = true;
                        time = Math.max(time, p[i].at);
                        break;
                    }
                }
            }
        }

        // Calculate TAT & WT
        for (Process pr : p) {
            pr.tat = pr.ct - pr.at;
            pr.wt = pr.tat - pr.bt;
        }

        // Output
        System.out.println("\nPID\tAT\tBT\tCT\tTAT\tWT");
        for (Process pr : p) {
            System.out.println(pr.pid + "\t" + pr.at + "\t" + pr.bt + "\t" +
                    pr.ct + "\t" + pr.tat + "\t" + pr.wt);
        }
    }
}
