import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.*;

public class process3 extends container3 { 
   public process3() {} 
   public static void main(String args[]) { 
	   List<Student> list = null;
	   String insert="";
	   int x=0;
	   int y=0;
      try { 
         // Instantiating the implementation class
    	 //connecting to mysql database
    	  Class.forName("com.mysql.jdbc.Driver");
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi3", "newuser", "password");

          Statement stmt = conn.createStatement();
         
         //creating an object of ImplExample type
         container3 obj = new container3(); 
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub)
         hello stub3 = (hello) UnicastRemoteObject.exportObject(obj, 0); 
//         Hello stub = new ImplExample();
//       
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello3", stub3);  
         System.out.println("Server ready");
         
         //creating skeletons for itself, others
         hello stub_self3 = (hello) registry.lookup("Hello3"); //skeleton
         Thread.sleep(3000);
         hello stub_s1 = (hello) registry.lookup("Hello"); //skeleton
         hello stub_s2 = (hello) registry.lookup("Hello2");
         hello stub_s4 = (hello) registry.lookup("Hello4");

         System.out.println("lookup server");

//         System.out.println(stub2.notify());
//         while(true) {
//             if(stub2.getstatus() ==1)
//        	 System.out.println(stub2.getMessage());
//
//         }
         int t =0;
         int t1=0;
//         stub_self.addStudent(t);
         Thread.sleep(200);
//-------------------------------------------------------------------------------------------------------------
         while(true) {
             if(x<10) {
            	 try {
         	          FileWriter logwtr = new FileWriter("Server1.log",true);
         	          BufferedWriter bw = new BufferedWriter(logwtr);
         	          PrintWriter pw = new PrintWriter(bw);
         	          System.out.println("LOGGING");

         	          pw.println(x);

//         	          logwtr.append();
         	             pw.flush();
         	          logwtr.close();
         	          
//         	          System.out.println("Successfully wrote to the file.");
         	        } catch (IOException e) {
         	          System.out.println("An error occurred.");
         	          e.printStackTrace();
         	        }
            	 x++;	 
             
             while(t<10 || (stub_self3.dbstatus(0)!=0 || stub_self3.dbstatus(1)!=0 || stub_self3.dbstatus(2)!=0 || stub_self3.dbstatus(3)!=0)) {
            	 if(t<10) {
        	 if(stub_self3.dbstatus(2) == 0 && stub_s2.dbstatus(2) == 0 && stub_s1.dbstatus(2) == 0 && stub_s4.dbstatus(2) == 0 ) {

        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p3 request insert a new statement STARTS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 stub_self3.request_write(t,3);
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p3 request insert a new statement ENDS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 
        		 
        		 
        		 
        		 stub_s2.set_dbstatus(2,1);
        		 stub_s1.set_dbstatus(2,1);
        		 stub_s4.set_dbstatus(2,1);
        		 t++;
        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P3: requested write");

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
        		
        	 }
        	 }
        	 
           	 if(stub_self3.dbstatus(3) == 1) {
        		 String insert1 = stub_s4.insert_container();
        		 stub_self3.request_write_others(insert1);
        		
           	      stub_self3.notify(3);
         	      System.out.println("Requested Replication of Server 4 to Server 3");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P3: updated for p4");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
         	  }
      	 
        	 
        	 if(stub_self3.dbstatus(1) == 1) {
        		 String insert1 = stub_s2.insert_container();
        		 stub_self3.request_write_others(insert1);
        		
           	      stub_self3.notify(1);
         	      System.out.println("Requested Replication of Server 2 to Server 3");
//-------------------------------------------------------------------------------------          		 
         	      try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P3: requested write for p2");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
         	  }
       	 Thread.sleep(400);
       	 if(stub_self3.dbstatus(0) == 1) {
    		 String insert1 = stub_s1.insert_container();
    		 stub_self3.request_write_others(insert1);
    		
       	      stub_self3.notify(0);
          	      System.out.println("Requested Replication of Server 1 to Server 3");
//-------------------------------------------------------------------------------------------
          	      try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P3: requested write for p1");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
          	  }
       	 
       	 
       	 
             }
       	 
       	
       	 Random rand = new Random();
         int upper = 10;
         x = rand.nextInt(upper);
       	 
         if(y<50) {
             y++;	 
       	 
       	 
       	if(stub_self3.dbstatus1(2) == 0 && stub_s2.dbstatus1(2) == 0 && stub_s1.dbstatus1(2) == 0 && stub_s4.dbstatus1(2) == 0 ) {

   		 try {
     	          FileWriter logwtr = new FileWriter("Server1.log",true);
     	          BufferedWriter bw = new BufferedWriter(logwtr);
     	          PrintWriter pw = new PrintWriter(bw);
     	          System.out.println("LOGGING");

     	          pw.println("p3 request for updating a new statement STARTS for sno: "+ Integer.toString(x));

//     	          logwtr.append();
     	             pw.flush();
     	          logwtr.close();
     	          
//     	          System.out.println("Successfully wrote to the file.");
     	        } catch (IOException e) {
     	          System.out.println("An error occurred.");
     	          e.printStackTrace();
     	        }
   		 stub_self3.request_update(t1,x);
   		 
   		 try {
     	          FileWriter logwtr = new FileWriter("Server1.log",true);
     	          BufferedWriter bw = new BufferedWriter(logwtr);
     	          PrintWriter pw = new PrintWriter(bw);
     	          System.out.println("LOGGING");

     	          pw.println("p3 reuest for updating a new statement ENDS for sno: "+Integer.toString(x));

//     	          logwtr.append();
     	             pw.flush();
     	          logwtr.close();
     	          
//     	          System.out.println("Successfully wrote to the file.");
     	        } catch (IOException e) {
     	          System.out.println("An error occurred.");
     	          e.printStackTrace();
     	        }
   		 
   		 
   		 
System.out.println("sleep is imp");
   		 
   		 stub_s2.set_dbstatus1(2,1);
   		 stub_s1.set_dbstatus1(2,1);
   		 stub_s4.set_dbstatus1(2,1);
   		 t1++;
   		 try {
  	          FileWriter logwtr = new FileWriter("Server1.log",true);
  	          BufferedWriter bw = new BufferedWriter(logwtr);
  	          PrintWriter pw = new PrintWriter(bw);
  	          System.out.println("LOGGING");

  	          pw.println("P3: updated(request) rmi");

//  	          logwtr.append();
  	             pw.flush();
  	          logwtr.close();
  	          
//  	          System.out.println("Successfully wrote to the file.");
  	        } catch (IOException e) {
  	          System.out.println("An error occurred.");
  	          e.printStackTrace();
  	        }
   		
       	}
   	 }
   	 
      	 if(stub_self3.dbstatus1(3) == 1) {
   		 String insert1 = stub_s4.insert_container1();
   		stub_self3.request_update_others(insert1);
   		
      	      stub_self3.notify1(3);
    	      System.out.println("requested replication of update of Server 4 to Server 3");
     		 try {
     	          FileWriter logwtr = new FileWriter("Server1.log",true);
     	          BufferedWriter bw = new BufferedWriter(logwtr);
     	          PrintWriter pw = new PrintWriter(bw);
     	          System.out.println("LOGGING");

     	          pw.println("P3: updated(request) for p4");

//     	          logwtr.append();
     	             pw.flush();
     	          logwtr.close();
     	          
//     	          System.out.println("Successfully wrote to the file.");
     	        } catch (IOException e) {
     	          System.out.println("An error occurred.");
     	          e.printStackTrace();
     	        }
    	  }
 	 
   	 
   	 if(stub_self3.dbstatus1(1) == 1) {
   		 String insert1 = stub_s2.insert_container1();
   		stub_self3.request_update_others(insert1);
   		
      	      stub_self3.notify1(1);
    	      System.out.println("requested Replication of update of Server 2 to Server 3");
//-------------------------------------------------------------------------------------          		 
    	      try {
     	          FileWriter logwtr = new FileWriter("Server1.log",true);
     	          BufferedWriter bw = new BufferedWriter(logwtr);
     	          PrintWriter pw = new PrintWriter(bw);
     	          System.out.println("LOGGING");

     	          pw.println("P3: updated(request) for p2");

//     	          logwtr.append();
     	             pw.flush();
     	          logwtr.close();
     	          
//     	          System.out.println("Successfully wrote to the file.");
     	        } catch (IOException e) {
     	          System.out.println("An error occurred.");
     	          e.printStackTrace();
     	        }
    	  }
  	 
  	 if(stub_self3.dbstatus1(0) == 1) {
		 String insert1 = stub_s1.insert_container1();
		 stub_self3.request_update_others(insert1);
		
  	      stub_self3.notify1(0);
     	      System.out.println("requested Replication of update of Server 1 to Server 3");
//-------------------------------------------------------------------------------------------
     	      try {
     	          FileWriter logwtr = new FileWriter("Server1.log",true);
     	          BufferedWriter bw = new BufferedWriter(logwtr);
     	          PrintWriter pw = new PrintWriter(bw);
     	          System.out.println("LOGGING");

     	          pw.println("P3: updated(reuest) for p1");

//     	          logwtr.append();
     	             pw.flush();
     	          logwtr.close();
     	          
//     	          System.out.println("Successfully wrote to the file.");
     	        } catch (IOException e) {
     	          System.out.println("An error occurred.");
     	          e.printStackTrace();
     	        }
     	  }
  	 

	 
 if(stub_self3.dbstatus1(0) == 0 && stub_self3.dbstatus1(1) == 0 && stub_self3.dbstatus1(2) == 0 && stub_self3.dbstatus1(3) == 0) {
		 
	 
	      System.out.println("Reading from process 3");
	      Random rand1 = new Random();
          int upperno = 10;
          int d = rand1.nextInt(upperno);
          Student student = new Student();
          list = new ArrayList<Student>(); 
   	   student = stub_self3.getStudents(d);  
      	      
   	   try {
   	          FileWriter logwtr = new FileWriter("Server1.log",true);
   	          BufferedWriter bw = new BufferedWriter(logwtr);
   	          PrintWriter pw = new PrintWriter(bw);
   	          System.out.println("LOGGING");
   	       if(student.getId()!=-1)
   	          pw.println("P3: read "+ student.getId() + "with percent: "+ student.getPercent());

//   	          logwtr.append();
   	             pw.flush();
   	          logwtr.close();
   	          
//   	          System.out.println("Successfully wrote to the file.");
   	        } catch (IOException e) {
   	          System.out.println("An error occurred.");
   	          e.printStackTrace();
   	        }
	      
	      try {
	          FileWriter logwtr = new FileWriter("Server1.log",true);
	          BufferedWriter bw = new BufferedWriter(logwtr);
	          PrintWriter pw = new PrintWriter(bw);
	          System.out.println("LOGGING");

	          pw.println(list);
	        pw.println("\n");

//	          logwtr.append();
	             pw.flush();
	          logwtr.close();
	          
//	          System.out.println("Successfully wrote to the file.");
	        } catch (IOException e) {
	          System.out.println("An error occurred.");
	          e.printStackTrace();
	        }
       	 
 		} 
             
             } 
             
             
             
             
             
             
//-------------------------------------sync-------------------------------------------------------             
             
             
             
  	Queue<String> q = stub_self3.queue();
//	 syncDB3 synch = new syncDB3(q);
//	 Thread thrd_sync = new Thread(synch);
//	 Thread.sleep(2000);
//	 thrd_sync.start();
//	 try {
//	          FileWriter logwtr = new FileWriter("Server1.log",true);
//	          BufferedWriter bw = new BufferedWriter(logwtr);
//	          PrintWriter pw = new PrintWriter(bw);
//	          System.out.println("LOGGING");
//
//	          pw.println("p3: " + thrd_sync.getId());
//
////	          logwtr.append();
//	             pw.flush();
//	          logwtr.close();
//	          
////	          System.out.println("Successfully wrote to the file.");
//	        } catch (IOException e) {
//	          System.out.println("An error occurred.");
//	          e.printStackTrace();
//	        }
//	 try {
//         FileWriter logwtr = new FileWriter("Server1.log",true);
//         BufferedWriter bw = new BufferedWriter(logwtr);
//         PrintWriter pw = new PrintWriter(bw);
//         System.out.println("LOGGING");
//
//         pw.println("p3 : " + thrd_sync.getId());
//
////         logwtr.append();
//            pw.flush();
//         logwtr.close();
//         
////         System.out.println("Successfully wrote to the file.");
//       } catch (IOException e) {
//         System.out.println("An error occurred.");
//         e.printStackTrace();
//       }
//	 stub_self3.clearqueue();
	 
//        	 System.out.println("We didnt update");
         
  	
  	
  	if(x==9||y>42) {
  	while(q.peek()!=null) {
		String insert1 = q.peek();
		q.remove();
		
		
		String DB_URL = "jdbc:mysql://localhost:3306/rmi3";  
	      
		 
	      
	      Connection conn1 = null; 
	      Statement stmt1 = null;  

	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      conn1 = DriverManager.getConnection(DB_URL, "newuser", "password"); 
	      System.out.println("Connected database successfully...");  
	      stmt1 = conn1.createStatement();
	      //ResultSet rs = stmt.executeQuery(sql);  
	      stmt1.executeUpdate(insert1);
	      conn1.close();
	      try {
   	          FileWriter logwtr = new FileWriter("Server1.log",true);
   	          BufferedWriter bw = new BufferedWriter(logwtr);
   	          PrintWriter pw = new PrintWriter(bw);
   	          System.out.println("LOGGING");

   	          pw.println("P3: wrote once");
   	       pw.println(insert1);
   	    pw.println("P3: wrote once");
   	          
//   	          logwtr.append();
   	             pw.flush();
   	          logwtr.close();
   	          
//   	          System.out.println("Successfully wrote to the file.");
   	        } catch (IOException e) {
   	          System.out.println("An error occurred.");
   	          e.printStackTrace();
   	        }

	      
	}
	 try {
	          FileWriter logwtr = new FileWriter("Server1.log",true);
	          BufferedWriter bw = new BufferedWriter(logwtr);
	          PrintWriter pw = new PrintWriter(bw);
	          System.out.println("LOGGING");

	          pw.println("P3: finished a set");

//	          logwtr.append();
	             pw.flush();
	          logwtr.close();
	          
//	          System.out.println("Successfully wrote to the file.");
	        } catch (IOException e) {
	          System.out.println("An error occurred.");
	          e.printStackTrace();
	        }

	
	 		x=0;
	 		stub_self3.clearqueue();
	
	
	
         }
  	
  	
  	
  	
         }
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}



//class syncDB3 implements Runnable
//{
//
//	Queue<String> q = new LinkedList<>();
//	int tempstatus = 0;
//	
//	
//
//	
//	syncDB3(Queue<String> queue){
//		this.q = queue;
//	}
//	
//	
//	
//	
//	public void run() {
//		System.out.println("Thread for sync start");
//		try {
//			
//			
//			
//		
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//}

