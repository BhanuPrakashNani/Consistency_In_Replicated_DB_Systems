import java.sql.ResultSet;
import java.sql.SQLException;

public class Threadreplicator{
    static DBhandler1 DBH1 = new DBhandler1();
    static DBhandler2 DBH2 = new DBhandler2();   

    
    public static void replicate() throws ClassNotFoundException, SQLException {
    	ResultSet read_input = DBH1.read();
		System.out.println("Replicating");
		int sno=0;
		String name="";
		if(!read_input.next()) {
			System.out.println("empty");
		}
		
        System.out.println("UPDATEd "+DBhandler1.isUpdated);

		if(DBhandler1.isUpdated == 0) {
			System.out.println("Empty");
		}
		else {
			while(DBhandler1.isUpdated >0) {
				while(read_input.next()) {
				sno  = read_input.getInt("sno");
	             name = read_input.getString("name");
				}
				DBH2.write(sno, name);
				DBhandler1.isUpdated--;
				System.out.println("Replicating ra");
			}
	
		}
    }
    
    public static void write(long sno, String name) throws ClassNotFoundException {
    	 System.out.println ("Thread " + 
                 Thread.currentThread().getId() + 
                 " is running"); 
           DBH1.write(sno, name);
    }
}
