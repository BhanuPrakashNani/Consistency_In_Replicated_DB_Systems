import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class container1 implements hello{
	static int status;
	int[] dbstatus = new int[]{ 0, 0, 0, 0};
	int[] dbstatus1 = new int[]{ 0, 0, 0, 0};
	String msg =",";
	String insert;
	 int i=0;
	 int id=0;
	 int l=-100;
	 int k=0;
	public List<Student> getStudents() throws Exception, ClassNotFoundException {  
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
	      String sql = "SELECT * FROM samplermi where sno =" + Integer.toString(k) + ";"; 
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
	      conn.close();
	      return list;     

	      }
	public void addStudent(int id,int k)throws Exception, ClassNotFoundException {
		String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
	      try {
	    	  Class.forName(JDBC_DRIVER); 
	    	  this.id=id;
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
	     
	      
	      String name = "Mani";
	      String branch = "cse";
	      int percent = i;
	      i++;
	      String email = "mani.gmail";
	      
	      stmt = conn.createStatement();
	      //ResultSet rs = stmt.executeQuery(sql);  
	      insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
	      int count=stmt.executeUpdate(insert);

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
	     

	      l--;
	      stmt = conn.createStatement();
	      //ResultSet rs = stmt.executeQuery(sql);  
	      insert = "UPDATE samplermi SET percentage = " + Integer.toString(l) + " where sno = " + Integer.toString(k)+" ;";
	      int count=stmt.executeUpdate(insert);

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

    
    
    public String insert_container() throws RemoteException{
    	
    	String name = "Mani";
	      String branch = "cse";
	      int percent = i;
	      i++;
	      String email = "mani1.gmail";
    	String insert = "INSERT INTO samplermi(sno, name, branch, percentage, email) values('"+id+"','"+name+"','"+branch+"','"+percent+"','"+email+"')";
    	return insert;
    }
    public String insert_container1() throws RemoteException{
    	

    	String insert = "UPDATE samplermi SET percentage = " + Integer.toString(l) + " where sno = " + Integer.toString(k)+" ;";
    	return insert;
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
    
    public int dbstatus(int i) throws RemoteException{
    	return dbstatus[i];
    }
    
    @Override
    public void set_dbstatus(int i, int val) throws RemoteException{
    	dbstatus[i]=val;
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
    public String get_insert() throws RemoteException{
    	return insert;
    }
    public int dbstatus() throws RemoteException{
    	int status = 0;
    	for (int i=0; i<4; i++) {
    		status = status + dbstatus[i];
    	}
    	status = status - dbstatus[2];

    	return status;
    }
    
    public int getStatus() throws RemoteException{
    	return status;
    }
    public void setStatus() throws RemoteException{
    	status = 1;
    }
    
}
