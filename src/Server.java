import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.rmi.*;

public class Server extends ImplExample { 
   public Server() {} 
   public static void main(String args[]) { 
      try { 
         // Instantiating the implementation class 
         ImplExample obj = new ImplExample(); 
    
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub) 
         Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);  
//         Hello stub = new ImplExample();
//         
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello", stub);  
         System.out.println("Server ready");
         Hello stub2 = (Hello) registry.lookup("Hello");
         System.out.println("lookup server");

//         System.out.println(stub2.notify());
//         while(true) {
//             if(stub2.getstatus() ==1)
//        	 System.out.println(stub2.getMessage());
//
//         }
         int t =0;
         stub2.addStudent(t);
         while(true) {
        	 if(stub2.dbstatus() == 0) {
        		 stub2.addStudent(t);
        		 t++;
        	 }
        	 System.out.println("We didnt update");
         }
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}