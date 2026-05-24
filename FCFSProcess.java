
package group.project.cpis222;

import java.util.ArrayList;


public class FCFSProcess extends Process{
    // Constructor to pass process data to the parent class
    public FCFSProcess(String Pname, int arrivalTime, int burstTime) {
        super(Pname, arrivalTime, burstTime);
    }

    public void calculateFCFS(ArrayList<Process> processes, boolean showOut) {
        int n = processes.size();

        // 1. Sort processes by arrival time using Bubble Sort
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (processes.get(j).arrivalTime > processes.get(j + 1).arrivalTime) {
                    Process temp = processes.get(j);
                    processes.set(j, processes.get(j + 1));
                    processes.set(j + 1, temp);
                }
            }
        }

        // 2. Setup starting time based on the first process arrival
        int currentTime = processes.get(0).arrivalTime;
        int totalBurst = 0;

        String topBar = "|";
        String bottomBar = "" + currentTime; // Start from first arrival time

        // Main loop for calculations and building the chart
        for (int i = 0; i < n; i++) {
            Process p = processes.get(i);

            // Handle Gaps time between processes 
            if (currentTime < p.arrivalTime) {
                topBar += "  null  |";
                currentTime = p.arrivalTime;
                bottomBar += "\t\t" + currentTime;
            }

            p.completionTime = currentTime + p.burstTime;
            p.turnaroundTime = p.completionTime - p.arrivalTime;
            p.waitingTime = p.turnaroundTime - p.burstTime;

            currentTime = p.completionTime;
            
            topBar += "\t" + p.PName + "\t|";
            bottomBar += "\t\t" + currentTime;
        }
        double avgW = 0;
        double avgP = 0;
        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            totalBurst += p.burstTime;
            avgW += p.waitingTime;
            avgP += p.turnaroundTime;
            
        }
        avgWaitingTime = avgW / n;
        avgTurningAroundTime = avgP / n;
        if(showOut){
        System.out.println("\n-------- Gantt Chart (FCFS) --------");
        System.out.println(topBar);
        System.out.println(bottomBar);

        // 3. Display calculate averages and results
        System.out.println("\n-------- calculation results --------");
        
        for (int i = 0; i < processes.size(); i++) {
            Process p = processes.get(i);
            System.out.println(p.PName + ": TAT = " + p.turnaroundTime + ", WT = " + p.waitingTime);
        }

       

        System.out.println("Average Waiting Time: " + Math.round(avgWaitingTime * 100.0) / 100.0);
        System.out.println("Average Turnaround Time: " + Math.round(avgTurningAroundTime * 100.0) / 100.0);

        // CPU Utilization
        double cpuUtilization = ((double) totalBurst / currentTime) * 100;
        double throughput = (double) n / currentTime;

        System.out.println("CPU Utilization: " + Math.round(cpuUtilization * 100.0) / 100.0 + "%");
        System.out.println("Throughput: " + Math.round(throughput * 100.0) / 100.0 + " processes/sec");
        }
    }
}
