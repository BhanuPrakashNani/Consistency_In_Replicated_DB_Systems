import java.rmi.Remote; 
import java.rmi.RemoteException; 
import java.util.*;

public interface Hello extends Remote{
	 public List<Student> getStudents() throws Exception;  
}
	