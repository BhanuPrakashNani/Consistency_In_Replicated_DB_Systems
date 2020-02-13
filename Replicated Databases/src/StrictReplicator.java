import java.sql.SQLException;
import java.util.Scanner;

public class StrictReplicator {
	//This replicator follows strict consistency
	public void write()throws InterruptedException, ClassNotFoundException 
    { 
        // synchronized block ensures only one thread 
        // running at a time. 
        synchronized(this) 
        { 
        	try { 
        		int i =0;
        	
        	while(true) {
        		System.out.println("HEy"+DBhandler1.isUpdated);
        		if(DBhandler1.isUpdated == 0)  {
        			Scanner sc = new Scanner(System.in);
//                  System.out.println("Enter sno: \n");
//                  int sno = sc.nextInt();
//                  sc.nextLine();
//                  System.out.println("Enter name: \n");
//                  String name = sc.nextLine();
                  // write mock data instead of input
        		  int sno = 1;
        		  String name = "sample"+i;
        		  i++;
        		  System.out.println("writing sno :"+sno+"  name: "+name);
        		  Threadreplicator.write(sno,name);
                  System.out.println("updated");
                  wait(); 
                  notify();
                  System.out.println("Resumed"); 
                  
        		}
        	}
            // releases the lock on shared resource 

            // and waits till some other method invokes notify(). 
        	}
        	catch(Exception e) {
        		
        	}
        	} 
    } 

    // Sleeps for some time and waits for a key press. After key 
    // is pressed, it notifies produce(). 
    public void replicate()throws InterruptedException, ClassNotFoundException, SQLException 
    { 
        // this makes the produce thread to run first. 
        Thread.sleep(1000); 

        // synchronized block ensures only one thread 
        // running at a time. 
        synchronized(this) 
        { 
        	try {
        		
        		System.out.println(DBhandler1.isUpdated);
        		while(true) {
        		if(DBhandler1.isUpdated > 0) {
        			DBhandler2.isReplicating = 1;
        			System.out.println("Found a update, updating......");
        			Threadreplicator.replicate();
        			System.out.println("UPDATEd "+DBhandler1.isUpdated);
        			DBhandler2.isReplicating = 0;

        			notify(); 
        			System.out.println("reader sleeping");
        			wait();
        			System.out.println("reader wokeup");
        		}
        		else
        		System.out.println("nope");
        		notify(); 

                Thread.sleep(2000); 

        		}

            // notifies the produce thread that it 
            // can wake up. 
            

            // Sleep 
        	}
        	catch(Exception e) {
        		
        	}
        	} 
    } 
} 

