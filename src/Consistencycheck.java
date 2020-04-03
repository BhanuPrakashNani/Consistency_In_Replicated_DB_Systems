import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

import javax.swing.plaf.synth.SynthOptionPaneUI;

import java.util.*;



public class Consistencycheck{
	public static void main(String[] args) {
		
		
		int[] vals = new int[10];
	
		
		
		
	    try {
	        File myObj = new File("process1.log");
	        Scanner myReader = new Scanner(myObj);
	        
	        while (myReader.hasNextLine()) {
	          
	          
	          String data = myReader.nextLine();
	          char[] arr = data.toCharArray();
	          
	          
	          
	          if(arr[4]=='I') {
	        	  int sno = Integer.parseInt(String.valueOf(arr[72]));//yes
	        	  String[] parts = data.split("\'");
	        	  String s = parts[7];
	        	  if(parts[3].contentEquals("bhanu")) {
	        		  vals[sno] = Integer.parseInt(String.valueOf(s));
	        	  }
	        	  
	        	  System.out.println("Done1"); 
	        	  }
	        	  
	          else if(arr[4]=='U') {
	        	  String[] parts = data.split(" ");
	        	  int sno = Integer.parseInt(String.valueOf(parts[10]));
	        	  int per = Integer.parseInt(String.valueOf(parts[6]));
	        	  vals[sno] = per;
	        	  System.out.println("Done2");
	        	  
	          }

	          else if((arr[4])=='E' && arr[5]=='x' && arr[9]=='r') {
	        	  String[] parts = data.split(" ");
	        	  	if(  String.valueOf(parts[1]).contentEquals("Exit") && String.valueOf(parts[2]).contentEquals("read") && String.valueOf(parts[5]).contentEquals("percent:")) {
	        	  		int decider = Integer.parseInt(String.valueOf(parts[6]));
	        	  		int sno = Integer.parseInt(String.valueOf(parts[4]));
	        	  		if(vals[sno]!=decider) {
	        	  			System.out.println("Error for "+ data);
	        	  			System.out.println(vals[3]	);
	        	  			return;
	        	  		}
	        	  		System.out.println("Done3");
	        	  	}
	          }
	          
	          
	          
	              
	    } 
	     System.out.println("Done");
	        
	        myReader.close();
	      } catch (FileNotFoundException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	      }
	   
		
		
		
		
		
		
	}
}