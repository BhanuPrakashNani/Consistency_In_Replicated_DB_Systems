import java.rmi.registry.LocateRegistry; 
import java.rmi.*;
import java.rmi.registry.Registry; 
import java.util.*; 
import java.sql.*;

public class Client {  
   private Client() {}  
   public static void main(String[] args)throws Exception, ClassNotFoundException {  
	   List<Student> list = null;
      try { 
    	  Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db1", "root", "asdf;lkj");
         // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
         Statement stmt = conn.createStatement();
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 
         
         // Looking up the registry for the remote object 
         System.out.println("lookup client");
         Hello stub = (Hello) registry.lookup("Hello"); 
        
         // Hello stub=(Hello)Naming.lookup("rmi://localhost:5000/sonoo");  

         // Calling the remote method using the obtained object 
         list = (List<Student>)stub.getStudents(); 
		 

           
      // System.out.println(list); 
       
      for (Student s:list) { 
          
          // System.out.println("bc "+s.getBranch()); 
          System.out.println("ID: " + s.getId()); 
          System.out.println("name: " + s.getName()); 
          System.out.println("branch: " + s.getBranch()); 
          System.out.println("percent: " + s.getPercent()); 
          System.out.println("email: " + s.getEmail());
      }
      
      String insert = "INSERT INTO users(sno, name) values("+list.get(0).getId()+",'"+list.get(0).getName()+"','"+ list.get(0).getBranch()+"',"+list.get(0).getPercent()+",'"+ list.get(0).getEmail()+ "')";
      int count=stmt.executeUpdate(insert);    

      
      
      
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