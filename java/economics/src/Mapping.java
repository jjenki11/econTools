package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Mapping {
	Economy ec;
	public Mapping(){
		
	}
	
	public BTree<String, Integer> dateMap(String fPath){
		BTree<String,Integer> dateMap =new BTree<String,Integer>();
		String[] values=new String[2];
		String filename = fPath+"dateMap.txt";
		Integer i = 0;
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;
		    str = in.readLine();   
		    
		    while ((str = in.readLine()) != null) 
		    {
		        values = str.split("	");   
		        dateMap.put((String)values[1], Integer.parseInt(values[0]));
		    	i++;
		    }
		    in.close();		    
		}
		catch (NullPointerException e){
			System.out.println("null");
		}
		catch (IOException e) 
		{
			System.out.println("BAD FILE WAS > " + fPath);
		    System.out.println("File Read Error in dateMap");
		    System.exit(0);
		}
		System.out.println("Date mapping success");
		return dateMap;
	
}
	
	public BTree<Integer, Integer> quartermap(String fname){
		BTree<Integer,Integer> dateMap =new BTree<Integer,Integer>();

		String[] values=new String[2];
		//String filename = "C:\\Users\\Jeff\\Desktop\\econTools\\java\\economics\\src\\quarters.txt";
		String filename = fname;
		Integer i = 0;
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename+"quarters.txt"));
		    String str;
		    str = in.readLine();   
		    
		    while ((str = in.readLine()) != null) 
		    {
		        values = str.split(",");   
		        dateMap.put(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		        //System.out.println(""+Integer.parseInt(values[0])+", "+ Integer.parseInt(values[1]));
		    	i++;
		    }
		    in.close();
		}
		catch (NullPointerException e){
			System.out.println("null");
		}
		catch (IOException e) 
		{
			System.out.println("BAD FILE WAS > " + filename);
		    System.out.println("File Read Error in dateMap");
		    System.exit(0);
		}
		System.out.println("Date mapping success");
		return dateMap;
	
}
	
	public BTree<String, Integer> cusipMap(String filename){
		BTree<String,Integer> cusipMap =new BTree<String,Integer>();

		String[] values=new String[2];
		//String filename = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\cusip_map.txt";
		//String filename1 = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\acquirer_data_reduced.txt";
		//String filename2 = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\bankrupcyreduced.txt";
		//String filename3 = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\target_data_reduced.txt";
		//String gcFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\Going_Concern_reduced.txt";
		Integer i = 0;
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;
		    str = in.readLine();   
		    
		    while ((str = in.readLine()) != null) 
		    {
		        values = str.split(",");   
		        cusipMap.put(((String)values[0]), Integer.parseInt(values[1]));
		    	i++;
		    }
		    in.close();		    
		}
		catch (NullPointerException e){
			System.out.println("null");
		}
		catch (IOException e) 
		{
		    System.out.println("File Read Error");
		}
		System.out.println("Cusip mapping success");
		return cusipMap;
	}
}