package group.project.cpis222;

import java.util.ArrayList;
//general process glass that defines important attributes
public class Process {
    //necessary process parameters.
    public String PName; //process name.
    public int arrivalTime; //the exact time a process enters the ready queue.
    public int burstTime; //the total CPU execution time required for the process to finnish.
    public int priority; //the priority of a process.
    //attributes that are necessary in the calculation step.
    public int turnaroundTime; //the total time spent in the system.
    public int waitingTime; //the total time time spent waiting for the process to be executed.
    public int completionTime; //the exact time when a process finishes executing.
    public int remainingTime; //tracks the time remaining for a process to finish.
    //the average of both WT and TAT.
    public double avgWaitingTime;
    public double avgTurningAroundTime;
    
    //Process constructor that accepts Pname, arrivalTime, burstTime, and also sets the remaing time of a process to its burst time.
    public Process(String Pname, int arrivalTime, int burstTime){
        this.PName = Pname;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;  
        this.remainingTime = burstTime;
    }
    //Process constructor that also accepts priority.
    public Process(String Pname, int arrivalTime, int burstTime, int priority){
        this.PName = Pname;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;  
        this.remainingTime = burstTime;
        this.priority = priority;
    }
}
    

    
    
