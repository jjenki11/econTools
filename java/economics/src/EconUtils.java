package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class EconUtils 
{
	Mapping m2= new Mapping();
	String filePath;
	BTree<String, Integer> dM2;
	BTree<Integer, Integer> qM2;
	Mapping map = new Mapping();
	BTree<Integer, Integer> qtrmap;
	Economy utilEcon;
	
	public EconUtils(String path){
		System.out.println("econ utils made");
		 filePath = path;
		 dM2 = m2.dateMap(path);
		 qM2 = m2.quartermap(path);
		 qtrmap = m2.quartermap(path);
	};
	
	// within data range checker
	public boolean withinDateRange(int data, int start, int end){
		
		return ((data>=start) && (data<=end));
	}
	
	// quarter from datadate retrieval (txt file)
	public int getQuarterIndex(BTree<Integer, Integer> quarters, int datadate){

		return quarters.get(datadate);
	}
	
	public int multiply(int x, int y){
		return (x*y);
	}
	
	public int[] countElements(Economy E, ArrayList<String> aList){

		int count = 0;
		int remaining = 0 ;
		for(int j=0;j<aList.size();j++)
		{
			if(E.AllFirms2.get(aList.get(j)) != null){
				E.AllFirms2.get(aList.get(j)).BK = true;
				E.AllFirms2.get(aList.get(j)).GC = false;
				count++;
			}
			else{
				remaining++;
				if(E.AllFirms2.get(aList.get(j))==null){
					System.out.println("not found");
				}
				else{
					E.AllFirms2.get(aList.get(j)).GC = true;
					remaining++;
				}
			}
		}		
		int[] counts = new int[2];
		counts[0] = count;
		counts[1] = remaining;
		return counts;
	}
	
	/*
	 * A sample arff file format
	 * 
	 * @relation your_relation
	 * 
	 * @ATTRIBUTE Numeric1 NUMERIC
	 * @ATTRIBUTE Numeric2 NUMERIC
	 * @ATTRIBUTE class {Label1,Label2}
	 * 
	 * @DATA
	 * 0.154763579,-0.000941792,Label1
	 * -0.000941792,0.154763579,Label2
	 * ... 
	 * 
	 *  Note: String[][] types 
	 *  	String[0][i] is the name of the ith attribute
	 *  	String[1][i] is the data type of the ith attribute
	 * 
	 */
	
	public void writeToARFFFile(String values, String name) throws IOException
	{
		String filename = filePath+"results2\\"+name+".arff";		
		String txt = "";
		txt += values;
		//write data lines
		writeList(filename,txt);
	}
	
	public void constructARFFFile(String name, String[][] types) throws IOException
	{
		String txt = "@relation "+name;	String filename = filePath+"results\\"+name+".arff";
		txt+="\r\n";
		//write line one
		writeList(filename, txt);
		
		//construct attribute strings
		txt = "";
		for(int i = 0; i < types[0].length; i++)
		{
			txt += "@attribute " + types[0][i] + " " + types[1][i] + "\r\n";
		}
		txt += "\r\n";
		//write attribute lines
		writeList(filename, txt);
		
		//write one line to prepare for the data dump!
		txt = "@data\r\n";
		writeList(filename, txt);
	}
	
	public boolean writeList( String filename, String text ) throws IOException{
    	Writer out = new BufferedWriter(new FileWriter(filename, true));
    	out.append(text);
    	out.write("\r\n");
    	out.close();	
    	return true;
	}	
	
	public ArrayList<String> readList( String filename ){
		System.out.println("file = "+filename);
		ArrayList<String> theList = new ArrayList<String>();
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;    
		    str = in.readLine();    	
		    while ((str = in.readLine()) != null) {
		    	//theList.add(str.substring(0,str.length()));
		    	theList.add(str);
		    }
		    in.close();
		} catch (IOException e) {
			System.out.println("BAD FILE WAS > " + filename);
		    System.out.println("File Read Error in writelist");
		    System.exit(0);
		}			
		return theList;
	}
	
	public String mapMonth(String month){
		String result="";
		switch(month){
			case "01": result = "jan";break;
			case "02": result = "feb";break;
			case "03": result = "mar";break;
			case "04": result = "apr";break;
			case "05": result = "may";break;
			case "06": result = "jun";break;
			case "07": result = "jul";break;
			case "08": result = "aug";break;
			case "09": result = "sep";break;
			case "10": result = "oct";break;
			case "11": result = "nov";break;
			case "12": result = "dec";break;
			default:   result = "YOURE SHIT OUTTA LUCK";break;		
		}		
		return result;
	}
	
	public String convertDateFormat(String dateIn){
		
		String year = dateIn.substring(0, 3);		
		String month = 	mapMonth(
							dateIn.substring(4,5)
						);		
		String day = dateIn.substring(6,7);		
		return (day+month+year);		
	}	
	
	public Economy readCRSP( Economy E , String filename )
	{
		System.out.println("REadcrsp");
		Firm firm = null;
		ArrayList<Firm> fList;
		String[] values;
		ArrayList<String> sics = new ArrayList<String>();
		ArrayList<Integer> qtrs = new ArrayList<Integer>();
	    try {
	    	values=new String[23];	
	        BufferedReader in = new BufferedReader(new FileReader(filename));
	        String str;
	        str = in.readLine();    	
	        while ((str = in.readLine()) != null) {
	        	
	        	        	
	            values = str.split(",");
	            
	            firm =new Firm();
	        	firm.gvkey=values[0];
	        	firm.datadate=values[1];
	        	firm.fyearq=values[2];
	        	firm.fqtr=values[3];
	        	firm.cusip=values[4];
	        	firm.cshpry=values[5];
	        	firm.txditcq=values[6];
	        	firm.sic=values[7];
	        	firm.deflator=values[8];
	        	firm.atq=values[9];        	
	        	firm.dlcq=values[10];
	        	firm.dlttq=values[11];
	        	firm.dpactq=values[12];
	        	firm.dpq=values[13];
	        	firm.oibdpq=values[14];
	        	firm.ppegtq=values[15];
	        	firm.prccq=values[16];
	        	firm.pstkq=values[17];
	        	firm.saleq=values[18];
	        	firm.Profitability=values[19];
	        	firm.Market_value_equity=values[20];
	        	firm.Equity_book_value=values[21];   
	        	firm.Tobins_Q=values[22];	
	        	
	        	//calculated in excel
	        	firm.ATO=values[23];
	        	firm.NW=values[24];
	        	
	        	//firm.ATO=calculateATO(firm);   _inApp
	        	//firm.NW=calculateNW(firm);     _inApp
	        	firm.dateIndex = dM2.get(firm.datadate);
	        	//  Since we use tobins q the correct way, please edit the line below to focus on a certain variable
	        	// I chose at first, but you should choose everything else in the list in order
	        	
	        	//  EVERYTHING COMMENTED MEANS TOBINS Q
	        	
	        	firm.Tobins_Q = firm.ATO;	            
	        	//firm.Tobins_Q = firm.oibdpq;
	        	//firm.Tobins_Q = firm.ppegtq;
	        	//firm.Tobins_Q = firm.Market_value_equity;
	        	//firm.Tobins_Q = firm.Profitability;
	        	//firm.Tobins_Q = firm.saleq;
	        	//firm.Tobins_Q = firm.prccq;
	        	
	        	
	        	if(firm.Tobins_Q == "" ||
	        	   firm.Tobins_Q == null 
	        	   || Float.parseFloat(firm.Tobins_Q) == 0.0f
	        	){}
	        	else{
	        	
		        	E.AllFirms.add(firm);	
		        	//build firm tree
		        	if(E.firmTree.get(firm.cusip)==null){
		        		fList = new ArrayList<Firm>();
		        		fList.add(firm);
		        		E.firmTree.put(firm.cusip, fList);
		        	} else{
		        		fList = E.firmTree.get(firm.cusip);
		        		fList.add(firm);
		        		E.firmTree.put(firm.cusip, E.firmTree.get(firm.cusip));
		        	}
		        	
		        	// build quarter tree
		        	if(E.quarterTree.get(qM2.get(firm.dateIndex)) == null){
		        		fList = new ArrayList<Firm>();
		        		fList.add(firm);
		        		E.quarterTree.put(qM2.get(firm.dateIndex), fList);
		        		qtrs.add(qtrmap.get(firm.dateIndex));
		        	} else {
		        		fList = E.quarterTree.get(qM2.get(firm.dateIndex));
		        		fList.add(firm);
		        		E.quarterTree.put(qM2.get(firm.dateIndex), fList);
		        	}	        	
		        	// build sic tree
		        	if(E.sicTree.get(firm.sic)==null){
		        		fList = new ArrayList<Firm>();
		        		fList.add(firm);
		        		E.sicTree.put(firm.sic, fList);
		        		sics.add(firm.sic);
		        	} else {
		        		fList = E.sicTree.get(firm.sic);
		        		fList.add(firm);
		        		E.sicTree.put(firm.sic, E.sicTree.get(firm.sic));
		        	}
		        	
		        	// build All firms linked list
		        	if(E.AllFirms2.get(firm.cusip)!= null){
		        		fList = (ArrayList<Firm>) E.AllFirms2.get(firm.cusip).entries;
		        		fList.add(firm);
		        		E.AllFirms2.put(firm.cusip, firm);		        	
		        	}
		        	else{
		        		fList = new ArrayList<Firm>();
		        		fList.add(firm);
		        		E.AllFirms2.put(firm.cusip, firm);
		        	}
	        	}
	        }
	        //print out all firms grouped by sic
	        /*
	         * TBD not sure if we need these?
		        for(int j = 0; j < sics.size(); j++){    		
		        	System.out.println("Firm entry for SIC " + sics.get(j) + ": "+ E.sicTree.get(sics.get(j)).get(0).cusip);
		        }
		        //print out all firms grouped by quarter
		        for(int j = 0; j < qtrs.size(); j++){
		        	System.out.println("Firm entry for QUARTER " + qtrs.get(j) + ": "+ E.quarterTree.get(qtrs.get(j)).get(0).cusip);
		        }
	        */
	        in.close();
	    } catch (IOException e) {
			System.out.println("BAD FILE WAS > " + filename);
		    System.out.println("File Read Error in read crsp");
		    System.exit(0);
	    }		
		return E;		
	}		
	
	public boolean addToBeforeTree(Economy e, Firm f){
		
		ArrayList<Firm> tmp;
		if(e.BeforeTree.get(f.cusip) != null){
			tmp = e.BeforeTree.get(f.cusip);
			tmp.add(f);
			e.BeforeTree.put(f.cusip, tmp);
		} else {
			tmp = new ArrayList<Firm>();
			tmp.add(f);
			e.BeforeTree.put(f.cusip, tmp);		
		}
		return true;
	}
	
	public boolean addToAfterTree(Economy e, Firm f){
		ArrayList<Firm> tmp;
		if(e.AfterTree.get(f.cusip) != null){			
			tmp = e.AfterTree.get(f.cusip);
			tmp.add(f);
			e.AfterTree.put(f.cusip, tmp);
		} else {
			tmp = new ArrayList<Firm>();
			tmp.add(f);
			e.AfterTree.put(f.cusip, tmp);
		}
		return true;
	}	
	
	public boolean addToGCTree(Economy e, Firm f){
		ArrayList<Firm> tmp;
		if(e.goingConcernTree.get(f.cusip) != null){
			tmp = e.goingConcernTree.get(f.cusip);
			tmp.add(f);
			e.goingConcernTree.put(f.cusip, tmp);
		} else {
			tmp = new ArrayList<Firm>();
			tmp.add(f);
			e.goingConcernTree.put(f.cusip, tmp);
		}		
		return true;
	}
	
	public boolean addToDuringTree(Economy e, Firm f){
		ArrayList<Firm> tmp;
		if(e.DuringTree.get(f.cusip) != null){
			tmp = e.DuringTree.get(f.cusip);
			tmp.add(f);
			e.DuringTree.put(f.cusip, tmp);
		} else {
			tmp = new ArrayList<Firm>();
			tmp.add(f);
			e.DuringTree.put(f.cusip, tmp);
		}
		return true;
	}	
	
	public ArrayList<ArrayList<Firm>> createGCRangeList(Economy e, int start, int end, String state)
	{		
		ArrayList<ArrayList<Firm>> tmp = new ArrayList<ArrayList<Firm>>();		
		ArrayList<Firm> lh = new ArrayList<Firm>();		
		for(int i = (start); i <= (end); i++){			
			lh = new ArrayList<Firm>();
			ArrayList<Firm> holster = e.quarterTree.get(i);
			if(holster==null){}
			else{			
				for(int j = 0;j < holster.size(); j++){					
					if(e.bankTree.get(holster.get(j).cusip) == null){ //found in GC tree
						lh.add(holster.get(j));
					}
					else{}					
				}			
				tmp.add(lh);
			}
		}
		return tmp;		
	}	
	
	public void writeUnconditionally(Firm f, String filename) throws IOException{
		writeList(filename, f.dateIndex+", "+f.ppegtq + ", " + f.Tobins_Q + ", " + f.sic+ ","+qM2.get(f.dateIndex));				
	}
	
	public int[] writeIfFound(Economy Eco, ArrayList<String> list, Firm f, String[] foundFiles) throws IOException{

		String txt = "";
		int[] count = {0,0,0,0,0};				
		ArrayList<Firm> tmp;
		boolean[] y = {false,false,false,false};	
		
		// checking bankrupcy for file write filtering		
		int watch = 0;
		if(Eco.bankTree.get(f.cusip) != null){
			for(int j = 0;j<Eco.bankTree.get(f.cusip).size();j++){						
				boolean[] x = Eco.bankTree.get(f.cusip).get(j).evaluateBK(f.dateIndex);
				boolean[] xx = {
					(x[0] && f.setCategory("BEFORE")),
					(x[1] && f.setCategory("DURING")),
					(x[2] && f.setCategory("AFTER")),
					(x[3] && f.setCategory("OUTSIDE"))
				};
				
				if(x[0] || x[1] || x[2]){
					f.setBankrupcy(Eco.bankTree.get(f.cusip).get(j));
				}
				
				watch = j;				
				txt = f.dateIndex+","+f.cusip+","+f.Tobins_Q+","+f.sic+","+(qM2.get(f.dateIndex)+","+(j+1)+","+f.category);
				
				y[0] =	(x[0] && writeList(foundFiles[0], txt) && addToBeforeTree(Eco, f));
				y[1] =	(x[1] && writeList(foundFiles[1], txt) && addToDuringTree(Eco, f));
				y[2] =	(x[2] && writeList(foundFiles[2], txt) && addToAfterTree( Eco, f));
				y[3] =	(x[3] && writeList(foundFiles[3], txt) && addToGCTree(    Eco, f));
				
				if(y[0]){						
					if(Eco.categoryTree.get("BEFORE") != null){
						Eco.categoryTree.get("BEFORE").add(f);
					} else {
						tmp = new ArrayList<Firm>();
						tmp.add(f);
						Eco.categoryTree.put("BEFORE", tmp);
					}
				} else if(y[1]) {						
					if(Eco.categoryTree.get("DURING") != null){
						Eco.categoryTree.get("DURING").add(f);
					} else {
						tmp = new ArrayList<Firm>();
						tmp.add(f);
						Eco.categoryTree.put("DURING", tmp);
					}
				} else if(y[2]) {						
					if(Eco.categoryTree.get("AFTER") != null){
						Eco.categoryTree.get("AFTER").add(f);
					} else {
						tmp = new ArrayList<Firm>();
						tmp.add(f);
						Eco.categoryTree.put("AFTER", tmp);
					}						
				} else if(y[3]) {				
					System.out.println("Adding an outsider...");
					//xxx[3] = true;
					if(Eco.categoryTree.get("OUTSIDE") != null){
						Eco.categoryTree.get("OUTSIDE").add(f);
					} else {
						tmp = new ArrayList<Firm>();
						tmp.add(f);
						Eco.categoryTree.put("OUTSIDE", tmp);
					}								
				} else {
					System.out.println("DONT KNOW");
				}					
			}							
		}
		//  otherwise its not in bk list duh
		else {			
			f.setCategory("NEVER");
			txt = f.dateIndex +","+f.cusip+","+f.Tobins_Q + "," + f.sic + ","+ (qM2.get(f.dateIndex)+","+(watch+1)+","+f.category);			
			writeList(foundFiles[3], txt);
			addToGCTree(Eco, f);
			if(Eco.categoryTree.get("NEVER") != null){
				
				tmp = Eco.categoryTree.get("NEVER");
				tmp.add(f);
				Eco.categoryTree.put("NEVER", tmp);
			} else {
				tmp = new ArrayList<Firm>();
				tmp.add(f);
				Eco.categoryTree.put("NEVER", tmp);
			}	
		}
		for(int k = 0;k<y.length;k++){
			if(y[k])
				count[k] = 1;
		}
		return count;
	}	
	
	public ArrayList<Float> calculateZScore(ArrayList<Float> values)
	{		
		float std = (float) Math.sqrt(varianceN(values));		
		ArrayList<Float> scores = new ArrayList<Float>();		
		for(int i = 0; i < values.size(); i++)
		{
			scores.add(
				(float) ((values.get(i) - averageN(values)) / (std))	//xbar - mu0 / std_dev*sqrt(N) gives test statistic
			);
		}		
		return scores;		
	}

	public String calculateATO(Firm f){
		
		return  ((Float) ( Float.parseFloat(f.saleq)/Float.parseFloat(f.atq) )).toString();
		
	}
	
	public String calculateNW(Firm f){
		
		return  ((Float) ( Float.parseFloat(f.atq)-Float.parseFloat(f.dlcq)- Float.parseFloat(f.dlttq) )).toString();
		
	}
	
	public float averageN(ArrayList<Float> list){
		float sum = sumN(list);
		return (sum / list.size());
	}
	
	public float varianceN(ArrayList<Float> list){
		
		float avg = averageN(list);
		ArrayList<Float> diffs = new ArrayList<Float>();
		
		for(int i = 0; i < list.size(); i++){
			diffs.add( 
					(float) Math.pow((list.get(i) - avg), 2)
			);
		}
		
		return sumN(diffs);		
	}
	
	public float sumN(ArrayList<Float> list){
		float x = 0;
		for(int i = 0; i< list.size(); i++){
			x+=(list.get(i));
		}		
		return x;
	}
	
	public float averageK(ArrayList<Firm> list){
		float x = 0;
		for(int i = 0; i< list.size(); i++){
			x+=Float.parseFloat(list.get(i).ppegtq);
		}
		return (x / list.size());		
	}

	public float averageTQ(ArrayList<Firm> list){
		float x = 0;
		for(int i = 0; i< list.size(); i++){
			x+=Float.parseFloat(list.get(i).Tobins_Q);
		}
		return (x / (list.size()));		
	}
	
	public static float averageTQList(ArrayList<Firm> list)
	{
		float res = 0;
		int badValues = 0;
		for(int i = 0;i<list.size();i++)
		{
			if(Float.isNaN(Float.parseFloat(list.get(i).Tobins_Q))){
			badValues++;
			}
			else{				
				res += Float.parseFloat(list.get(i).Tobins_Q);
			}
		}		
		return (res / (list.size() - badValues));	
	}
	
	public void setEconomy(Economy e){
		utilEcon = e;
	}
	
	public int dateFound(String date){
		if(dM2.get(date) != null){
			return (dM2.get(date));
		}
		System.out.println("BAD VALUE: "+ date);		
		return -1;
	}
	
	public String printList(ArrayList<Firm> firms){
		Firm f;
		String txt = "";
		String tmp = "";
		if(firms!=null){
			for(int i = 0;i<firms.size();i++){
				f = new Firm();
				f = firms.get(i);
				tmp =  "CUSIP: "+f.cusip+" Date: "+f.dateIndex+" State: "+f.category+"\n";
				txt += tmp;
				System.out.println(tmp);
			}			
		} else {
			System.out.println("NULL LIST ");
		}
		return txt;
	}	
	
	public void printSICTree(Economy e){
			System.out.println(e.sicTree.toString());
	}
	
	public String printFirmTransitionObject(ArrayList<ArrayList<ArrayList<Firm>>> list){
		
		String fname = filePath+"results\\Transitions.txt";
		
		Firm firm;
		String txt = "";
		String tmp = "";
		String retVal = "";
		for(int i = 0; i < list.size(); i++)
		{
			//Going thru cusip list
			
			System.out.println("\n");
			txt = "\n\n";
			for(int j = 0; j < list.get(i).size(); j++)
			{
				if(list.get(i).get(j) != null)
				{
				//Going thru 3 element (before during after) list
					for(int k = 0; k < list.get(i).get(j).size(); k++)
					{
						//Going thru each entry
						firm = new Firm();
						firm = list.get(i).get(j).get(k);
						if(firm.category != "NEVER")
						{
							tmp = "CUSIP: "+firm.cusip+" Date: "+firm.dateIndex+" Quarter: "+qM2.get(firm.dateIndex) + " State: "+firm.category;
							retVal += (txt+tmp+"\n");
						}
						txt = "";				
					}
				}
			}
		}		
		return retVal;		
	}	
}
