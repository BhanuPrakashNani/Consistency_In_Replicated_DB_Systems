import java.rmi.registry.*; 
import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;
import java.util.Random;
import java.rmi.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;


public class Server extends ImplExample { 
   public Server() {} 
   public static void main(String args[]) { 
	   List<Student> list = null;

      try {
     	 Class.forName("com.mysql.jdbc.Driver");
    	  // Instantiating the implementation class 
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi", "root", "asdf;lkj");

          Statement stmt = conn.createStatement();

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
         Hello stub_self = (Hello) registry.lookup("Hello");
         Thread.sleep(3000);
         Hello stub_s2 = (Hello) registry.lookup("Hello2");

         System.out.println("lookup server");

//         System.out.println(stub2.notify());
//         while(true) {
//             if(stub2.getstatus() ==1)
//        	 System.out.println(stub2.getMessage());
//
//         }
         Random rand = new Random();

         int t =0, x = 0;
//         Thread.sleep(2000);
         
//         stub_self.addStudent(t);
         Thread.sleep(2000);

         while(true) {
             Thread.sleep(2000);
        		 stub_self.setStatus();
        		 stub_self.write(t);
        		 stub_self.notify(2);
//                 stub_self.notify(2);
        		 t++;


        	 if(stub_s2.dbstatus(2) == 1) {

       		  list = (List<Student>)stub_s2.getStudents();
       	      String insert = "INSERT INTO samplermi(id, name, branch, percentage, email) values("+list.get(list.size()-1).getId()+",'"+list.get(list.size()-1).getName()+"','"+ list.get(list.size()-1).getBranch()+"',"+list.get(list.size()-1).getPercent()+",'"+ list.get(list.size()-1).getEmail()+ "')";
       	      int count=stmt.executeUpdate(insert);
       	      stub_s2.notify(2);
       	      System.out.println("Replicated Server 2 to Server 1");
       	  }
        	 
             if(stub_self.dbstatus() == 0 && stub_s2.dbstatus() == 0 && stub_s2.getStatus() == 0) {
        		 stub_self.setStatus();
        		 
	             System.out.println("T x is "+t+"  "+x);
	             
	             switch(rand.nextInt(2)) {
		            case 1:
		            	 stub_self.write(x);
		            	 break;
		            case 0:
		            	stub_self.setDbStatus();
		            	Student s = read(x);
		                if(s != null)
		             	   System.out.println("I am reading :"+s.getId());
		                else
		             	   System.out.println("READ NULL");
		                break;
		            default:
		            	System.out.println("NOTHING");
	             }
	             
	             
	            System.out.println("Im a boy");

	        
	             x = rand.nextInt(8);
	             stub_self.notify(2);
	             t++;
             }
//        	 System.out.println("We didnt update");
         }
     } catch (Exception e) { 
         System.err.println("Server exception: " + e.toString()); 
         e.printStackTrace(); 
      }
      

   }
   
	public static Student read(int t)throws Exception, ClassNotFoundException {
		  t = t % 7;
		   
	      // JDBC driver name and database URL 
	      String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi";  
	      
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
	      	
	      stmt = conn.createStatement();  
	      String sql = "SELECT * FROM samplermi where id ="+t; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      //Extract data from result set
	         // Retrieve by column name
	      	if(rs.next()) {
	         int id  = rs.getInt("id"); 
	         
	         String name = rs.getString("name"); 
	         String branch = rs.getString("branch"); 
	         
	         int percent = rs.getInt("percentage"); 
	         String email = rs.getString("email");  
	         
	         // Setting the values 
	         Student st = new Student(); 
	         st.setID(id); 
	         st.setName(name); 
	         st.setBranch(branch); 
	         st.setPercent(percent); 
	         st.setEmail(email); 
	    


	 		 try {
	 		      FileWriter logwtr = new FileWriter("Server1.log",true);
	 		      BufferedWriter bw = new BufferedWriter(logwtr);
	 		      PrintWriter pw = new PrintWriter(bw);
	 		      System.out.println("LOGGIGN");

	 		      pw.println("P1: Read id: "+id +"  Percent: "+ percent);

//	 		      logwtr.append();
	 	          pw.flush();
	 		      logwtr.close();
	 		      
//	 		      System.out.println("Successfully wrote to the file.");
	 		    } catch (IOException e) {
	 		      System.out.println("An error occurred.");
	 		      e.printStackTrace();
	 		    }
	 		 
	 		 return st;
	      	}
	 		 try {
	 		      FileWriter logwtr = new FileWriter("Server1.log",true);
	 		      BufferedWriter bw = new BufferedWriter(logwtr);
	 		      PrintWriter pw = new PrintWriter(bw);
	 		      System.out.println("LOGGIGN");

	 		      pw.println("P1:  Read id: "+t +" NULL");

//	 		      logwtr.append();
	 	          pw.flush();
	 		      logwtr.close();
	 		      
//	 		      System.out.println("Successfully wrote to the file.");
	 		    } catch (IOException e) {
	 		      System.out.println("An error occurred.");
	 		      e.printStackTrace();
	 		    }
	     return null;
		
	}
   
   
}