import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Queue;


// ALGO:
// .....

public class ConsistencyCheck {
	public static void main(String[] args) throws IOException, InterruptedException {
		
		Thread.sleep(5000);
		int lineno = 2;
		long lineCount = Files.lines(Paths.get("Server1.log")).count();

		while(lineCount < lineno+1) {
			System.out.println("waiting to get updated");
			lineCount = Files.lines(Paths.get("Server1.log")).count();

		}
		String current = Files.readAllLines(Paths.get("Server1.log")).get(2);
		String previous = Files.readAllLines(Paths.get("Server1.log")).get(1);
		int[] data = new int[10];
		Queue<Integer> q1 = new LinkedList<>();
		Queue<Integer> q4 = new LinkedList<>();
		Queue<Integer> q2 = new LinkedList<>();
		Queue<Integer> que = new LinkedList<>();
		Queue<Integer> order = new LinkedList<>();
		que.add(0);
		while(true) {
			lineCount = Files.lines(Paths.get("Server1.log")).count();

			// while(lineCount < lineno+2) {
			// 	System.out.println("waiting to get updated");
			// 	lineCount = Files.lines(Paths.get("Server1.log")).count();

			// }
			if (lineCount < lineno +2)
				break;
			lineno++;
			String[] arrOfStr = current.split(" ",-1);

			if(arrOfStr[2].equals("Read") && arrOfStr[1].equals("Exit") && arrOfStr[4].equals("0")){
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
				if(arrOfStr[0].equals("P1:")){
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
						break;
					}
				}
				else if(arrOfStr[0].equals("P4:"))
					q4.add((Integer.parseInt(arrOfStr[6])));
				else if(arrOfStr[0].equals("P2:"))
					q2.add((Integer.parseInt(arrOfStr[6])));
				System.out.println("Queue: order " + order); 
				System.out.println("Queue: que " + que); 


				
			}
			// for (String a: arrOfStr)
			// 	System.out.println(a);
			current = Files.readAllLines(Paths.get("Server1.log")).get(lineno);
			
		}
		System.out.println("Queue: q1 " + q1); 
		System.out.println("Queue: q2 "+ q2);
		System.out.println("Queue: q4 "+ q4);

		
	}

// public static void killAllProcesses() {
// 	Config.SAFE = false;
// }
}