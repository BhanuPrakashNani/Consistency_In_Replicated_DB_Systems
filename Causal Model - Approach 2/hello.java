import java.rmi.Remote; 
import java.rmi.RemoteException; 
import java.util.*;

public interface hello extends Remote{
	 public List<Student> getStudents() throws Exception;
	 public void addStudent(int id,int k) throws Exception;
	 public void updateStudent(int id,int k) throws Exception;
	void sendMessage(String s) throws RemoteException;
	public int dbstatus(int i) throws RemoteException;
	public int dbstatus() throws RemoteException;
	public int dbstatus1(int i) throws RemoteException;
	String getMessage() throws RemoteException;
	public void notify(int i) throws RemoteException ;
	public void notify1(int i) throws RemoteException ;
	public int getStatus() throws RemoteException;
	public void setStatus() throws RemoteException;
	public void set_dbstatus(int i, int val) throws RemoteException;
	public void set_dbstatus1(int i, int val) throws RemoteException;
	public String get_insert() throws RemoteException;
	public String insert_container() throws RemoteException;
	public String insert_container1() throws RemoteException;
}
	