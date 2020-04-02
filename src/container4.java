import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class container4 implements hello{
	static int status;
	int[] dbstatus = new int[]{ 0, 0, 0, 0};
	int[] dbstatus1 = new int[]{ 0, 0, 0, 0};
	String msg =",";
	String insert;
	int i=0;
	int id=0;
	int l=-100;
	int k=0;
	Queue<String> queue = new LinkedList<>();
	public Student getStudents(int k) throws Exception, ClassNotFoundException {  
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
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi";  
	      
 
	      
	      Connection conn = null; 
	      Statement stmt = null;  

	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, "newuser", "password"); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement..."); 
	      	
	      stmt = conn.createStatement();  
	      String sql = "SELECT * FROM samplermi where sno = " + Integer.toString(k) + " and name = 'Mani';";
	      ResultSet rs = stmt.executeQuery(sql);  
	      Student student = new Student();
	      //Extract data from result set 
	      student.setID(-1);
	      if(rs.next()) {
	         // Retrieve by column name 
	         int id  = rs.getInt("sno"); 
	         
	         String name = rs.getString("name"); 
	         String branch = rs.getString("branch"); 
	         
	         int percent = rs.getInt("percentage"); 
	         String email = rs.getString("email");  
	         
	         // Setting the values 
	         
	         student.setID(id); 
	         student.setName(name); 
	         student.setBranch(branch); 
	         student.setPercent(percent); 
	         student.setEmail(email); 
	         list.add(student); 
	      }
	      rs.close(); 
	      conn.close();
	      return student;     

	      }
	public void addStudent(int id, int k)throws Exception, ClassNotFoundException {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER);
	    	  this.id=id;
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi" + Integer.toString(k);  
	      
  
	      
	      Connection conn = null; 
	      Statement stmt = null;  

	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, "newuser", "password"); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Creating statement...");
	      
	      
	      String name = "shreshta";
	      String branch = "cse";
	      int percent = i;
	      String email = "shre.gmail";
	      i++;
	      stmt = conn.createStatement();
	      //ResultSet rs = stmt.executeQuery(sql);  
	      insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
	      int count=stmt.executeUpdate(insert);
	      conn.close();
//	      dbstatus[2] =0;
	      status = 0;
	      System.out.println(count);     

	}
	
	
	
	public void updateStudent(int id,int k)throws Exception, ClassNotFoundException {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	    	  this.k=k;
	      }
	      catch(ClassNotFoundException e) {
	    	System.out.println("SWWWWWERRRRRR");
	    	e.printStackTrace();
	      }
	      String DB_URL = "jdbc:mysql://localhost:3306/rmi";  
	      
 
	      
	      Connection conn = null; 
	      Statement stmt = null;  

	      //Register JDBC driver 
	        
	      //Open a connection
	      System.out.println("Connecting to a selected database..."); 
	      conn = DriverManager.getConnection(DB_URL, "newuser", "password"); 
	      System.out.println("Connected database successfully...");  
	      
	      //Execute a query 
	      System.out.println("Updating statement...");
	     

	      i++;
	      stmt = conn.createStatement();
	      //ResultSet rs = stmt.executeQuery(sql);  
	      insert = "UPDATE samplermi SET percentage = " + Integer.toString(i) + " where sno = " + Integer.toString(k)+" ;";
	      int count=stmt.executeUpdate(insert);
	      conn.close();
//	      dbstatus[2] =0;
	      status = 0;
	      System.out.println(count);     

	}
    @Override
    public void sendMessage(String s) throws RemoteException {
        System.out.println(s);
        msg = s;
        status++;
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
    public void set_dbstatus(int i, int val) throws RemoteException{
    	dbstatus[i]=val;
    }
    @Override
    public String get_insert() throws RemoteException{
    	return insert;
    }
    
    public int dbstatus(int i) throws RemoteException{
    	return dbstatus[i];
    }
    public int dbstatus() throws RemoteException{
    	int status = 0;
    	for (int i=0; i<4; i++) {
    		status = status + dbstatus[i];
    	}
    	status = status - dbstatus[2];

    	return status;
    }
    public String insert_container() throws RemoteException{
    	String name = "shrestha";
	      String branch = "cse";
	      int percent = i;
	      i++; 
	      String email = "shres1.gmail";
    	String insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
    	return insert;
    }
    
    
    public String insert_container1() throws RemoteException{
    	insert = "UPDATE samplermi SET percentage = " + Integer.toString(i) + " where sno = " + Integer.toString(k)+" ;";
	    return insert;
    }
    
    
    
    
    @Override
    public void notify1(int i) throws RemoteException{
    	dbstatus1[i]--;
    }
    
    public int dbstatus1(int i) throws RemoteException{
    	return dbstatus1[i];
    }
    
    @Override
    public void set_dbstatus1(int i, int val) throws RemoteException{
    	dbstatus1[i]=val;
    }
    @Override
    public int getStatus() throws RemoteException{
    	return status;
    }
    
    @Override
    public void setStatus() throws RemoteException{
    	status = 1;
    }
    public void request_write(int id, int k) throws RemoteException{
    	this.id=id;		
    	String name = "thatguy";
	      String branch = "cse";
	      int percent = i;
	      i++;
	      String email = "thatguy.gmail";
	    insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
	      
    	queue.add(insert);
    	
    	
    	
    	
    	
    	
    }
    
    public void request_update(int id, int k) throws RemoteException{
    	this.k=k;
	    insert = "UPDATE samplermi SET percentage = " + Integer.toString(i) + " where sno = " + Integer.toString(k)+" ;";
	      
    	queue.add(insert);
    	i++;
    	
    	
    	
    	
    	
    }
    public void request_write_others(String insert) throws RemoteException{
    	queue.add(insert);
    	
	      try {
   	          FileWriter logwtr = new FileWriter("Server1.log",true);
   	          BufferedWriter bw = new BufferedWriter(logwtr);
   	          PrintWriter pw = new PrintWriter(bw);
   	          System.out.println("LOGGING");

   	          pw.println(insert);
   	          
//   	          logwtr.append();
   	             pw.flush();
   	          logwtr.close();
   	          
//   	          System.out.println("Successfully wrote to the file.");
   	        } catch (IOException e) {
   	          System.out.println("An error occurred.");
   	          e.printStackTrace();
   	        }
    	
    	
    }
    
    
    
    
    
    
    
    
    public void request_update_others(String insert) throws RemoteException{
    	queue.add(insert);
    	
    }
    public Queue<String> queue() throws RemoteException{
    	return this.queue;
    }
    public void clearqueue() throws RemoteException{
    	queue.clear();
    }
    
}
