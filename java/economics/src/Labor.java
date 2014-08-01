import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class Labor {

	//public static void main(String[] args)
	//{
	
	public Labor(){
	
	}
	
	public Labor(Economy E){
		Mapping m = new Mapping();
		BTree<String, Integer> dM = m.dateMap();
		
		List<Firm> laborList = new ArrayList<Firm>();
		
		String filename="C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\distributions\\tmp_l.txt";
		//Firm f;
		try{
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;   
		    int date;
		    String cus;
		    String emp;
		    String vals[];
		    str = in.readLine();    	
		    while ((str = in.readLine()) != null) {
		    	vals = str.split(",");
		    	//acquirerList.add(str.substring(0,str.length()));
		    	//System.out.println(dM.get(vals[0]) + ","+vals[1]);
		    	//System.out.println(acquirerList.get(i));
		    	//i++;
		    	cus = vals[0];
		    	date = dM.get(vals[1]);
		    	emp = vals[2];
		    	
		    	if(E.AllFirms2.get(cus) != null){
		    		System.out.println(cus+", "+date+", "+emp);
		    		laborList.add(new Firm(cus,date,emp));
		    	}
		    	else{System.out.println("NOT FOUND?");}
		    	
		    	
		    }
		    in.close();	
			
		}	catch (IOException e)
			{
			    System.out.println("Exception ");       
			}
		
		
//cusip, date, value
		Firm f;
		try {
			
			System.out.println(laborList.size());
			 String text;	
			 Writer out;
		 int idx=0;
		 while(idx<laborList.size())
		 {	
			//f = new Firm();
		    f = laborList.get(idx);
		    f.BK = E.AllFirms2.get(f.cusip).BK;
		    f.MA = E.AllFirms2.get(f.cusip).MA;
		    f.GC = E.AllFirms2.get(f.cusip).GC;
		    f.TA = E.AllFirms2.get(f.cusip).TA;
		    f.AQ = E.AllFirms2.get(f.cusip).AQ;
		    text="";
		    //text+=f.cusip;
		    text+=f.dateIndex+",";
		    text+=f.labor;

		    if(f.GC == true){
		    	System.out.println("GC");
		    	out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\distributions\\GC_L.txt", true));
		    	out.append(text);
		    	out.write("\r\n");
		    	out.close();
		    }
		    if(f.BK == true){
		    	System.out.println("BK");
		    	out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\distributions\\BK_L.txt", true));
		    	out.append(text);
		    	out.write("\r\n");
		    	out.close();
		    }
		    if(f.TA == true){
		    	System.out.println("TG");
		    	out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\distributions\\TG_L.txt", true));
		    	out.append(text);
		    	out.write("\r\n");
		    	out.close();   	
		    }
		    if(f.AQ == true){
		    	System.out.println("AQ");
		    	out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\distributions\\AQ_L.txt", true));
		    	out.append(text);
		    	out.write("\r\n");
		    	out.close();   	
		    }    
		    if(f.MA == true){
		    	System.out.println("MA");
		    	out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\distributions\\MA_L.txt", true));
		    	out.append(text);
		    	out.write("\r\n");
		    	out.close();
		    }
		    
		    idx++; 
		  }
		 System.out.println("Done with Labor!"); 
		}
		catch (IOException e)
		{
		    System.out.println("Exception ");       
		}
	}		
}
