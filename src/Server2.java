import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class Server2 extends DB4STUB { 
   public Server2() {} 
   public static void main(String args[]) { 
	   //List<Student> list = null;

      try { 
         // Instantiating the implementation class 
         DB4STUB obj = new DB4STUB(); 
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi4", "root", "asdf;lkj");

         Statement stmt = conn.createStatement();

         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub) 
         DBRemote stubk = (DBRemote) UnicastRemoteObject.exportObject(obj,0);  
//         Hello stub = new ImplExample();

//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(null);
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello2", stubk);  
         System.out.println("Server2 ready");
         DBRemote stub_self = (DBRemote) registry.lookup("Hello2");
         Thread.sleep(2000);
         DBRemote stub_s1 = (DBRemote) registry.lookup("Hello");
         System.out.println("lookup server2 ");

//         System.out.println(stub2.notify());
//         while(true) {
//             if(stub2.getstatus() ==1)
//        	 System.out.println(stub2.getMessage());
//
//         }
         int t =0;
         Thread.sleep(2000);
         String name = "Bhanu";
         String branch = "cse";
	     int percent = 94;
	     String email = "bhanu.gmail";

         while(true) {
             Thread.sleep(3000);
             Student s = new Student();
             s.setID(t); 
	         s.setName(name); 
	         s.setBranch(branch); 
	         s.setPercent(percent); 
	         s.setEmail(email); 

	         stub_self.request(s);
	         
	         int tempStatus = stub_self.dbstatus(3);
	         
	         if(tempStatus > 0) {
	        	 System.out.println("Writer 2 inside loop1 ");

	        	 Queue<Student> q = stub_self.getQobj();
	        	 while(tempStatus > 0) {
	        		 s = q.peek();
	        		 q.remove();
	        		 System.out.println("Hiiiiii");
	        		 stub_self.addStudent(s);
	        		 stub_self.notify(3);
	        		 tempStatus--;
	        	 }
	         }
	         
	         tempStatus = stub_s1.dbstatus(3);
	         
	         if(tempStatus > 0) {
	        	 System.out.println("Writer 2 inside loop2 ");

	        	 Queue<Student> q = stub_s1.getQobj();
	        	 while(tempStatus > 0) {
	        		 s = q.peek();
	        			Student st = q.peek();
	        	        System.out.println("ID: " + st.getId()); 
	        	        System.out.println("name: " + st.getName()); 
	        	        System.out.println("branch: " + st.getBranch()); 
	        	        System.out.println("percent: " + st.getPercent()); 
	        	        System.out.println("email: " + st.getEmail());
	        			System.out.println("QUEUE 1");
	        		 q.remove();
	        		 stub_self.addStudent(s);
	        		 stub_s1.notify(3);
	        		 tempStatus--;
	        	 }
	         }
	         t++;
	         
         }
     } 
      catch (Exception e) { 
         System.err.println("Server 2 exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}