
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class replicatedDatabase {
	
	public  boolean database(int sno, String name) throws ClassNotFoundException {
	      try {
	    	 Class.forName("com.mysql.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:3306/db1", "root", "bhanuprakash");
	               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
	         
	         Statement stmt = conn.createStatement();
	         String insert = "INSERT INTO users(sno, name) values('"+sno+"','"+name+"')";
	         int count=stmt.executeUpdate(insert);
	         System.out.println(count);
	         return true;
	      }
	      
	      catch (SQLException e){
	    	  e.printStackTrace();
	    	  System.out.println("SQLException: " + e.getMessage());
	    	  System.out.println("SQLState: " + e.getSQLState());
	    	  System.out.println("VendorError: " + e.getErrorCode());
	      }
		return false;
	}
}
