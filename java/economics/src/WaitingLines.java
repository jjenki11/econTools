package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public class WaitingLines {
	
	
	static ArrayList<Integer> quarters;
	
	public static ArrayList<Integer> getQuarters(){
		
		ArrayList<Integer> stuff = new ArrayList<Integer>();
		Mapping m = new Mapping();
		BTree<String, Integer> dM = m.dateMap();
		String filename = "C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\deflator_values.txt";
		String[] values;
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;    
		    str = in.readLine();  
	        while ((str = in.readLine()) != null) {
	        	values=new String[2]; 	        	
	            values = str.split(",");
	            stuff.add(dM.get(values[0]));
	        }
	        in.close();
	    } catch (IOException e) {
	        System.out.println("File Read Error");
	    } 
		
		return stuff;
	}
	
	
	public void WaitingLines(){}
	
	
	
	
	public void WaitingLine(String start, String end, String cusip){
		
		
	}
	
	
	public int findDaysAtExit(String cusip){
		
		int idx = 0;
		
		return idx;
	}
	
	
	public static void main (String[] args){
		
		quarters = getQuarters();
		for(int i = 0;i<quarters.size();i++){
			
			System.out.print(quarters.get(i) + ",");
		}
		
		
	}
}
