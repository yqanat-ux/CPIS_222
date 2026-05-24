
package group.project.cpis222;

import java.util.ArrayList;

public class PriorityProcess  extends Process {
    public PriorityProcess(String Pname, int arrivalTime, int burstTime, int priority) {
        super(Pname, arrivalTime, burstTime,priority);
    }

    public void calculatePriorityPreemptive(ArrayList<Process> x , boolean showOut) {
        int currentTime = 0;
        double avgW = 0.0;
        double avgP = 0.0;
        int n = x.size();
        int completed = 0;
        int totalBurst = 0;
        
        boolean[] isFinished = new boolean[n];
        ArrayList<Process> finishedOrder = new ArrayList<>();

        while (completed < n) {
            int minIndex = -1;
            int minPriorityValue = Integer.MAX_VALUE;

            // Search for the process with the highest priority (lowest number) that has arrived
            for (int i = 0; i < n; i++) {
                Process p = x.get(i);
                if (p.arrivalTime <= currentTime && !isFinished[i]) {
                    if (p.priority < minPriorityValue) {
                        minPriorityValue = p.priority;
                        minIndex = i;
                    }
                    // Tie-breaker: If priorities are equal, pick the one that arrived first
                    else if (p.priority == minPriorityValue) {
                        if (p.arrivalTime < x.get(minIndex).arrivalTime) {
                            minIndex = i;
                        }
                    }
                }
            }

            if (minIndex == -1) {
                currentTime++;
            } else {
                Process current = x.get(minIndex);
                current.remainingTime--;
                currentTime++;

                if (current.remainingTime == 0) {
                    current.completionTime = currentTime;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;

                    avgW += current.waitingTime;
                    avgP += current.turnaroundTime;
                    isFinished[minIndex] = true;
                    finishedOrder.add(current);
                    completed++;
                }
            }
        }
        avgWaitingTime = avgW / n;
        avgTurningAroundTime = avgP / n;
        if(showOut){
        // --- Output Results ---
        System.out.println("\n-------- Gantt Chart (Priority Preemptive) --------");
        String topBar = "|";
        String bottomBar = "0";
        for (Process p : finishedOrder) {
            topBar += "\t" + p.PName + "\t|";
            bottomBar += "\t\t" + p.completionTime;
        }
        System.out.println(topBar);
        System.out.println(bottomBar);

        System.out.println("\n-------- Calculation Results --------");
        for (Process p : x) {
            totalBurst += p.burstTime;
            System.out.println(p.PName + ": TAT = " + p.turnaroundTime + ", WT = " + p.waitingTime);
        }

        double cpuUtilization = ((double) totalBurst / currentTime) * 100;
        double throughput = (double) n / currentTime;

        System.out.println("Average Waiting Time: " + Math.round((avgWaitingTime) * 100.0) / 100.0);
        System.out.println("Average Turnaround Time: " + Math.round((avgTurningAroundTime) * 100.0) / 100.0);
        System.out.println("CPU Utilization: " + Math.round(cpuUtilization * 100.0) / 100.0 + "%");
        System.out.println("Throughput: " + Math.round(throughput * 100.0) / 100.0 + " processes/sec");
        }
    }
}
