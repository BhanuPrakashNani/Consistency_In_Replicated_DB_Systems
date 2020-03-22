import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.*;

public class process2 extends container2 { 
   public process2() {} 
   public static void main(String args[]) { 
	   List<Student> list = null;
	   String insert="";
	   int x=0;
	   int y=0;
      try { 
         // Instantiating the implementation class
    	 //connecting to mysql database
    	  Class.forName("com.mysql.jdbc.Driver");
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi2", "newuser", "password");

          Statement stmt = conn.createStatement();
         
         //creating an object of ImplExample type
         container2 obj = new container2(); 
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub)
         hello stub2 = (hello) UnicastRemoteObject.exportObject(obj, 0); 
//         Hello stub = new ImplExample();
//       
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello2", stub2);  
         System.out.println("Server ready");
         
         //creating skeletons for itself, others
         hello stub_self2 = (hello) registry.lookup("Hello2"); //skeleton
         Thread.sleep(3000);
         hello stub_s1 = (hello) registry.lookup("Hello"); //skeleton
         hello stub_s3 = (hello) registry.lookup("Hello3");
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
//----------------------------------------------------------------------------------------------------------
         while(true) {
             Thread.sleep(300);
             while(t<10 || (stub_self2.dbstatus(0)!=0 || stub_self2.dbstatus(1)!=0 || stub_self2.dbstatus(2)!=0 || stub_self2.dbstatus(3)!=0)) {
            	 if(t<10) {
             
        	 if(stub_self2.dbstatus(1) == 0 && stub_s1.dbstatus(1) == 0 && stub_s3.dbstatus(1) == 0 && stub_s4.dbstatus(1) == 0 ) {
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p2 insert a new statement STARTS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 stub_self2.addStudent(t,2);
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p2 insert a new statement ENDS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 
        		 stub_s1.set_dbstatus(1,1);
        		 stub_s3.set_dbstatus(1,1);
        		 stub_s4.set_dbstatus(1,1);
        		 t++;

        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P2: updated rmi");

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
        		 
        		 


        	 if(stub_self2.dbstatus(0) == 1) {
        		 String insert1 = stub_s1.insert_container();
        		 int count=stmt.executeUpdate(insert1);
        		
           	      stub_self2.notify(0);
         	      System.out.println("Replicated Server 1 to Server 2");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P2: updated for p1");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
         	  }
       	 
       	 if(stub_self2.dbstatus(2) == 1) {
    		 String insert1 = stub_s3.insert_container();
    		 int count=stmt.executeUpdate(insert1);
    		
       	      stub_self2.notify(2);
          	      System.out.println("Replicated Server 3 to Server 2");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P2: updated for p3");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
          	  }
       	 
       	 if(stub_self2.dbstatus(3) == 1) {
    		 String insert1 = stub_s4.insert_container();
    		 int count=stmt.executeUpdate(insert1);
    		
       	      stub_self2.notify(3);
          	      System.out.println("Replicated Server 4 to Server 2");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P2: updated for p4");

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
         
    	 if(stub_self2.dbstatus1(1) == 0 && stub_s1.dbstatus1(1) == 0 && stub_s3.dbstatus1(1) == 0 && stub_s4.dbstatus1(1) == 0 ) {
    		 
    		 try {
      	          FileWriter logwtr = new FileWriter("Server1.log",true);
      	          BufferedWriter bw = new BufferedWriter(logwtr);
      	          PrintWriter pw = new PrintWriter(bw);
      	          System.out.println("LOGGING");

      	          pw.println("p2 update a new statement STARTS"+Integer.toString(x));

//      	          logwtr.append();
      	             pw.flush();
      	          logwtr.close();
      	          
//      	          System.out.println("Successfully wrote to the file.");
      	        } catch (IOException e) {
      	          System.out.println("An error occurred.");
      	          e.printStackTrace();
      	        }
    		 stub_self2.updateStudent(t1,x);
    		 try {
      	          FileWriter logwtr = new FileWriter("Server1.log",true);
      	          BufferedWriter bw = new BufferedWriter(logwtr);
      	          PrintWriter pw = new PrintWriter(bw);
      	          System.out.println("LOGGING");

      	          pw.println("p2 update a new statement ENDS"+Integer.toString(x));

//      	          logwtr.append();
      	             pw.flush();
      	          logwtr.close();
      	          
//      	          System.out.println("Successfully wrote to the file.");
      	        } catch (IOException e) {
      	          System.out.println("An error occurred.");
      	          e.printStackTrace();
      	        }
    		 
    		 stub_s1.set_dbstatus1(1,1);
    		 stub_s3.set_dbstatus1(1,1);
    		 stub_s4.set_dbstatus1(1,1);
    		 t1++;

    		 try {
   	          FileWriter logwtr = new FileWriter("Server1.log",true);
   	          BufferedWriter bw = new BufferedWriter(logwtr);
   	          PrintWriter pw = new PrintWriter(bw);
   	          System.out.println("LOGGING");

   	          pw.println("P2: updated(update) rmi");

//   	          logwtr.append();
   	             pw.flush();
   	          logwtr.close();
   	          
//   	          System.out.println("Successfully wrote to the file.");
   	        } catch (IOException e) {
   	          System.out.println("An error occurred.");
   	          e.printStackTrace();
   	        }
    	 } 
    	 }
    	 
    		 
    	 


    	 if(stub_self2.dbstatus1(0) == 1) {
    		 String insert1 = stub_s1.insert_container1();
    		 int count=stmt.executeUpdate(insert1);
    		
       	      stub_self2.notify1(0);
     	      System.out.println("Replicated update of Server 1 to Server 2");
      		 try {
      	          FileWriter logwtr = new FileWriter("Server1.log",true);
      	          BufferedWriter bw = new BufferedWriter(logwtr);
      	          PrintWriter pw = new PrintWriter(bw);
      	          System.out.println("LOGGING");

      	          pw.println("P2: updated(update) for p1");

//      	          logwtr.append();
      	             pw.flush();
      	          logwtr.close();
      	          
//      	          System.out.println("Successfully wrote to the file.");
      	        } catch (IOException e) {
      	          System.out.println("An error occurred.");
      	          e.printStackTrace();
      	        }
     	  }
   	 
   	 if(stub_self2.dbstatus1(2) == 1) {
		 String insert1 = stub_s3.insert_container1();
		 int count=stmt.executeUpdate(insert1);
		
   	      stub_self2.notify1(2);
      	      System.out.println("Replicated update of Server 3 to Server 2");
      		 try {
      	          FileWriter logwtr = new FileWriter("Server1.log",true);
      	          BufferedWriter bw = new BufferedWriter(logwtr);
      	          PrintWriter pw = new PrintWriter(bw);
      	          System.out.println("LOGGING");

      	          pw.println("P2: updated(update) for p3");

//      	          logwtr.append();
      	             pw.flush();
      	          logwtr.close();
      	          
//      	          System.out.println("Successfully wrote to the file.");
      	        } catch (IOException e) {
      	          System.out.println("An error occurred.");
      	          e.printStackTrace();
      	        }
      	  }
   	 
   	 if(stub_self2.dbstatus1(3) == 1) {
		 String insert1 = stub_s4.insert_container1();
		 int count=stmt.executeUpdate(insert1);
		
   	      stub_self2.notify1(3);
      	      System.out.println("Replicated update of Server 4 to Server 2");
      		 try {
      	          FileWriter logwtr = new FileWriter("Server1.log",true);
      	          BufferedWriter bw = new BufferedWriter(logwtr);
      	          PrintWriter pw = new PrintWriter(bw);
      	          System.out.println("LOGGING");

      	          pw.println("P2: updated(update) for p4");

//      	          logwtr.append();
      	             pw.flush();
      	          logwtr.close();
      	          
//      	          System.out.println("Successfully wrote to the file.");
      	        } catch (IOException e) {
      	          System.out.println("An error occurred.");
      	          e.printStackTrace();
      	        }
      	  }
    	 
	 if(stub_self2.dbstatus1(0) == 0 && stub_self2.dbstatus1(1) == 0 && stub_self2.dbstatus1(2) == 0 && stub_self2.dbstatus1(3) == 0) {
   		 
		 
   	      System.out.println("Reading from process 2");
   	      list = new ArrayList<Student>(); 
   	      list = stub_self2.getStudents();
   	      
   	      try {
  	          FileWriter logwtr = new FileWriter("process2.log",true);
  	          BufferedWriter bw = new BufferedWriter(logwtr);
  	          PrintWriter pw = new PrintWriter(bw);
  	          System.out.println("LOGGING");

  	          pw.println(list);
  	        pw.println("\n");

//  	          logwtr.append();
  	             pw.flush();
  	          logwtr.close();
  	          
//  	          System.out.println("Successfully wrote to the file.");
  	        } catch (IOException e) {
  	          System.out.println("An error occurred.");
  	          e.printStackTrace();
  	        }
   	  
     
 }
    	 
    	 
    	 
         
        	 
             
        	 
        	 
//        }	 System.out.println("We didnt update");
         }
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}