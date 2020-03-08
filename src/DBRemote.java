import java.rmi.Remote; 
import java.rmi.RemoteException; 
import java.util.*;

public interface DBRemote extends Remote{
	 public List<Student> getStudents() throws Exception;
	 public void addStudent(Student s) throws Exception;
	void sendMessage(String s) throws RemoteException;
	public int dbstatus(int i) throws RemoteException;
	public int dbstatus() throws RemoteException;
	public void request(Student s) throws RemoteException;
	String getMessage() throws RemoteException;
	public Queue<Student> getQobj() throws RemoteException;
	public void notify(int i) throws RemoteException ;
	public int getStatus() throws RemoteException;
	public void setStatus() throws RemoteException;
	public void setRead() throws RemoteException;
	public void setWrite() throws RemoteException;
	public boolean isWrite() throws RemoteException;
	public void setDBStatus() throws RemoteException;
	public Student read(int x) throws Exception, ClassNotFoundException;
}
	