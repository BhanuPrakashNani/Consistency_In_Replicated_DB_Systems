import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject; 
import java.rmi.*;

public class Server2 extends ImplExample2 { 
   public Server2() {} 
   public static void main(String args[]) { 
      try { 
         // Instantiating the implementation class 
         ImplExample2 obj = new ImplExample2(); 

         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub) 
         Hello stubk = (Hello) UnicastRemoteObject.exportObject(obj,0);  
//         Hello stub = new ImplExample();

//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(null);
//         Naming.bind("rmi://localhost:5000/sonoo",stub);  
         registry.bind("Hello2", stubk);  
         System.out.println("Server2 ready");
         Hello stub_self = (Hello) registry.lookup("Hello2");
         Thread.sleep(2000);
         Hello stub_s1 = (Hello) registry.lookup("Hello");
         System.out.println("lookup server2 ");

//         System.out.println(stub2.notify());
//         while(true) {
//             if(stub2.getstatus() ==1)
//        	 System.out.println(stub2.getMessage());
//
//         }
         int t =0;
         while(true) {
        	 if(stub_self.dbstatus() == 0 && stub_s1.dbstatus() == 0 && stub_s1.getStatus() == 0) {
        		 stub_self.setStatus();
                 Thread.sleep(2000);
        		 stub_self.addStudent(t);
        		 t++;
        	 }
        	 System.out.println("Server 2 We didnt update");
         }
     } catch (Exception e) { 
         System.err.println("Server 2 exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   } 
}