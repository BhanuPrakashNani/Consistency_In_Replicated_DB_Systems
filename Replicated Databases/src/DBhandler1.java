
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

import com.mysql.cj.protocol.Resultset;


public class DBhandler1 {
	static int isUpdated = 0;
	public  ResultSet read() throws ClassNotFoundException {
	      try {
	    	 Class.forName("com.mysql.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:3306/db1", "root", "asdf;lkj");
	               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
	         
	         Statement stmt = conn.createStatement();
	         String select = "select * from users";
	         ResultSet result = stmt.executeQuery(select);
	         System.out.println("The data in the DB");
//	         while(result.next()){
//	             //Retrieve by column name
//	             int sno  = result.getInt("sno");
//	             String name = result.getString("name");
//
//	             //Display values
//	             System.out.print("sno: " + sno);
//	             System.out.println(", Name: " + name);
//	          }
	         System.out.println(result);
	         return result;
	      }
	      
	      catch (SQLException e){
	    	  e.printStackTrace();
	    	  System.out.println("SQLException: " + e.getMessage());
	    	  System.out.println("SQLState: " + e.getSQLState());
	    	  System.out.println("VendorError: " + e.getErrorCode());
	      }
		return null;
	}
	
	public  boolean write(long l, String name) throws ClassNotFoundException {
	      try {
	    	 Class.forName("com.mysql.jdbc.Driver");
	         Connection conn = DriverManager.getConnection(
	               "jdbc:mysql://localhost:3306/db1", "root", "asdf;lkj");
	               // MySQL: "jdbc:mysql://hostname:port/databaseName", "username", "password"
	         
	         Statement stmt = conn.createStatement();
	         String insert = "INSERT INTO users(sno, name) values('"+l+"','"+name+"')";
	         isUpdated++;
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
