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

public class process1 extends container1 { 
   public process1() { }
   public static void main(String args[]) { 
	   
	   List<Student> list = null;
	   String insert="";
	   int x=0;
	   int y=0;
	   int z=0;
      try { 
    	  Class.forName("com.mysql.jdbc.Driver");
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi", "newuser", "password");

          Statement stmt = conn.createStatement();
         
          
          container1 obj = new container1(); 
         
          
          hello stub1 = (hello) UnicastRemoteObject.exportObject(obj, 0); 
         
          
          
          Registry registry = LocateRegistry.getRegistry(); 
         
          registry.bind("Hello", stub1);  
         System.out.println("Server ready");
         
       
         hello stub_self1 = (hello) registry.lookup("Hello"); //skeleton
         Thread.sleep(3000);
         hello stub_s2 = (hello) registry.lookup("Hello2"); //skeleton
         hello stub_s3 = (hello) registry.lookup("Hello3");
         hello stub_s4 = (hello) registry.lookup("Hello4");

         System.out.println("lookup server");
//CONNECTION AND BASIC SETUP DONE

         
         
         
         
         
         int t =0;
         int t1=0;
         Thread.sleep(200);

         
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
        	 
             while (t<10 || (stub_self1.dbstatus(0)!=0 || stub_self1.dbstatus(1)!=0 || stub_self1.dbstatus(2)!=0 || stub_self1.dbstatus(3)!=0)){
            	 if(t<10) {
        	 if(stub_self1.dbstatus(0) == 0 && stub_s2.dbstatus(0) == 0 && stub_s3.dbstatus(0) == 0 && stub_s4.dbstatus(0) == 0 ) {
        		 
        		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("p1 request insert a new statement STARTS");


       	             pw.flush();
       	          logwtr.close();
       	          

       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
       	 stub_self1.request_write(t,1);
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p1 request insert a new statement ENDS");

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

        	          pw.println("P1: requested write");

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
        		 stub_self1.request_write_others(insert1);
          	     
        		 stub_self1.notify(1);
          	      System.out.println("requested replcation of Server 2 to Server 1");
          	   try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P1: requested write for p2");

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
        		 stub_self1.request_write_others(insert1);
        		
           	      stub_self1.notify(2);
           	      System.out.println("requested replication of Server 3 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: requested write for p3");

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
           		 stub_self1.request_write_others(insert1);
           	      stub_self1.notify(3);
           	      System.out.println("requested replication of Server 4 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: requested write for p4");

//          	          logwtr.append();
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

       	          pw.println("p1 request to insert a new statement STARTS with sno: "+Integer.toString(x));

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
       	 
        		 
        		 
        		 
        		 stub_self1.request_update(t1,x);
        		 
        		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("p1 request to update a new statement ENDS with sno: "+Integer.toString(x));

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

        	          pw.println("P1: updated(request) rmi");

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
        		 stub_self1.request_update_others(insert1);    
          	      stub_self1.notify1(1);
          	      System.out.println("Replicated update of Server 2 to Server 1");
          	      
         		 try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("P1: updated(request) for p2");

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
        		 stub_self1.request_update_others(insert1);   
        		
           	      stub_self1.notify1(2);
           	      System.out.println("Replicated update of Server 3 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: updated(request) for p3");

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
           		stub_self1.request_update_others(insert1);   
           	      stub_self1.notify1(3);
           	      System.out.println("Replicated update of Server 4 to Server 1");
          		 try {
          	          FileWriter logwtr = new FileWriter("Server1.log",true);
          	          BufferedWriter bw = new BufferedWriter(logwtr);
          	          PrintWriter pw = new PrintWriter(bw);
          	          System.out.println("LOGGING");

          	          pw.println("P1: updated(request) for p4");

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
           	      
           	      
           	   try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("read in p1 starts");

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
           	      
           	      
           	      
           	   Random rand1 = new Random();
               int upperno = 10;
               int d = rand1.nextInt(upperno);
               Student student = new Student();
               list = new ArrayList<Student>(); 
        	   student = stub_self1.getStudents(d);  
           	      
        	   try {
        	          FileWriter logwtr = new FileWriter("Server1.log",true);
        	          BufferedWriter bw = new BufferedWriter(logwtr);
        	          PrintWriter pw = new PrintWriter(bw);
        	          System.out.println("LOGGING");
        	          if(student.getId()!=-1)
        	          pw.println("P1: read "+ student.getId() + " with sno: "+ student.getPercent());

//        	          logwtr.append();
        	             pw.flush();
        	          logwtr.close();
        	          
//        	          System.out.println("Successfully wrote to the file.");
        	        } catch (IOException e) {
        	          System.out.println("An error occurred.");
        	          e.printStackTrace();
        	        }
        	   try {
       	          FileWriter logwtr = new FileWriter("Server1.log",true);
       	          BufferedWriter bw = new BufferedWriter(logwtr);
       	          PrintWriter pw = new PrintWriter(bw);
       	          System.out.println("LOGGING");

       	          pw.println("read in p1 ends");

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
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
//-----------------------------------------------------------------sync part---------------------------------------------------------------------------------
        	 
        	 
        	   	Queue<String> q = stub_self1.queue();
//        		 syncDB synch = new syncDB(q);
//        		 Thread thrd_sync = new Thread(synch);
//        		 Thread.sleep(2000);
//        		 thrd_sync.start();
//        		 try {
//          	          FileWriter logwtr = new FileWriter("Server1.log",true);
//          	          BufferedWriter bw = new BufferedWriter(logwtr);
//          	          PrintWriter pw = new PrintWriter(bw);
//          	          System.out.println("LOGGING");
//
//          	          pw.println("p1: " + thrd_sync.getId());
//
////          	          logwtr.append();
//          	             pw.flush();
//          	          logwtr.close();
//          	          
////          	          System.out.println("Successfully wrote to the file.");
//          	        } catch (IOException e) {
//          	          System.out.println("An error occurred.");
//          	          e.printStackTrace();
//          	        }
//        		 
//        		 try {
//       	          FileWriter logwtr = new FileWriter("Server1.log",true);
//       	          BufferedWriter bw = new BufferedWriter(logwtr);
//       	          PrintWriter pw = new PrintWriter(bw);
//       	          System.out.println("LOGGING");
//
//       	          pw.println("p1 : " + thrd_sync.getId());
//
////       	          logwtr.append();
//       	             pw.flush();
//       	          logwtr.close();
//       	          
////       	          System.out.println("Successfully wrote to the file.");
//       	        } catch (IOException e) {
//       	          System.out.println("An error occurred.");
//       	          e.printStackTrace();
//       	        }
//        		 stub_self1.clearqueue(); 
        	 if(x==9||y>42) {
             while(!q.isEmpty()) {
 			    String insert1 = q.peek();
 				q.remove();
 				
 				
 				String DB_URL = "jdbc:mysql://localhost:3306/rmi";  
 			    Connection conn1 = null; 
 			    Statement stmt1 = null;  

 			      //Register JDBC driver 
 			        
 			      //Open a connection
 			      System.out.println("p1: Connecting to a selected database..."); 
 			      conn1 = DriverManager.getConnection(DB_URL, "newuser", "password"); 
 			      System.out.println("p1: Connected database successfully...");  
 			      stmt1 = conn1.createStatement();
 			      //ResultSet rs = stmt.executeQuery(sql);  
 			      stmt1.executeUpdate(insert1);
 			      conn1.close();
 			      try {
 	       	          FileWriter logwtr = new FileWriter("Server1.log",true);
 	       	          BufferedWriter bw = new BufferedWriter(logwtr);
 	       	          PrintWriter pw = new PrintWriter(bw);
 	       	          System.out.println("LOGGING");
 	       	          
 	       	       pw.println("P1: wrote once");
 	       	    
 	       	    pw.println(insert1);
 	       	          pw.println("P1: wrote once");

// 	       	          logwtr.append();
 	       	             pw.flush();
 	       	          logwtr.close();
 	       	          
// 	       	          System.out.println("Successfully wrote to the file.");
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

       	          pw.println("P1: finished a set");

//       	          logwtr.append();
       	             pw.flush();
       	          logwtr.close();
       	          
//       	          System.out.println("Successfully wrote to the file.");
       	        } catch (IOException e) {
       	          System.out.println("An error occurred.");
       	          e.printStackTrace();
       	        }
        	 
 			x=0;
 			stub_self1.clearqueue();
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 
        	 }
        	 
        	 
        	 
        	 
        	 
        	 
             
         }
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}


////
////class syncDB implements Runnable
////{
////
////	Queue<String> q = new LinkedList<>();
////	int tempstatus = 0;
////	
////	
////
////	
////	syncDB(Queue<String> queue){
////		this.q = queue;
////	}
////	
////	
////	
////	
////	public void run() {
////		System.out.println("Thread for sync start");
////		try {
////			
////			w
////
////			
////			
////			
////			
////			
////		
////			
////		} catch (Exception e) {
////			e.printStackTrace();
////		}
////	}
////	
//}