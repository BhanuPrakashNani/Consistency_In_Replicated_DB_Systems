import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.*;
import java.util.Random;
public class process4 extends container4 { 
   public process4() {} 
   public static void main(String args[]) { 
	   List<Student> list = null;
	   String insert="";
	   int x=0;
	   int y=0;
      try { 
         // Instantiating the implementation class
    	 //connecting to mysql database
    	  Class.forName("com.mysql.jdbc.Driver");
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi4", "newuser", "password");

          Statement stmt = conn.createStatement();
         
         //creating an object of ImplExample type
         container4 obj = new container4(); 
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub)
         hello stub4 = (hello) UnicastRemoteObject.exportObject(obj, 0); 
//         Hello stub = new ImplExample();
//       
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello4", stub4);  
         System.out.println("Server ready");
         
         //creating skeletons for itself, others
         hello stub_self4 = (hello) registry.lookup("Hello4"); //skeleton
         Thread.sleep(3000);
         hello stub_s1 = (hello) registry.lookup("Hello"); //skeleton
         hello stub_s3 = (hello) registry.lookup("Hello3");
         hello stub_s2 = (hello) registry.lookup("Hello2");

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
             while(t<10 || (stub_self4.dbstatus(0)!=0 || stub_self4.dbstatus(1)!=0 || stub_self4.dbstatus(2)!=0 || stub_self4.dbstatus(3)!=0)) {
            	 if(t<10) {
        	 if(stub_self4.dbstatus(3) == 0 && stub_s2.dbstatus(3) == 0 && stub_s3.dbstatus(3) == 0 && stub_s1.dbstatus(3) == 0 ) {
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p4 insert a new statement STARTS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 stub_self4.addStudent(t,4);
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p4 insert a new statement ENDS");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 stub_s2.set_dbstatus(3,1);
        		 stub_s3.set_dbstatus(3,1);
        		 stub_s1.set_dbstatus(3,1);
        		 t++;

        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P4: updated rmi");

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
           	 if(stub_self4.dbstatus(2) == 1) {
        		 String insert1 = stub_s3.insert_container();
        		 int count=stmt.executeUpdate(insert1);
        		
           	      stub_self4.notify(2);
         	      System.out.println("Replicated Server 3 to Server 4");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P4: updated for p3");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
         	  }	 
         if(stub_self4.dbstatus(1) == 1) {
    		 String insert1 = stub_s2.insert_container();
    		 int count=stmt.executeUpdate(insert1);
    		
       	      stub_self4.notify(1);
         	      System.out.println("Replicated Server 2 to Server 4");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P4: updated for p2");

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
         	  }
       	 

       	 
       	 if(stub_self4.dbstatus(0) == 1) {
    		 String insert1 = stub_s1.insert_container();
    		 int count=stmt.executeUpdate(insert1);
    		
       	      stub_self4.notify(0);
          	      System.out.println("Replicated Server 1 to Server 4");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P4: updated for p1");

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
             
//-------------------------------------------------------------------------------------------------------             
//        	 System.out.println("We didnt update");
             Random rand = new Random();
             int upper = 10;
             x = rand.nextInt(upper);
             
             
             if(y<50) {
                 y++;
if(stub_self4.dbstatus1(3) == 0 && stub_s2.dbstatus1(3) == 0 && stub_s3.dbstatus1(3) == 0 && stub_s1.dbstatus1(3) == 0 ) {
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p4 update a new statement STARTS with sno"+ Integer.toString(x));

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 stub_self4.updateStudent(t1,x);
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p4 update a new statement ENDS for sno"+ Integer.toString(x));

//          	          logwtr.append();
          	             pw.flush();
          	          logwtr.close();
          	          
//          	          System.out.println("Successfully wrote to the file.");
          	        } catch (IOException e) {
          	          System.out.println("An error occurred.");
          	          e.printStackTrace();
          	        }
        		 stub_s2.set_dbstatus1(3,1);
        		 stub_s3.set_dbstatus1(3,1);
        		 stub_s1.set_dbstatus1(3,1);
        		 t1++;

        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P4: updated(update) rmi");

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
//------------------------------------------------------------------------------------------
	 if(stub_self4.dbstatus1(2) == 1) {
	 String insert1 = stub_s3.insert_container1();
	 int count=stmt.executeUpdate(insert1);
	
	      stub_self4.notify1(2);
      System.out.println("Replicated update of Server 3 to Server 4");
		 try {
	          FileWriter logwtr = new FileWriter("Server1.log",true);
	          BufferedWriter bw = new BufferedWriter(logwtr);
	          PrintWriter pw = new PrintWriter(bw);
	          System.out.println("LOGGING");

	          pw.println("P4: updated(update) for p3");

//	          logwtr.append();
	             pw.flush();
	          logwtr.close();
	          
//	          System.out.println("Successfully wrote to the file.");
	        } catch (IOException e) {
	          System.out.println("An error occurred.");
	          e.printStackTrace();
	        }
  }	 
if(stub_self4.dbstatus1(1) == 1) {
String insert1 = stub_s2.insert_container1();
int count=stmt.executeUpdate(insert1);

    stub_self4.notify1(1);
      System.out.println("Replicated update of Server 2 to Server 4");
		 try {
	          FileWriter logwtr = new FileWriter("Server1.log",true);
	          BufferedWriter bw = new BufferedWriter(logwtr);
	          PrintWriter pw = new PrintWriter(bw);
	          System.out.println("LOGGING");

	          pw.println("P4: updated(update) for p2");

//	          logwtr.append();
	             pw.flush();
	          logwtr.close();
	          
//	          System.out.println("Successfully wrote to the file.");
	        } catch (IOException e) {
	          System.out.println("An error occurred.");
	          e.printStackTrace();
	        }
  }



if(stub_self4.dbstatus1(0) == 1) {
String insert1 = stub_s1.insert_container1();
int count=stmt.executeUpdate(insert1);

    stub_self4.notify1(0);
	      System.out.println("Replicated update of Server 1 to Server 4");
		 try {
	          FileWriter logwtr = new FileWriter("Server1.log",true);
	          BufferedWriter bw = new BufferedWriter(logwtr);
	          PrintWriter pw = new PrintWriter(bw);
	          System.out.println("LOGGING");

	          pw.println("P4: updated(update) for p1");

//	          logwtr.append();
	             pw.flush();
	          logwtr.close();
	          
//	          System.out.println("Successfully wrote to the file.");
	        } catch (IOException e) {
	          System.out.println("An error occurred.");
	          e.printStackTrace();
	        }
	  }
             
if(stub_self4.dbstatus1(0) == 0 && stub_self4.dbstatus1(1) == 0 && stub_self4.dbstatus1(2) == 0 && stub_self4.dbstatus1(3) == 0) {
	 
	 
    System.out.println("Reading from process 4");
    list = new ArrayList<Student>(); 
    list = stub_self4.getStudents();
    
    try {
        FileWriter logwtr = new FileWriter("process4.log",true);
        BufferedWriter bw = new BufferedWriter(logwtr);
        PrintWriter pw = new PrintWriter(bw);
        System.out.println("LOGGING");

        pw.println(list);
      pw.println("\n");

//        logwtr.append();
           pw.flush();
        logwtr.close();
        
//        System.out.println("Successfully wrote to the file.");
      } catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
 	 
   
//  	 System.out.println("We didnt update");
   } 
             
             
         }
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}