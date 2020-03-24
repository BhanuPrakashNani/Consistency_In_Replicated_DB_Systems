import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

public class Server extends DB1STUB { 
   public Server() {} 
   public static void main(String args[]) { 
	   //List<Student> list = null;

      try { 
         // Instantiating the implementation class 
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi", "root", "asdf;lkj");

          Statement stmt = conn.createStatement();

         DB1STUB obj = new DB1STUB(); 
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub) 
         DBRemote stub = (DBRemote) UnicastRemoteObject.exportObject(obj, 0); 

         Registry registry = LocateRegistry.getRegistry(); 
         registry.bind("Hello", stub);  
         System.out.println("Server ready");
         DBRemote stub_self = (DBRemote) registry.lookup("Hello");
         Thread.sleep(3000);
         DBRemote stub_s2 = (DBRemote) registry.lookup("Hello2");

         System.out.println("lookup server");


         Random  rand = new Random();
         int t =0, x = 0;
         boolean idExists = false;
         Thread.sleep(2000);
         String name = "Mani";
         String branch = "cse";
	     int percent = 01;
	     String email = "mani.gmail";
         
	     while(true) {
             Thread.sleep(2000);
             Student s = new Student();
	   	     t  = t% 7; 
             String query = "SELECT * FROM SAMPLERMI";
             ResultSet rs = stmt.executeQuery(query);
             while(rs.next()) {
		    	  if(t == rs.getInt("id")) {
		    		  idExists = true;
		    		  percent = rs.getInt("percentage")+1;
		    		  break;
		    	  }
		      }

             s.setID(t); 
	         s.setName(name); 
	         s.setBranch(branch); 
	         s.setPercent(percent); 
	         s.setEmail(email); 
	         percent = 1;
	         switch(rand.nextInt(2)) {
	            case 1:
	            	  stub_s2.addQobj(s);
	            	  stub_self.request(s); // request to write
	       	       	  break;
				case 0:
					x = rand.nextInt(8);
	            	Student st = stub_self.read(x); // read from db
					break;
	            default:
	            	System.out.println("NOTHING");
          }
	         
	         
	         
	         //sync with other writer processes
	         int tempStatus = stub_self.dbstatus(2)+stub_s2.dbstatus(2);
	         
	         if(tempStatus > 0) {
	        	 System.out.println("Writer 1 inside loop1 ");
	        	 Queue<Student> q = stub_self.getQobj();
//	        	 while(tempStatus > 0) {
//	        		 s = q.peek();
//	        		 q.remove();
//	        		 stub_self.addStudent(s);
//	        		 stub_self.notify(2);
//	        		 tempStatus--;
//	        	 }
	        	 syncDB synch = new syncDB(q, tempStatus, stub_self);
	        	 Thread thrd_sync = new Thread(synch);
	        	 thrd_sync.start();
	         }
	         
//	          tempStatus = stub_s2.dbstatus(2);
//	         
//	         if(tempStatus > 0) {
//	        	 System.out.println("Writer 1 inside loop2 ");
//
//	        	 Queue<Student> q = stub_self.getQobj();
//	        	 while(tempStatus > 0) {
//	        		 s = q.peek();
//	        			Student st = q.peek();
//	        	        System.out.println("ID: " + st.getId()); 
//	        	        System.out.println("name: " + st.getName()); 
//	        	        System.out.println("branch: " + st.getBranch()); 
//	        	        System.out.println("percent: " + st.getPercent()); 
//	        	        System.out.println("email: " + st.getEmail());
//	        		 q.remove();
//	        		 stub_self.addStudent(s);
//	        		 stub_s2.notify(2);
//	        		 tempStatus--;
//	        	 }
//	         }
	         System.out.println("WRITER 1 "+t); 
	         t++;
        	 
         }
     } 
      catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}

class syncDB implements Runnable
{
	Queue<Student> q = new LinkedList<>();
	int tempstatus = 0;
	DBRemote stub_self = null;
	Student s;
	syncDB(Queue<Student> que, int tempstat, DBRemote stub_slf){
		this.q = que;
		this.tempstatus = tempstat;
		this.stub_self = stub_slf;
	}
	
	public void run() {
		System.out.println("Thread for sync start");
		try {
			q = stub_self.getQobj();

	   	 while(this.tempstatus > 0) {
	   		 s = q.peek();
	   		 q.remove();
	   		 stub_self.addStudent(s);
	   		 stub_self.notify(2);
	   		 this.tempstatus--;
	   	 }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}