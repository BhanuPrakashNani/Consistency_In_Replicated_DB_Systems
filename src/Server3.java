import java.rmi.registry.LocateRegistry; 
import java.rmi.RemoteException; 
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.rmi.registry.*;  
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.rmi.*;
import java.io.BufferedWriter;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.PrintWriter;


public class Server3 extends ImplExample { 
   public Server3() {} 
   public static void main(String args[]) { 
	   List<Student> list = null;

      try {
    	  Class.forName("com.mysql.jdbc.Driver");
         // Instantiating the implementation class 
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi5", "root", "bhanuprakash");

          Statement stmt = conn.createStatement();

         ImplExample obj = new ImplExample(); 
         // Exporting the object of implementation class (
         // here we are exporting the remote object to the stub) 
         Hello stub3 = (Hello) UnicastRemoteObject.exportObject(obj, 0); 
//       
//         // Binding the remote object (stub) in the registry 
         Registry registry = LocateRegistry.getRegistry(); 
         registry.bind("Hello3", stub3);  
         System.out.println("Server3 ready");
         Hello stub_self = (Hello) registry.lookup("Hello3");
         Thread.sleep(3000);
         Hello stub_s2 = (Hello) registry.lookup("Hello2");
         Hello stub_s1 = (Hello) registry.lookup("Hello");

         System.out.println("lookup server3");
         Random rand = new Random();

         int t =0, x = 0;
//         Thread.sleep(2000);

         while(Config.SAFE) {
             Thread.sleep(2000);
	         System.out.println("HElllo man DBSTATUS2 "+stub_s2.dbstatus(0)+" "+stub_s2.dbstatus(1)+" is Write "+stub_s2.isWrite());

        	 if(stub_s2.dbstatus(4) == 1 ) {
        		 if(stub_s2.isWrite()) {
        			 list = (List<Student>)stub_s2.getStudents();
        			 String insert = "INSERT INTO samplermi(id, name, branch, percentage, email) values("+list.get(list.size()-1).getId()+",'"+list.get(list.size()-1).getName()+"','"+ list.get(list.size()-1).getBranch()+"',"+list.get(list.size()-1).getPercent()+",'"+ list.get(list.size()-1).getEmail()+ "')";
        			 String exec = stub_s2.getStmt();
        			 int count=stmt.executeUpdate(exec);
        		 }
          	      stub_s2.notify(4);
          	      System.out.println("Replicated Server 2 to Server 3");
          	  }
        	 Thread.sleep(3000);
        	 System.out.println("HElllo man DBSTATUS1 "+stub_s1.dbstatus(0)+" "+stub_s1.dbstatus(1)+" is Write "+stub_s1.isWrite());

        	 if(stub_s1.dbstatus(4) == 1 ) {
        		 if(stub_s1.isWrite()) {
        			 list = (List<Student>)stub_s1.getStudents();
        			 String insert = "INSERT INTO samplermi(id, name, branch, percentage, email) values("+list.get(list.size()-1).getId()+",'"+list.get(list.size()-1).getName()+"','"+ list.get(list.size()-1).getBranch()+"',"+list.get(list.size()-1).getPercent()+",'"+ list.get(list.size()-1).getEmail()+ "')";
        			 String exec = stub_s1.getStmt();
        			 int count=stmt.executeUpdate(exec);
        		 }
          	      stub_s1.notify(4);
          	      System.out.println("Replicated Server 1 to Server 3");
          	  }
             if(stub_self.dbstatus() == 0 && stub_s2.dbstatus() == 0 && stub_s2.getStatus() == 0 && stub_s1.dbstatus() == 0 && stub_s1.getStatus() == 0) {
        		 stub_self.setStatus();
        		 
	             System.out.println("T x is "+t+"  "+x);
	             
	             switch(rand.nextInt(2)) {
		            case 1:
		            	 stub_self.setWrite();
		            	 stub_self.write(x);
		            	 break;
		            case 0:
		            	stub_self.setRead();
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

        	 System.out.println("We didnt update");
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
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi5";  
	      
	      // Database credentials 
	      String USER = "root"; 
	      String PASS = "bhanuprakash";  
	      
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
	 		      System.out.println("LOGGING");

	 		      pw.println("P3: Read id: "+id +"  Percent: "+ percent);

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

	 		      pw.println("P3:  Read id: "+t +" NULL");

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