import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.*;
import java.rmi.server.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Writer extends UnicastRemoteObject implements WriteInterface {
    private String nickname;
    protected ServerInterface serverInterface;
    boolean isSafe = true;
    Queue<String> receiveQueue = new LinkedList<>(); // queue for received messages
    Queue<String> sendQueue = new LinkedList<>();    // queue for messages to be sent
    Queue<Student> sendStudentQueue = new LinkedList<>(); // queue for Student objects to be sent
    Queue<Student> receiveStudentQueue = new LinkedList<>(); // queue for received Student objects
    Queue<Request> requestQueue = new LinkedList<>(); // queue of requests.

    public Writer(String nickname, ServerInterface serverInterface)throws RemoteException{
        super();
        this.nickname = nickname;
        this.serverInterface = serverInterface;
    }
    
    public static void initialize(Writer writer) {
        try {
            Naming.rebind("rmi://localhost:5000/" + writer.nickname, writer);
        } catch(Exception e) {
            System.out.println("Failed initializing writer");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter nickname: ");
        String name = scan.next();
        Writer writer = null;
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            ServerInterface serverInterface = (ServerInterface) Naming.lookup("rmi://localhost:5000/main");
            writer = new Writer(name, serverInterface);
        } catch (Exception e) {
            System.out.println("Failed initializing writer");
        }
        if(writer == null){
            System.out.println("Try again");
            scan.close();
            return;
        }
        initialize(writer);
        writer.registerWithServer(writer.nickname);
        while(true){
            System.out.println(writer.receiveQueue);
            if(writer.receiveQueue.size()>0){
                String message = writer.receiveQueue.remove();
                if(message.equals("[controller]: begin")){
                    break;
                }
            }
        }
        Runnable writerComm = new DBWriter(writer.nickname, writer);
        executor.execute(writerComm);
        while(writer.getIsSafe()){
            Thread.sleep(200);
            if(writer.sendQueue.size()>0){
                try {
                    String message = writer.sendQueue.remove();
                    if(message.equals("stop")){
                        writer.isSafe = false;
                    }
                    writer.serverInterface.sendMessage(writer.nickname, message);
                    
				} catch (RemoteException e) {
					e.printStackTrace();
				}

            }
            if(writer.sendStudentQueue.size()>0){
                try {
                    Student student = writer.sendStudentQueue.remove();
                    System.out.println("hehehe");
                    writer.serverInterface.sendStudent(student );
                    
				} catch (RemoteException e) {
					e.printStackTrace();
				}

            }
        }
        try{
            writer.serverInterface.disconnect(writer.nickname);
        } catch(Exception e) {
            System.out.println("Failed to leave chat");
        }
        scan.close();
        executor.shutdown();
        System.exit(0);
        
    }

    public void registerWithServer(String nickname) {
        try {
            serverInterface.connect(nickname);
        } catch(Exception e) {
            System.out.println("Failed connecting " + nickname);
        }
    }

    public void messageFromServer(String message) {
        System.out.println(message);
        if(message.equals("[controller]: exit")){
            this.isSafe = false;
        }
        receiveQueue.add(message);

    }
    public void studentFromServer(Student student){
        System.out.println(student.getName());
        if(!student.getName().equals("Process "+this.nickname))
            receiveStudentQueue.add(student);
    }

    public void requestFromServer(Request request){
        requestQueue.add(request);

    }
    public Queue<String> getReceiveQueue(){
        Queue<String> temp = new LinkedList<>();
        for(int i = 0; i< receiveQueue.size();i++){
            temp.add(receiveQueue.remove());
        }
        return temp;
    }
    public Queue<Student> getReceivedStudentQueue(){
        Queue<Student> temp = new LinkedList<>();
        for(int i =0; i< receiveStudentQueue.size();i++){
            temp.add(receiveStudentQueue.remove());
        }
        return temp;
    }
    public Queue<Request> getRequestQueue(){
        Queue<Request> temp = new LinkedList<>();
        for(int i =0; i< requestQueue.size();i++){
            temp.add(requestQueue.remove());
        }
        return temp;
    }
    public void addToSendQueue(Writer w, String message){
        w.sendQueue.add(message);
    }
    public void addToStudentSendQueue(Writer w, Student s){
        w.sendStudentQueue.add(s);
    }

    public boolean getIsSafe(){
        return this.isSafe;
    }
    
}

class DBWriter implements Runnable{
    String nickname;
    Writer writer;
    Queue <String> recQueue;
    Object lock;
    Scanner scan = new Scanner(System.in);
    public DBWriter(String nName, Writer writer){
        this.nickname = nName;
        this.writer = writer;
    }
    

    public void run(){
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi"+nickname, "root", "bhanuprakash");
            Statement stmt = conn.createStatement();

            System.out.println("Writer ready");
            Random  rand = new Random();
            int t =0, x = 0, c = 0;
            boolean idExists = false;
            Thread.sleep(2000);
            String name = "Process "+nickname ;
            String branch = "cse";
            int percent = 01;
            String email = "mani.gmail";
         
            while(writer.getIsSafe()){
                Thread.sleep(5000);
                Student s = new Student();
                Request r;
	   	        t  = rand.nextInt(7); 

                switch(rand.nextInt(2)) {
                    case 1:
                        String query = "SELECT * FROM samplermi";
                        ResultSet rs = stmt.executeQuery(query);
                        while(rs.next()) {
                            if(t == rs.getInt("id")) {
                                idExists = true;
                                percent = rs.getInt("percentage")+5;
                                break;
                            }
                        }
                        s.setID(t); 
                        s.setName(name); 
                        s.setBranch(branch); 
                        s.setPercent(percent); 
                        s.setEmail(email); 
                        s.setClock(c);
                        r  = new Request("write", s);
                        writer.serverInterface.request(r);
                        break;
                    case 0:
                        x = rand.nextInt(7);
                        r = new Request("read", nickname, x);
                        writer.serverInterface.request(r);
                        break;
                    default:
                        System.out.println("NOTHING");
              }
 
                 //process the pending requests.
                 Queue<Request> temp = writer.getRequestQueue();
                if(temp != null){
                 while(temp.size() > 0) {
                    Student st;
                    System.out.println("Writer "+nickname+" processing pending requests");
                    Request req = temp.remove();
                    switch (req.getOperation()) {
                        case "write":
                            st = req.getStudent();
                            this.write(st);
                            break;
                        case "read":
                            if(req.getNickName().equals(nickname)){
                                st = this.read(req.getRow());
                            }
                            break;
                        default:
                            break;
                    }
                    
                 }
                }
                 System.out.println("WRITER "+nickname+" "+t); 
                 c++;
                 
            }
            System.out.println("Leaving... bye bye.");

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    //Read from the database

    public Student read(int t)throws Exception, ClassNotFoundException {
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi"+nickname, "root", "bhanuprakash");
	      try {
 		      FileWriter logwtr = new FileWriter("Writers.log",true);
 		      BufferedWriter bw = new BufferedWriter(logwtr);
 		      PrintWriter pw = new PrintWriter(bw);
 		      System.out.println("Logging......");

 		      pw.println("P"+this.nickname+": Entry Read id: "+t);
 	          pw.flush();
 		      logwtr.close();
 		      
 		    } catch (IOException e) {
 		      System.out.println("An error occurred.");
 		      e.printStackTrace();
 		    }
	     
	      //Execute a query 
	      System.out.println("Creating statement..."); 
	      Statement stmt = con.createStatement();  
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
	         int clock = rs.getInt("clock");
			  con.close();

		      try {
	 		      FileWriter logwtr = new FileWriter("Writers.log",true);
	 		      BufferedWriter bw = new BufferedWriter(logwtr);
	 		      PrintWriter pw = new PrintWriter(bw);
	 		      System.out.println("LOGGING");
	 		      pw.println("P"+this.nickname+": Exit Read id: "+t+ " Percent: "+percent+" Clock: "+clock);
	 	          pw.flush();
	 		      logwtr.close();
	 		      
//	 		      System.out.println("Successfully wrote to the file.");
	 		    } catch (IOException e) {
	 		      System.out.println("An error occurred.");
	 		      e.printStackTrace();
	 		    }
	         // Setting the values 
	         Student st = new Student(); 
	         st.setID(id); 
	         st.setName(name); 
	         st.setBranch(branch); 
	         st.setPercent(percent); 
	         st.setEmail(email); 

	 		 return st;
	 		 
	      	}
		      try {
	 		      FileWriter logwtr = new FileWriter("Writers.log",true);
	 		      BufferedWriter bw = new BufferedWriter(logwtr);
	 		      PrintWriter pw = new PrintWriter(bw);
                   System.out.println("LOGGING....");
	 		      pw.println("P"+this.nickname+": Exit Read id: "+t+ " Percent: "+0+" Clock: "+0);
	 	          pw.flush();
	 		      logwtr.close();
	 		      
	 		    } catch (IOException e) {
	 		      System.out.println("An error occurred.");
	 		      e.printStackTrace();
				 }
	     return null;
    }
    //Write a student into its db.

    public void write(Student s)throws Exception{
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/rmi"+nickname, "root", "bhanuprakash");

        //Execute a query 
	      System.out.println("Creating statement...");
	      
	      boolean idExists = false;
	      Statement stmt = conn.createStatement();
	      String sql = "SELECT * FROM samplermi"; 
	      ResultSet rs = stmt.executeQuery(sql);  
	      
	      int id = s.getId();
	      String name = s.getName();
	      String branch = s.getBranch();
	      int percent = s.getPercent();
	      String email = s.getEmail();
	      int clock = s.getClock();

	      int t = id % 7;
	      try {
 		      FileWriter logwtr = new FileWriter("Writers.log",true);
 		      BufferedWriter bw = new BufferedWriter(logwtr);
 		      PrintWriter pw = new PrintWriter(bw);
 		      System.out.println("Logging.....");

 		      pw.println("P"+this.nickname+": Entry Write id: "+t+" Percent: "+ percent);
 	          pw.flush();
 		      logwtr.close();
 		      
 		    } catch (IOException e) {
 		      System.out.println("An error occurred.");
 		      e.printStackTrace();
 		    }
	      // search for id in the database 
	      while(rs.next()) {
	    	  if(t == rs.getInt("id")) {
	    		  idExists = true;
	    		  break;
	    	  }
	      }
	      
          stmt = conn.createStatement();
          // If the corresponding  row is not there in the db , INSERT 
	      if (!idExists) {
		      String insert = "INSERT INTO samplermi(id, name, branch, percentage, email,clock) values('"+t+"','"+name+"','"+branch+"','"+percent+"','"+email+"',"+clock+")";
		      stmt.executeUpdate(insert);
	      }
	      else {
	    	  
	    	  String update = "UPDATE samplermi SET percentage = "+percent+", name = '"+name+"', clock = "+clock+" where id = "+t;
		      stmt.executeUpdate(update);
	      }
	      try {
 		      FileWriter logwtr = new FileWriter("Writers.log",true);
 		      BufferedWriter bw = new BufferedWriter(logwtr);
 		      PrintWriter pw = new PrintWriter(bw);
 		      System.out.println("Logging.....");

 		      pw.println("P"+this.nickname+": Exit Write id: "+t	 +" Percent: "+ percent+" Clock: "+clock);
 	          pw.flush();
 		      logwtr.close();
 		    } catch (IOException e) {
 		      System.out.println("An error occurred.");
 		      e.printStackTrace();
 		    }
	      conn.close();
      System.out.println("wrote in Writer"+this.nickname);
    }

    public void messageFromServer(String message) {
        System.out.println(message);
    }
}