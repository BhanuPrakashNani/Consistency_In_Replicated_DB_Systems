import java.rmi.registry.LocateRegistry; 
import java.rmi.*;
import java.rmi.registry.Registry; 
import java.util.*; 
import java.sql.*;

public class Client2 {  
   private Client2() {}  
   public static void main(String[] args)throws Exception, ClassNotFoundException {  
	   List<Student> list = null;
	   Student s;
      try { 
    	  Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi3", "root", "asdf;lkj");
         // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
         Statement stmt = conn.createStatement();
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 
         
         // Looking up the registry for the remote object 
         System.out.println("lookup client");
         DBRemote stub = (DBRemote) registry.lookup("Hello"); 
         DBRemote stub2 = (DBRemote) registry.lookup("Hello2");
         // Hello stub=(Hello)Naming.lookup("rmi://localhost:5000/sonoo");  

         // Calling the remote method using the obtained object 
         list = (List<Student>)stub.getStudents(); 
		 

           
      // System.out.println(list); 
       
         int t=0;

         while(true) {
       	  int tempStatus = stub.dbstatus(1);
   	         
   	         if(tempStatus > 0) {
   	        	 System.out.println("Writer 1 inside loop1 ");
   	        	 Queue<Student> q = stub.getQobj();
   	        	 while(tempStatus > 0) {
   	        		 s = q.peek();
   	        		 q.remove();
   	        		 String insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values("+s.getId()+",'"+s.getName()+"','"+s.getBranch()+"',"+s.getPercent()+",'"+s.getEmail()+"')";
   	        		 stmt.executeUpdate(insert);
   	        		 stub.notify(1);
   	        		 tempStatus--;
   	        	 }
   	         }
   	         
   	         tempStatus = stub2.dbstatus(1);
   	         
   	         if(tempStatus > 0) {
   	        	 System.out.println("Writer 1 inside loop2 ");

   	        	 Queue<Student> q = stub2.getQobj();
   	        	 while(tempStatus > 0) {
   	        		 s = q.peek();
   	        			Student st = q.peek();
   	        	        System.out.println("ID: " + st.getId()); 
   	        	        System.out.println("name: " + st.getName()); 
   	        	        System.out.println("branch: " + st.getBranch()); 
   	        	        System.out.println("percent: " + st.getPercent()); 
   	        	        System.out.println("email: " + st.getEmail());
   	        		 q.remove();
   	        		 String insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values("+s.getId()+",'"+s.getName()+"','"+s.getBranch()+"',"+s.getPercent()+",'"+s.getEmail()+"')";
   	        		 stmt.executeUpdate(insert);
   	        		 
   	        		 
   	        		 
   	        		 stub2.notify(1);
   	        		 tempStatus--;
   	        	 }
   	         }
   	         System.out.println("Client 1 "+t);
   	         t++;
//       	  System.out.println("Server not updated");
         }
      
      
      
//      String msg="";
//      while(true) {
//    	  if(stub.getstatus() == 0) {
//    		  System.out.println("Enter your  message");
//    		  Scanner sc = new Scanner(System.in);
//    		  msg = sc.nextLine();
//    		  stub.sendMessage(msg);
//     
//    	  }
//      }
      } catch (Exception e) { 
          System.err.println("Client exception: " + e);
       }
      } 
}