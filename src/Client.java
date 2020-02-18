import java.rmi.registry.LocateRegistry; 
import java.rmi.*;
import java.rmi.registry.Registry; 
import java.util.*;  

public class Client {  
   private Client() {}  
   public static void main(String[] args)throws Exception {  
	   List<Student> list = null;
      try { 
         // Getting the registry 
         Registry registry = LocateRegistry.getRegistry(null); 
         
         // Looking up the registry for the remote object 
        Hello stub = (Hello) registry.lookup("Hello"); 

         // Hello stub=(Hello)Naming.lookup("rmi://localhost:5000/sonoo");  

         // Calling the remote method using the obtained object 
         list = (List<Student>)stub.getStudents(); 
		 
           
      // System.out.println(list); 
      } catch (Exception e) { 
         System.err.println("Client exception: " + e);
      } 
      for (Student s:list) { 
          
          // System.out.println("bc "+s.getBranch()); 
          System.out.println("ID: " + s.getId()); 
          System.out.println("name: " + s.getName()); 
          System.out.println("branch: " + s.getBranch()); 
          System.out.println("percent: " + s.getPercent()); 
          System.out.println("email: " + s.getEmail());
      }
   } 
}