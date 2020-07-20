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

public class process1 extends container1 { 
   public process1() {} 
   public static void main(String args[]) { 
	   List<Student> list = null;
	   String insert="";
	   int x=0;
	   int y=0;
      try { 
         // Instantiating the implementation class
    	 //connecting to mysql database
    	  Class.forName("com.mysql.jdbc.Driver");
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi", "newuser", "password");

          Statement stmt = conn.createStatement();
         
         //creating an object of ImplExample type
         container1 obj = new container1(); 
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub)
         hello stub1 = (hello) UnicastRemoteObject.exportObject(obj, 0); 
//         Hello stub = new ImplExample();
//       
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello", stub1);  
         System.out.println("Server ready");
         
         //creating skeletons for itself, others
         hello stub_self1 = (hello) registry.lookup("Hello"); //skeleton
         Thread.sleep(3000);
         hello stub_s2 = (hello) registry.lookup("Hello2"); //skeleton
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
//--------------------------------------------------------------------------------------------------------------
         while(true) {
             Thread.sleep(200);
             while(t<10 || (stub_self1.dbstatus(0)!=0 || stub_self1.dbstatus(1)!=0 || stub_self1.dbstatus(2)!=0 || stub_self1.dbstatus(3)!=0)) {
            	 if(t<10) {
        	 if(stub_self1.dbstatus(0) == 0 && stub_s2.dbstatus(0) == 0 && stub_s3.dbstatus(0) == 0 && stub_s4.dbstatus(0) == 0 ) {
        		 
        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("p1 insert a new statement STARTS");

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
       	 
        		 
        		 
        		 
        		 stub_self1.addStudent(t,1);
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p1 insert a new statement ENDS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 
        		 stub_s2.set_dbstatus(0,1);
        		 stub_s3.set_dbstatus(0,1);
        		 stub_s4.set_dbstatus(0,1);
        		 t++;
        		 
        		 try {
        	          FileWriter logwtr = new FileWriter("Server1.log",true);
        	          BufferedWriter bw = new BufferedWriter(logwtr);
        	          PrintWriter pw = new PrintWriter(bw);
        	          System.out.println("LOGGING");

        	          pw.println("P1: updated rmi");

//        	          logwtr.append();
        	             pw.flush();
        	          logwtr.close();
        	          
//        	          System.out.println("Successfully wrote to the file.");
        	        } catch (IOException e) {
        	          System.out.println("An error occurred.");
        	          e.printStackTrace();
        	        }
        	 }
        	 
            	 } 
        	 if(stub_self1.dbstatus(1) == 1) {
        		 String insert1 = stub_s2.insert_container();
        		 int count=stmt.executeUpdate(insert1);
          	      stub_self1.notify(1);
          	      System.out.println("Replicated Server 2 to Server 1");
          	      
         		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P1: updated for p2");

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
          	  }
        	 
        	 if(stub_self1.dbstatus(2) == 1) {
        		 String insert1 = stub_s3.insert_container();
        		 int count=stmt.executeUpdate(insert1);
        		
           	      stub_self1.notify(2);
           	      System.out.println("Replicated Server 3 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: updated for p3");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
           	  }
        	 
        	 if(stub_self1.dbstatus(3) == 1) {
           		 String insert1 = stub_s4.insert_container();
        		 int count=stmt.executeUpdate(insert1);
           	      stub_self1.notify(3);
           	      System.out.println("Replicated Server 4 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: updated for p4");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
           	  }
//        	 System.out.println("We didnt update");
         }
           	 
             
             
             
             Random rand = new Random();
             int upper = 10;
             x = rand.nextInt(upper);
             
             
     
             if(y<50) {
            y++;
             
             
        	 if(stub_self1.dbstatus1(0) == 0 && stub_s2.dbstatus1(0) == 0 && stub_s3.dbstatus1(0) == 0 && stub_s4.dbstatus1(0) == 0 ) {
        		 
        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("p1 insert a new statement STARTS"+Integer.toString(x));

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
       	 
        		 
        		 
        		 
        		 stub_self1.updateStudent(t1,x);
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p1 update a new statement ENDS"+Integer.toString(x));

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 
        		 stub_s2.set_dbstatus1(0,1);
        		 stub_s3.set_dbstatus1(0,1);
        		 stub_s4.set_dbstatus1(0,1);
        		 t1++;
        		 
        		 try {
        	          FileWriter logwtr = new FileWriter("Server1.log",true);
        	          BufferedWriter bw = new BufferedWriter(logwtr);
        	          PrintWriter pw = new PrintWriter(bw);
        	          System.out.println("LOGGING");

        	          pw.println("P1: updated(update) rmi");

//        	          logwtr.append();
        	             pw.flush();
        	          logwtr.close();
        	          
//        	          System.out.println("Successfully wrote to the file.");
        	        } catch (IOException e) {
        	          System.out.println("An error occurred.");
        	          e.printStackTrace();
        	        }
        	 }
             }
        	 
        	 if(stub_self1.dbstatus1(1) == 1) {
        		 String insert1 = stub_s2.insert_container1();
        		 int count=stmt.executeUpdate(insert1);
          	      stub_self1.notify1(1);
          	      System.out.println("Replicated update of Server 2 to Server 1");
          	      
         		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P1: updated(update) for p2");

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
          	  }
        	 
        	 if(stub_self1.dbstatus1(2) == 1) {
        		 String insert1 = stub_s3.insert_container1();
        		 int count=stmt.executeUpdate(insert1);
        		
           	      stub_self1.notify1(2);
           	      System.out.println("Replicated update of Server 3 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: updated(update) for p3");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
           	  }
        	 
        	 if(stub_self1.dbstatus1(3) == 1) {
           		 String insert1 = stub_s4.insert_container1();
        		 int count=stmt.executeUpdate(insert1);
           	      stub_self1.notify1(3);
           	      System.out.println("Replicated update of Server 4 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: updated(update) for p4");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
           	  }
        	 if(stub_self1.dbstatus1(0) == 0 && stub_self1.dbstatus1(1) == 0 && stub_self1.dbstatus1(2) == 0 && stub_self1.dbstatus1(3) == 0) {

           	      System.out.println("Reading from process 1");
           	      
           	      
           	      
           	      list = new ArrayList<Student>(); 
           	      list = stub_self1.getStudents();
           	      
           	      
           	      
           	      
           	      
           	      
           	      
           	      
           	      
           	      
           	      
          		 try {
          	          FileWriter logwtr = new FileWriter("process1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println(list.get(0).getName());

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
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}