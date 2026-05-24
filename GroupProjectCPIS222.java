
package group.project.cpis222;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class GroupProjectCPIS222 {

   
    public static void main(String[] args) {
        //Scanner object used to accept user inputs.
        Scanner userIn = new Scanner(System.in);
        //an instance of the GroupProjectCPIS222 class since there are three local methods used in this class.
        GroupProjectCPIS222 process = new GroupProjectCPIS222();
        
        //two cases for inputs either being manually inputed or randomly generated.
        //case 1 allows the user to select a specific scheduling algorithm.
        //case 2 outputs calculations and a gantt chart of a randomly generated process list.
        //case 3 generates 10 processes and then compares them.
        System.out.println(" How do you want to enter the processes: (1)Manually or (2)Randomly Generated or (3)10 Randomly Generated Processes with comparison");
        int choiceOfIn = userIn.nextInt();
        
       //manual input case.
        if (choiceOfIn == 1) {
            System.out.println("Enter the number of processes: ");
            int numOfP = userIn.nextInt();
            //asking the user to specify which scheduling algorithm to use.
            System.out.println("Which scheduling algorithm would you like to use: FCFS(1) or SJF(2) or Priority(3)");
            int choiceOfSA = userIn.nextInt();
            //creation of a process array list, user input will be added to the list.
            ArrayList<Process> processes = new ArrayList<>();
            
            switch (choiceOfSA) {
                case 1:
                    //a for loop which repeatedly asks the user for the process name, arrival time, and burst time, depending on the number of processes.
                    for (int i = 0; i < numOfP; i++) {
                        System.out.println("Enter the Process Name:");
                        String name = userIn.next();
                        System.out.println("Enter the Process arrival time:");
                        int av = userIn.nextInt();
                        System.out.println("Enter the Process burst time:");
                        int bt = userIn.nextInt();
                         //adding the processes to the process list.
                         processes.add(new Process(name, av, bt));
                     }
                    //creating a instance of the FCFSProcess class and then call the calculate method and pass the process list and a boolean value of true to show the output.
                    FCFSProcess FCFSscheduler = new FCFSProcess("scheduler", 0, 0);
                    FCFSscheduler.calculateFCFS(processes,true);
                    break;
                case 2:
                    //a for loop which repeatedly asks the user for the process name, arrival time, and burst time, depending on the number of processes.
                    for (int i = 0; i < numOfP; i++) {
                        System.out.println("Enter the Process Name:");
                        String name = userIn.next();
                        System.out.println("Enter the Process arrival time:");
                        int av = userIn.nextInt();
                        System.out.println("Enter the Process burst time:");
                        int bt = userIn.nextInt();
                         //adding the processes to the process list.
                         processes.add(new Process(name, av, bt));
                     }
                    //creating a instance of the SJFprocess class and then call the calculate method and pass the process list and a boolean value of true to show the output.
                    SJFprocess SJFscheduler = new SJFprocess("scheduler", 0, 0);
                    SJFscheduler.calculatePreemptive(processes,true);
                    break;
                default:
                    //the default case is the priority scheduling algorithm.
                    //a for loop which repeatedly asks the user for the process name, arrival time,burst time, and priority, depending on the number of processes.
                    for (int i = 0; i < numOfP; i++) {
                        System.out.println("Enter the Process Name:");
                        String name = userIn.next();
                        System.out.println("Enter the Process arrival time:");
                        int av = userIn.nextInt();
                        System.out.println("Enter the Process burst time:");
                        int bt = userIn.nextInt();
                        System.out.println("Enter the Process priority:");
                        int p = userIn.nextInt();
                         //adding the processes to the process list.
                         processes.add(new Process(name, av, bt,p));
                     }
                     //creating a instance of the PriorityProcess class and then call the calculate method and pass the process list and a boolean value of true to show the output.
                    PriorityProcess PriorScheduler = new PriorityProcess("scheduler",0,0,0);
                    PriorScheduler.calculatePriorityPreemptive(processes, true);
                    break;
            }
            
        }
        //second case of random generated processes.
        else if (choiceOfIn == 2) {
            //asks the user for the number of processes
            System.out.println("Enter the number of processes: ");
            int numOfP = userIn.nextInt();
            //creating a random process list, and storing an arraylist of randomly generated process using normal distribution.
            ArrayList<Process> Rprocesses = process.generateRandomProcessesWithPriority(numOfP);
            //we then copy the original list of random processes into a specific array list of processes for each scheduling alogrithm.
            //this is done to ensure that after the random list of processes is processed by the calculate method for a scheduling algorithm,
            //the changes done (specifically attributes like completionTime or remainingTime) will not affect the calcualtion in another scheduling algorithm.
            ArrayList<Process> FCFSList = process.copyProcesses(Rprocesses);
            ArrayList<Process> SJFList = process.copyProcesses(Rprocesses);
            ArrayList<Process> PriorList = process.copyProcesses(Rprocesses);
            //creating a instance of the FCFSProcess class and then call the calculate method and pass the process list and a boolean value of true to show the output.      
            FCFSProcess fcfsScheduler = new FCFSProcess("scheduler",0,0);
            fcfsScheduler.calculateFCFS(FCFSList, true);
            //creating a instance of the SJFprocess class and then call the calculate method and pass the process list and a boolean value of true to show the output.
            SJFprocess sjfScheduler = new SJFprocess("scheduler",0,0);
            sjfScheduler.calculatePreemptive(SJFList, true);
            //creating a instance of the PriorityProcess class and then call the calculate method and pass the process list and a boolean value of true to show the output.
            PriorityProcess priorScheduler = new PriorityProcess("scheduler",0,0,0);
            priorScheduler.calculatePriorityPreemptive(PriorList, true);
            
        }
        //third case being the 10 randomly generated processes with comparison.
        else{
            //creating an instance of this class and then calling the compareAlgorithms method.
            GroupProjectCPIS222 compare = new GroupProjectCPIS222();
            compare.compareAlgorithms();
        }
        
    }
    
    
    
    
   
   //the method that generates random processes using normal distribution.
   //the passed int is the number of processes.
   public ArrayList generateRandomProcessesWithPriority (int n){
       //Random variable called randN.
       Random randN = new Random();
       //A process arraylist used to later store the random processes with their burst times, arrival times, and priority.
       ArrayList<Process> randomList = new ArrayList<>();
       //assigning an average to burst time, the average burstime should be 10.0.
       double meanBurst = 10.0;
       //then assigning a standard deviation of 3 to make sure that almost all values will be between 1 and 19.
       double stdDev = 3.0;
       //a for loop to create the random process's burst times, arrival times, and priorities.
       for (int i = 0; i < n; i++) {
           //using the method nextGaussian generates a value with a mean of 0 and a stdDev of 1, then we multiply it by 3 and add 10.
           //lastly we use math.max to ensure that the number is always positive.
           int burst = (int) Math.max(1, (randN.nextGaussian() * stdDev + meanBurst));
           //we make the arrival time to be between 0 and 19.
           int arrival = randN.nextInt(20);
           //making the priority to be assigned a value from 1 which is the highest priority to 10 which is the lowest.
           int priority = randN.nextInt(10) + 1;
           //lastly create the process and add it to the list.
           randomList.add(new Process("P" + i, arrival, burst, priority));
       }
       return randomList;
   }
   //method creates a copy of the process list passed.
   //this method is used to create copies of a random process list to ensure that when the list is tested using either FCFS, SJF, and priority the  same list is used.
   public ArrayList<Process> copyProcesses(ArrayList<Process> n){
       //Initialize a list called copy which is then used to hold the copied processes.
       ArrayList<Process> copy = new ArrayList<>();
       //a for loop to iterate over the pass process list and create a new process and add it to the copy list.
       for (int i = 0; i < n.size(); i++) {
           Process p = n.get(i);
           //creation of a new process that is initialized with the values of the current process index.
           Process np = new Process(p.PName, p.arrivalTime, p.burstTime, p.priority);
           //resetting remaining time since is would've been decreased and changed from each scheduling alrogrithm.
           np.remainingTime = p.burstTime;
           //lastly add it to the copy list.
           copy.add(np);     
       }
       return copy;
   }
   //this method creates 10 random processes from n = 1 to n = 10, then calls all three scheduling algorithms and compares their WT and TAT.
   public void compareAlgorithms (){
       //header for the table.
       System.out.println("n | FCFS-WT | SJF-WT | PRIO-WT | FCFS-TAT | SJF-TAT | PRIO-TAT");
       System.out.println("---------------------------------------------------------------");
       //for loop that increments by 1 and with each increment a new random process is created,then copied for each scheduling algorithm, and lastly gets passed to each scheduling algorithms calculate method.
       for (int i = 1;  i <= 10; i++) {
           //process array list that hold the original random process in each iteration.
           ArrayList<Process> og = generateRandomProcessesWithPriority(i);
           //making copies of the current iteration random process for each scheduling algorithm.
           ArrayList<Process> fcfsList = copyProcesses(og);
           ArrayList<Process> sjfList = copyProcesses(og);
           ArrayList<Process> priorList = copyProcesses(og);
           //creating a instance of the FCFSProcess class and then call the calculate method and pass the process list and a boolean value of false since we don't need a gantt chart and only need the WT and TAT.
           FCFSProcess fcfsScheduler = new FCFSProcess("scheduler",0,0);
           fcfsScheduler.calculateFCFS(fcfsList, false);
           //creating a instance of the SJFprocess class and then call the calculate method and pass the process list and a boolean value of false since we don't need a gantt chart and only need the WT and TAT.
           SJFprocess sjfScheduler = new SJFprocess("scheduler",0,0);
           sjfScheduler.calculatePreemptive(sjfList, false);   
           //creating a instance of the PriorityProcess class and then call the calculate method and pass the process list and a boolean value of false since we don't need a gantt chart and only need the WT and TAT.
           PriorityProcess priorScheduler = new PriorityProcess("scheduler",0,0,0);
           priorScheduler.calculatePriorityPreemptive(priorList, false);
           //a printf statement to format how each process's WT and TAT should be displayed.
           System.out.printf("%d| %.2f | %.2f | %.2f | %.2f | %.2f | %.2f\n",i,
                   fcfsScheduler.avgWaitingTime,
                   sjfScheduler.avgWaitingTime, 
                   priorScheduler.avgWaitingTime,
                   fcfsScheduler.avgTurningAroundTime,
                   sjfScheduler.avgTurningAroundTime,
                   priorScheduler.avgTurningAroundTime);
       }    
   }
}
