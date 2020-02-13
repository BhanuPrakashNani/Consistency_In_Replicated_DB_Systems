import java.sql.SQLException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class Main {
	public static void main(String[] args) throws Exception {
        DBhandler2 DBH2 = new DBhandler2();
        DBhandler1 DBH1 = new DBhandler1();
		ExecutorService executor = Executors.newFixedThreadPool(2);

        @SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
//        System.out.println("Enter sno: \n");
//        int sno = sc.nextInt();
//        sc.nextLine();
//        System.out.println("Enter name: \n");
//        String name = sc.nextLine();
//        if(dao.write(sno, name))
//        {
//        	dao2.write(sno, name);
//        }
//        DBH1.write(sno, name);
//        DBH1.read();
          final StrictReplicator R = new StrictReplicator();
          Thread t1 = new Thread(new Runnable() 
          { 
              @Override
              public void run() 
              { 
                  try
                  {
                	  R.write();
                  } 
                  catch(InterruptedException | ClassNotFoundException e) 
                  { 
                      e.printStackTrace(); 
                  } 
              } 
          }); 
    
          // Create another thread object that calls 
          // pc.consume() 
          Thread t2 = new Thread(new Runnable() 
          { 
              @Override
              public void run() 
              { 
                  try
                  { 
                      R.replicate(); 
                  } 
                  catch(InterruptedException | ClassNotFoundException | SQLException e) 
                  { 
                      e.printStackTrace(); 
                  } 
              } 
          }); 
    
          // Start both threads 
          t1.start(); 
          t2.start(); 

    }
	
}
