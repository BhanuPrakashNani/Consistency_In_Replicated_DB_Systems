import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;

public class ConsistencyTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String PStr = "P:";
        String Row = "";
        Check chk = new Check(PStr, Row);
        Thread t = new Thread(chk);
        t.start();
    }
}

class Check implements Runnable {
    String Process;
    String row;

    Check(String proc, String rw){
        this.Process = proc;
        this.row = rw;
    }

    @Override
    public void run(){
        try {
    	System.out.println("Thread for Process ==> "+Process+" or row: "+row);
        int lineno = 2;
		long lineCount = Files.lines(Paths.get("Writers.log")).count();
		String current = Files.readAllLines(Paths.get("Writers.log")).get(2);
		String previous = Files.readAllLines(Paths.get("Writers.log")).get(1);
        int[] data = new int[10];
        for (int i = 0; i < data.length; i++) {
            data[i] =0;
        }
        boolean success = true;
		while(true) {
			lineCount = Files.lines(Paths.get("Writers.log")).count();
			if (lineCount ==lineno)
			 	break;
			lineno++;
			String[] arrOfStr = current.split(" ",-1);
			if(arrOfStr[2].equals("Read") && arrOfStr[1].equals("Exit")){
                if(data[Integer.parseInt(arrOfStr[4])] != Integer.parseInt(arrOfStr[6])){
                    System.out.println("gotcha");
                    success = false;
                    break;
                }
            }
            else if(arrOfStr[2].equals("Write") && arrOfStr[1].equals("Exit")){
                data[Integer.parseInt(arrOfStr[4])] = Integer.parseInt(arrOfStr[6]);
            }
            if(lineno<lineCount)   
			current = Files.readAllLines(Paths.get("Writers.log")).get(lineno);
		}
        if(success) {
        System.out.println("Success");
        }
        else{
            System.out.println("BREAK");
            System.out.println(current);
        }

}
catch(Exception e) {
    System.out.println("An error occurred.");
    e.printStackTrace();
}
}
}