
package group.project.cpis222;

import java.util.ArrayList;


public class SJFprocess extends Process {
    
    //constructor calls the parent constructor in the class Process.
    public SJFprocess(String Pname, int arrivalTime, int burstTime) {
        super(Pname, arrivalTime, burstTime);
    }
    
    //this method calculates the TAT, WAT, average WAT of a non preepmtive sjf process and lastly display the gantt chart.
        public void calculatePreemptive(ArrayList<Process> x , boolean showOut ){
            int currentTime = 0;
            double avgW = 0.0;
            double avgP = 0.0;
            int n = x.size();
            int completed = 0;
            int totalBurst = 0;
            double cpuUtilization = 0.0;
            double throughput = 0.0;
            // boolean array used to know if the current process is finished or not.
            boolean[] isFinished = new boolean[x.size()];            
            // an array list that saves the correct and finished order, used to create the grantt chart.
            ArrayList<Process> finishedOrder = new ArrayList<>();
            //an array list to keep track of the time in the chart when preemption occurs.
            ArrayList<Integer> chartTimes = new ArrayList<>();
            //we initialize chartTimes at zero since the chart always starts at zero.
            chartTimes.add(0);
            //a process object used to record the last process worked on.
            Process lastProcess = null;
            //core logic for the calculate method, simulates a CPU clock.
            while (completed < n) {
                //variable that store the index of the shortest job.
                int minIndex = -1;
                //in preemptive SJF we care about the amount or a process has left to do 
                int minRemaining = Integer.MAX_VALUE;
                
            //step 1 is searching for a process that has arrived and is not yet finnished.
                for (int i = 0; i < n; i++) { 
                    Process p = x.get(i);
                    if (p.arrivalTime <= currentTime && !isFinished[i]) {
                   //step 2 check for the process with the smallest remaining time.
                    if (p.remainingTime < minRemaining) {
                            minRemaining = p.remainingTime;
                            minIndex = i;
                    } 
                    //this statement is a tie breaker which is used to see that if the current process in the loops remaining time is = to minRemaining,
                    //we then compare the arrival time of the current process and the process stored in minIndex.
                    else if(p.remainingTime == minRemaining){
                        if(p.arrivalTime < x.get(minIndex).arrivalTime){
                            minIndex = i;
                        }
                    }
                    }
                }
                //an if condition to check if minIndex was updated after the loop.
                //if minIndex is equal to -1 then there is no process to currently work on meaning the CPU is idle.
                if(minIndex == -1){
                    //since CPU is idle we advance the time in the CPU.
                    currentTime++;
                    lastProcess = null;
                }
                else{
                    //gets the process that has the shortest remaining time.
                    Process current = x.get(minIndex);
                    //checking if preemption occured.
                    if(current != lastProcess){
                        //if process is different then we record to track preemption in the Gantt chart.
                        finishedOrder.add(current);
                        if(currentTime != 0){
                            chartTimes.add(currentTime);
                        }
                    }
                    //decrement remainging time by 1.
                    current.remainingTime--;
                    //incrementing the current time to simulate time passing in the CPU.
                    currentTime++;
                    //recording the last process worked on.
                    lastProcess = current;
                    //if remaining time is 0 we can finally calculate the tat, wat, and average wat. 
                if (current.remainingTime == 0) {
                    current.completionTime = currentTime;
                    current.turnaroundTime = current.completionTime - current.arrivalTime;
                    current.waitingTime = current.turnaroundTime - current.burstTime;
        
                    avgW += current.waitingTime;
                    avgP += current.turnaroundTime;
                    isFinished[minIndex] = true;
                   
                    completed++;
                }
                }
                }
                    //finalizing the chart timeline.
                    chartTimes.add(currentTime);
                    //calculating the average WT.
                    avgWaitingTime =avgW / x.size();
                    //calculating the average TAT.
                    avgTurningAroundTime =avgP / x.size();
                    
            //display block where the Gantt chart is printed and the results from the calculation are displayed.
            if(showOut){                
                    String topBar = "|";
                    String bottomBar = "";
                    System.out.println("--------Gantt Chart-(SJF)--------");
                //constructing the top bar of the chart.
                for (int i = 0; i < finishedOrder.size(); i++) {
                      Process p = finishedOrder.get(i);
                      topBar += "\t" + p.PName + "\t|";               
                    }
                //constructing the bottom part of the chart.
                for (int i = 0; i < chartTimes.size(); i++) {
                      int time = chartTimes.get(i);
                      bottomBar += time + "\t\t";
                    }
                //displaying the chart.
                System.out.println(topBar);
                System.out.println(bottomBar);
                //displaying the calculation results.
                    System.out.println("--------calculation results--------");
                for (int i = 0; i < x.size(); i++) {
                         Process p = x.get(i);
                         totalBurst += p.burstTime; 
                         System.out.println(p.PName + ": TAT = " + p.turnaroundTime + ", WT = " + p.waitingTime);            
                        }
                        cpuUtilization = ((double) totalBurst / currentTime) * 100;
                        throughput = (double) n / currentTime;
                        System.out.println( "Average Waiting Time: " + Math.round(avgWaitingTime * 100.0) / 100.0);
                        System.out.println( "Average Turnaround Time: " + Math.round(avgTurningAroundTime * 100.0) / 100.0);
                        System.out.println( "CPU Utilization: " + Math.round(cpuUtilization * 100.0) / 100.0 + "%");
                        System.out.println( "Throughput: " + Math.round(throughput * 100.0) / 100.0 + "processes/sec");      
            }
            
        }          
        }
        
        
    
    
    

