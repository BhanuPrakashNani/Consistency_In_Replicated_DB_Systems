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
        for (int i = 1; i < 5; i++) {
            for (int j = 0; j < 7; j++) {
                String PStr = "P"+i+":";
                String Row = ""+j;
                Check chk = new Check(PStr, Row);
                Thread t = new Thread(chk);
                t.start();
            }
            
        }
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
		long lineCount;

			lineCount = Files.lines(Paths.get("Server1.log")).count();

        

//		while(lineCount < lineno+1) {
//			System.out.println("waiting to get updated");
//			lineCount = Files.lines(Paths.get("Server1.log")).count();
//
//		}
		String current = Files.readAllLines(Paths.get("Server1.log")).get(2);
		String previous = Files.readAllLines(Paths.get("Server1.log")).get(1);
		int[] data = new int[10];
		Queue<Integer> q1 = new LinkedList<>();
		Queue<Integer> q4 = new LinkedList<>();
		Queue<Integer> q2 = new LinkedList<>();
		Queue<Integer> que = new LinkedList<>();
		Queue<Integer> order = new LinkedList<>();
		Queue<Integer> qwrites = new LinkedList<>();
		que.add(0);
		while(true) {
			lineCount = Files.lines(Paths.get("Server1.log")).count();
			if(lineCount < lineno+2) {
				System.out.println("waiting to get updated");
				lineCount = Files.lines(Paths.get("Server1.log")).count();

			}
			 if (lineCount < lineno +2)
			 	break;
			lineno++;
			String[] arrOfStr = current.split(" ",-1);
//			if(arrOfStr[0].equals(Process)&&arrOfStr[2].equals("Write") && arrOfStr[1].equals("Exit") && arrOfStr[4].equals(row)){
//				boolean found = false;
//				System.out.println(arrOfStr);
//					qwrites.add(Integer.parseInt(arrOfStr[6]));
//
//			}
			if(arrOfStr[2].equals("Read") && arrOfStr[1].equals("Exit") && arrOfStr[4].equals(row)){
				System.out.println(arrOfStr[0] +"->  :(  "+arrOfStr[4]+" , "+arrOfStr[6]);
				boolean found = false;
				for (Integer item: order) {
					if(item == Integer.parseInt(arrOfStr[6])){
						found = true;
					}
				}
				if(!found){
					order.add(Integer.parseInt(arrOfStr[6]));
					que.add(Integer.parseInt(arrOfStr[6]));

				}
				if(arrOfStr[0].equals(Process)){
					q1.add(Integer.parseInt(arrOfStr[6]));

					int c =0;
					boolean f = false;
					for (Integer item: que){
						c++;
						if(item == Integer.parseInt(arrOfStr[6])){
							f = true;
							System.out.println("hehehe "+c+"  "+item);
							break;
						}
					}
					for (int i = 0; i < c-1; i++) {
						que.remove();
					}
					if(!f){
						System.out.println("BREAK");
						 try {
					            FileWriter logwtr = new FileWriter("result.txt",true);
					            BufferedWriter bw = new BufferedWriter(logwtr);
					            PrintWriter pw = new PrintWriter(bw);
					            System.out.println("LOGGIGN");
								pw.println("READ order: "+order);
					            pw.println(Process+row+" BREAK ln: "+lineno);

//						 		      logwtr.append();
					            pw.flush();
					            logwtr.close();
					            
//						 		      System.out.println("Successfully wrote to the file.");
					          } catch (IOException e) {
					            System.out.println("An error occurred.");
					            e.printStackTrace();
					          }
						break;
					}
				}

				System.out.println("Queue: order " + order); 
				System.out.println("Queue: que " + que); 


				
			}
			// for (String a: arrOfStr)
			// 	System.out.println(a);
			current = Files.readAllLines(Paths.get("Server1.log")).get(lineno);
			
		}
		System.out.println("Queue : correct q " + q1); 
        try {
            FileWriter logwtr = new FileWriter("result.txt",true);
            BufferedWriter bw = new BufferedWriter(logwtr);
            PrintWriter pw = new PrintWriter(bw);
            System.out.println("LOGGIGN");
			pw.println("READ order: "+order);
//			pw.println("writes order: "+qwrites);

            pw.println(Process+row+" SUCCESS");

//	 		      logwtr.append();
            pw.flush();
            logwtr.close();
            
//	 		      System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
    }
    catch(Exception e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
    }
}
}
