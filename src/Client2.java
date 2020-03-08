import java.rmi.registry.LocateRegistry;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
   	        		 replicate(s);
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
   	        		 replicate(s);
   	        		 
   	        		 
   	        		 stub2.notify(1);
   	        		 tempStatus--;
   	        	 }
   	         }
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
   public static void replicate(Student s) throws Exception, ClassNotFoundException {
	   String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi3";  
	      
	      // Database credentials 
	      String USER = "root"; 
	      String PASS = "asdf;lkj";  
	      
	      Connection conn = null; 
	      Statement stmt = null;  
	      
	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement...");
	      
	      boolean idExists = false;
	      stmt = conn.createStatement();
	      String sql = "SELECT * FROM samplermi"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      int id = s.getId();
	      String name = s.getName();
	      String branch = s.getBranch();
	      int percent = s.getPercent();
	      String email = s.getEmail();
	      
	      int t = id % 7;
	      // search for id in the database 
	      while(rs.next()) {
	    	  if(t == rs.getInt("id")) {
	    		  idExists = true;
	    		  percent += rs.getInt("percentage");
	    		  break;
	    	  }
	      }
	      
	      stmt = conn.createStatement();
	      //ResultSet rs = stmt.executeQuery(sql);  
	      if (!idExists) {
	      //ResultSet rs = stmt.executeQuery(sql);  
		      String insert = "INSERT INTO samplermi(id, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
		      int count=stmt.executeUpdate(insert);
	      }
	      else {
	    	  
	    	  String update = "UPDATE samplermi SET percentage = "+percent+" where id = "+t;
		      int count=stmt.executeUpdate(update);
	      }
	      try {
		      FileWriter logwtr = new FileWriter("Server1.log",true);
		      BufferedWriter bw = new BufferedWriter(logwtr);
		      PrintWriter pw = new PrintWriter(bw);
		      System.out.println("LOGGIGN");

		      pw.println("C2:  Write id: "+t	 +"  Percent: "+ percent);

//		      logwtr.append();
	          pw.flush();
		      logwtr.close();
		      
//		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
   System.out.println("wrote in C2");    
	}

}