import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
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
//         Hello stub = new ImplExample();
//       
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello", stub);  
         System.out.println("Server ready");
         DBRemote stub_self = (DBRemote) registry.lookup("Hello");
         Thread.sleep(3000);
         DBRemote stub_s2 = (DBRemote) registry.lookup("Hello2");

         System.out.println("lookup server");

//         System.out.println(stub2.notify());
//         while(true) {
//             if(stub2.getstatus() ==1)
//        	 System.out.println(stub2.getMessage());
//
//         }
         int t =0;
//         stub_self.addStudent(t);
         Thread.sleep(2000);
         String name = "Mani";
         String branch = "cse";
	     int percent = 95;
	     String email = "mani.gmail";
         
	     while(true) {
             Thread.sleep(2000);
             Student s = new Student();
             s.setID(t); 
	         s.setName(name); 
	         s.setBranch(branch); 
	         s.setPercent(percent); 
	         s.setEmail(email); 
	         
	         stub_self.request(s);
	         
	         int tempStatus = stub_self.dbstatus(2);
	         
	         if(tempStatus > 0) {
	        	 Queue<Student> q = stub_self.getQobj();
	        	 while(tempStatus > 0) {
	        		 s = q.peek();
	        		 stub_self.addStudent(s);
	        		 tempStatus--;
	        	 }
	         }
	         
	         tempStatus = stub_s2.dbstatus(2);
	         
	         if(tempStatus > 0) {
	        	 Queue<Student> q = stub_s2.getQobj();
	        	 while(tempStatus > 0) {
	        		 s = q.peek();
	        		 stub_s2.addStudent(s);
	        		 tempStatus--;
	        	 }
	         }
	         t++;
        	 
         }
     } 
      catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}