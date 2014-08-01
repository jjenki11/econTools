import java.util.*;
import java.io.*;

public class TobinsQ 
{
	static String 	cusip="";
	static int 	datadate = 0;
	float 	cshprq = 0;
	float 	prccq = 0;
	float 	deflator = 0;
	float 	Tobins_Q = 0;
	float 	Profitability = 0;
	float 	Market_value_equity = 0;
	float	Equity_book_value = 0;
	
	public static void main(String[] args)
	{
//		Read in bankrupcy
		Bankrupcy b;
	 	String text="";		
	 	Writer out;
		//Mapping m = new Mapping();
		//BTree<String, Integer> dM = m.dateMap();	
	String[] values = new String[9];
	String filename = "C:\\Users\\jeff\\Desktop\\econ_project\\june_2013\\ECON PAPER\\output\\TobinsQ\\bankrupcy_reduced_old.txt";
	//i = 0;
	try {
	    BufferedReader in = new BufferedReader(new FileReader(filename));
	    String str;    
	    str = in.readLine();  
	    int firmCounter = 0;
    	ArrayList<String> cusipList = new ArrayList<String>();

	    while ((str = in.readLine()) != null) 
	    {	        	
            values = str.split(",");	            
            
            if(!cusipList.contains(values[0]))
            {            	
            	firmCounter++;
            	cusipList.add(values[0]);
            	
            }
            
            
            //cusipList.add(values[0]);
            text = firmCounter+","+values[1]+","+values[5];
            //if(values[1]!=null){
            out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\june_2013\\ECON PAPER\\output\\bk_tobins_q.txt", true));
	    	out.append(text);
	    	out.write("\r\n");
	    	out.close();
            //}
            //else{}
	    }
	    in.close();
	} catch (IOException e) {
	    System.out.println("File Read Error");
	}	    
	System.out.println("Reading bk tobins q data: done!");
}
	
	
}
