import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ImplExample2 implements Hello{
	static int status;
	int[] dbstatus = new int[]{ 0, 0, 0, 0};  // 0- c1, 1-c2, 3-s1, 4-s2
	String msg =",";
	public List<Student> getStudents() throws Exception, ClassNotFoundException {
   	 		Class.forName("com.mysql.jdbc.Driver");
			List<Student> list = new ArrayList<Student>();   
	      // JDBC driver name and database URL 
	      String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi4";  
	      
	      // Database credentials 
	      String USER = "root"; 
	      String PASS = "bhanuprakash";  
	      
	      Connection conn = null; 
	      Statement stmt = null;  

	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("SERVER 2 Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("SERVER 2 Creating statement..."); 
	      	
	      stmt = conn.createStatement();  
	      String sql = "SELECT * FROM samplermi"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      //Extract data from result set 
	      while(rs.next()) { 
	         // Retrieve by column name 
	         int id  = rs.getInt("sno"); 
	         
	         String name = rs.getString("name"); 
	         String branch = rs.getString("branch"); 
	         
	         int percent = rs.getInt("percentage"); 
	         String email = rs.getString("email");  
	         
	         // Setting the values 
	         Student student = new Student(); 
	         student.setID(id); 
	         student.setName(name); 
	         student.setBranch(branch); 
	         student.setPercent(percent); 
	         student.setEmail(email); 
	         list.add(student); 
	      } 
	      rs.close(); 
	      return list;     

	      }
	public void addStudent(int id)throws Exception, ClassNotFoundException {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi4";  
	      
	      // Database credentials 
	      String USER = "root"; 
	      String PASS = "bhanuprakash";  
	      
	      Connection conn = null; 
	      Statement stmt = null;  

	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("SERVER 2 Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, USER, PASS); 
	      System.out.println("SERVER 2 Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("SERVER 2 Creating statement...");
	      
	      	
	      stmt = conn.createStatement();  
	      String sql = "SELECT * FROM samplermi"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      //Extract data from result set 
	      while(rs.next()) { 
	         // Retrieve by column name 
	         int id  = rs.getInt("sno"); 
	         
	         String name = rs.getString("name"); 
	         String branch = rs.getString("branch"); 
	         
	         int percent = rs.getInt("percentage"); 
	         String email = rs.getString("email");  
	         
	         // Setting the values 
	         Student student = new Student(); 
	         student.setID(id); 
	         student.setName(name); 
	         student.setBranch(branch); 
	         student.setPercent(percent); 
	         student.setEmail(email); 
	         list.add(student); 
	      } 
	      rs.close(); 
	      return list; 
	}
	
	public void write(int sno)throws Exception, ClassNotFoundException {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      
	      int t = sno%7;
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi4";  
	      
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
	      
	      
	      String name = "Mani";
	      String branch = "cse";
	      int percent = 95;
	      String email = "mani.gmail";
	      
	      boolean idExists = false;
	      stmt = conn.createStatement();
	      String sql = "SELECT * FROM samplermi"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      //Extract data from result set 
	      while(rs.next()) { 
	         // Retrieve by column name 
	         if( t == rs.getInt("sno")) {
	        	 idExists = true;
	        	 percent += rs.getInt("percentage");
	        	 break;
	         }
	      }
	      
	      if (!idExists) {
		      //ResultSet rs = stmt.executeQuery(sql);  
		      String insert = "INSERT INTO samplermi(id, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
		      int count=stmt.executeUpdate(insert);
		      msg = insert;
		      }
		      else {
		    	  
		    	  String update = "UPDATE samplermi SET percentage = "+percent+" where id = "+t;
			      int count=stmt.executeUpdate(update);
			      msg = update;
		      }
		      setDbStatus();
	//	      dbstatus[2] =0;
		      status = 0;
	
	
		 		 try {
		 		      FileWriter logwtr = new FileWriter("Server1.log",true);
		 		      BufferedWriter bw = new BufferedWriter(logwtr);
		 		      PrintWriter pw = new PrintWriter(bw);
		 		      System.out.println("LOGGIGN");
	
		 		      pw.println("P1:  Write id: "+t	 +"  Percent: "+ percent);
	
	//	 		      logwtr.append();
		 	          pw.flush();
		 		      logwtr.close();
		 		      
	//	 		      System.out.println("Successfully wrote to the file.");
		 		    } catch (IOException e) {
		 		      System.out.println("An error occurred.");
		 		      e.printStackTrace();
		 		    }
		      System.out.println("wrote");    
		          

	}
	@Override
	public void setWrite() throws RemoteException {
        isWrite = true;

    }
	
	@Override
    public void setRead() throws RemoteException {
    	isWrite = false;
    }
	
	@Override
    public boolean isWrite() throws RemoteException {
    	return isWrite;
    }
	
	@Override
    public String getStmt() throws RemoteException {
    	return msg;
    }
    
    @Override
    public void sendMessage(String s) throws RemoteException {
        System.out.println(s);
        msg = s;
        status++;
    }
    
    @Override
    public void setDbStatus() throws RemoteException {
	      for (int i =0; i<4; i++) {
	    	  dbstatus[i]++;
	      }
    }

    @Override
    public String getMessage() throws RemoteException {
    	status--;
        return "Your message is: " + msg;
        
    }
    @Override
    public void notify(int i) throws RemoteException{
    	dbstatus[i]--;
    }
    
    @Override
    public int dbstatus(int i) throws RemoteException{
    	return dbstatus[i];
    }
    @Override
    public int dbstatus() throws RemoteException{
    	int status = 0;
    	for (int i=0; i<4; i++) {
    		status = status + dbstatus[i];
    	}
    	status = status - dbstatus[3];
    	return status;
    }
	@Override
	public int getStatus() throws RemoteException {
		// TODO Auto-generated method stub
		return status;
	}
	@Override
	public void setStatus() throws RemoteException {
		// TODO Auto-generated method stub
		status = 1;
	}
    
}

