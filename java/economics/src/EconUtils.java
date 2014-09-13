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
	String mapFile = "C:\\Users\\Rutger\\Desktop\\ECON REPO\\econTools\\java\\economics\\src\\quarters.txt";
	 Mapping m2= new Mapping();
	 BTree<String, Integer> dM2 = m2.dateMap();
	 BTree<Integer, Integer> qM2 = m2.quartermap(mapFile);
		Mapping map = new Mapping();
		BTree<Integer, Integer> qtrmap = map.quartermap(mapFile);
 	 Economy utilEcon;
	public EconUtils(){
		System.out.println("econ utils made");
		//qM2 = m2.quartermap();
		//dM2 = m2.dateMap();
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
		
/*
 * 	public BTree<String,ArrayList<Firm>> targetTree;
	public BTree<String,ArrayList<Firm>> acquirerTree;
	public BTree<String,ArrayList<Firm>> bankruptTree;
	public BTree<String,ArrayList<Firm>> goingConcernTree;

	public BTree<String,ArrayList<Merger>> mergeTree;
	public BTree<Integer,ArrayList<Merger>> successfulMergerTree;
	public BTree<Integer,ArrayList<Merger>> failedMergerTree;
	public BTree<String,ArrayList<Bankrupcy>> bankTree; 
	public BTree<String,ArrayList<Firm>> firmTree;
 * 
 * 		
 */

		
		
		
		int count = 0;
		int remaining = 0 ;
		for(int j=0;j<aList.size();j++)
		{
			if(E.AllFirms2.get(aList.get(j)) != null){
				E.AllFirms2.get(aList.get(j)).BK = true;
				E.AllFirms2.get(aList.get(j)).MA = false;
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
		    System.out.println("File Read Error");
		}			
		return theList;
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
	        BufferedReader in = new BufferedReader(new FileReader(filename));
	        String str;
	        str = in.readLine();    	
	        while ((str = in.readLine()) != null) {
	        	values=new String[23]; 
	        	
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
	        	
	        	//build firm tree
	        	if(E.firmTree.get(firm.cusip)==null){
	        		fList = new ArrayList<Firm>();
	        		fList.add(firm);
	        		E.firmTree.put(firm.cusip, fList);
	        	} else{
	        		E.firmTree.get(firm.cusip).add(firm);
	        		E.firmTree.put(firm.cusip, E.firmTree.get(firm.cusip));
	        	}
	        	
	        	// build quarter tree
	        	if(E.quarterTree.get(qtrmap.get(dM2.get(firm.datadate))) == null){
	        		fList = new ArrayList<Firm>();
	        		fList.add(firm);
	        		E.quarterTree.put(qtrmap.get(dM2.get(firm.datadate)), fList);
	        		qtrs.add(qtrmap.get(dM2.get(firm.datadate)));
	        	} else {
	        		E.quarterTree.get(qtrmap.get(dM2.get(firm.datadate))).add(firm);
	        		E.quarterTree.put(qtrmap.get(dM2.get(firm.datadate)), E.quarterTree.get(qtrmap.get(dM2.get(firm.datadate))));
	        	}
	        	
	        	// build sic tree
	        	if(E.sicTree.get(firm.sic)==null){
	        		fList = new ArrayList<Firm>();
	        		fList.add(firm);
	        		E.sicTree.put(firm.sic, fList);
	        		sics.add(firm.sic);
	        	} else {
	        		E.sicTree.get(firm.sic).add(firm);
	        		E.sicTree.put(firm.sic, E.sicTree.get(firm.sic));
	        	}
	        	
	        	E.AllFirms.add(firm);	        	
	        	if(E.AllFirms2.get(firm.cusip)!= null){
	        		E.AllFirms2.get(firm.cusip).entries.add(firm);
	        	}
	        	else{
	        		E.AllFirms2.put(firm.cusip, firm);
	        	}
	        	
	        	//System.out.println(E.AllFirms2.get(firm.cusip).cusip+" now has "+(E.AllFirms2.get(firm.cusip).entries.size()+1)+" entry(s)!");
	        }
	        //print out all firms grouped by sic
	        for(int j = 0; j < sics.size(); j++){
	        	//for(int i = 0; i < E.sicTree.get(sics.get(j)).size(); i++){	        		
	        		System.out.println("Firm entry for SIC " + sics.get(j) + ": "+ E.sicTree.get(sics.get(j)).get(0).cusip);
	        	//}	
	        }
	        //print out all firms grouped by quarter
	        for(int j = 0; j < qtrs.size(); j++){
	        	//for(int i = 0; i < E.quarterTree.get(qtrs.get(j)).size(); i++){
	        		System.out.println("Firm entry for QUARTER " + qtrs.get(j) + ": "+ E.quarterTree.get(qtrs.get(j)).get(0).cusip);
	        	//}	
	        }
	        in.close();
	        //System.exit(0);
	    } catch (IOException e) {
	        System.out.println("File Read Error");
	    }		
		return E;		
	}	
	
	
	public boolean addToBeforeTree(Economy e, Firm f){
		ArrayList<Firm> tmp;
		if(e.BeforeTree.get(f.cusip) != null){
			e.BeforeTree.get(f.cusip).add(f);
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
			e.AfterTree.get(f.cusip).add(f);
		} else {
			tmp = new ArrayList<Firm>();
			tmp.add(f);
			e.AfterTree.put(f.cusip, tmp);
		}
		return true;
	}	
	
	public boolean addToDuringTree(Economy e, Firm f){
		ArrayList<Firm> tmp;
		if(e.AfterTree.get(f.cusip) != null){
			e.AfterTree.get(f.cusip).add(f);
		} else {
			tmp = new ArrayList<Firm>();
			tmp.add(f);
			e.AfterTree.put(f.cusip, tmp);
		}
		return true;
	}	
	
	
	public void writeUnconditionally(Firm f, String filename) throws IOException{
		writeList(filename, dM2.get(f.datadate)+", "+f.ppegtq + ", " + f.Tobins_Q + ", " + f.sic+ ","+qM2.get(dM2.get(f.datadate)));				
	}
	
	public int[] writeIfFound(Economy Eco, ArrayList<String> list, Firm f, String[] foundFiles) throws IOException{

		String txt = "";
		int[] count = {0,0,0,0,0};				
		ArrayList<Firm> tmp;
		//boolean[] y = null;
		boolean[] y = {false,false,false,false};
		if(Eco.bankTree.get(f.cusip) != null){
			for(int j = 0;j<Eco.bankTree.get(f.cusip).size();j++){						
				boolean[] x = Eco.bankTree.get(f.cusip).get(j).evaluateBK(dM2.get(f.datadate));
				boolean[] xx = {
					(x[0] && f.setCategory("BEFORE")),
					(x[1] && f.setCategory("DURING")),
					(x[2] && f.setCategory("AFTER")),
					(x[3] && f.setCategory("NEVER"))
				};
				txt = dM2.get(f.datadate)+", "+f.cusip+","+f.ppegtq + ", " + f.Tobins_Q + ", " + f.sic + ","+(qtrmap.get(dM2.get(f.datadate))+","+(j+1)+","+f.category);
				
				//y = {
				y[0] =	(x[0] && writeList(foundFiles[0], txt) && addToBeforeTree(Eco, f));
				y[1] =	(x[1] &&  writeList(foundFiles[1], txt) && addToDuringTree(Eco, f));
				y[2] =	(x[2] && writeList(foundFiles[2], txt) && addToAfterTree(Eco, f));
				y[3] =	(x[3] && writeList(foundFiles[4], txt));
				//};			
				//xxx = y;
				
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
					//xxx[3] = true;
					if(Eco.categoryTree.get("NEVER") != null){
						Eco.categoryTree.get("NEVER").add(f);
					} else {
						tmp = new ArrayList<Firm>();
						tmp.add(f);
						Eco.categoryTree.put("NEVER", tmp);
					}								
				} else {
					System.out.println("DONT KNOW");
				}	
				
				
			}							
		}
		for(int k = 0;k<y.length;k++){
			if(y[k])
				count[k] = 1;
		}
		return count;
	}	
	
	
	
	
	
	/*
	public void writespecificQuery(Economy Eco, ArrayList<String> list, Firm f, String[] foundFiles) throws IOException{

		String txt = "";
		int[] count = {0,0,0};				
		ArrayList<Firm> tmp;
		//boolean[] y = null;
		boolean[] y = {false,false,false};
		if(Eco."BEFORE".get(f.cusip) != null){
			for(int j = 0;j<Eco."BEFORE".get(f.cusip).size();j++){						
				boolean[] x = Eco.bankTree.get(f.cusip).get(j).evaluateBK(dM2.get(f.datadate));
				boolean[] xx = {
					(x[0] && f.setCategory("BEFORE")),
					(x[1] && f.setCategory("DURING")),
					(x[2] && f.setCategory("AFTER")),
					(x[3] && f.setCategory("NEVER"))
				};
				txt = dM2.get(f.datadate)+", "+f.cusip+","+f.ppegtq + ", " + f.Tobins_Q + ", " + f.sic + ","+(qtrmap.get(dM2.get(f.datadate))+","+(j+1)+","+f.category);
	}
		}
	}
	*/
				
	//  float  f.ppegt - averageK( per quarter sic );
	
		
	
	
	//		get all firms having f.sic and f.quarter; start with one CUSIP get SIC and write out file for all the quarters it has to find the average of the SIC for
	/*		if(Eco.DuringTree.get(f.cusip) != null){
			for(int j = 0;j<Eco.DuringTree.get(f.cusip).get(f.sic).get(f.quarter).size();j++) {
			return perCusipSicquartersList;
			
			for(int j =0; j<Eco.GCalwaysTree.get(f.quarter).size();j++) {
			return sicGCalwaysList;
			
			
			.sicTree.get(f.sic).get(f.quarter).size();j++){	
			
			}
			public ArrayList<firm> sicFirmList string (filename)
	 * 		ArrayList<firm> sicFirmList = Econ.sicTree.get(f.sic);
	 * 		return sicFirmList ; 
	 *		}
	 *		public ArrayList<firm> sicFirmList string (filename)
	 * 		ArrayList<firm> quarterFirmSICList = Econ.sicTree.get(f.sic).get(f.quarter);
	 * 		return quarterFirmSICList;
	 * 
	 * 		for(int i = 0; i<sicFirmList.size();i++){
	 * 			if(sicFirmList.get(i).quarter == f.quarter){
	 * 				quarterFirmSICList.add(sicFirmList.get(i));	
	 * 			}
	 * 		}
	 * 
	 * 		float averageSicAtQuarter = averageK(quarterFirmList);
	 * 		
	 * 		float differenceFromSic = f.ppegt - averageSicAtQuarter;
	 * 
	 * 
	 * 		
	 * 
	 */
	//	 f.ppegt - averageK( within BK all firms );
	
	
	
	
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
		return (x / list.size());		
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
				tmp =  "CUSIP: "+f.cusip+" Date: "+dM2.get(f.datadate)+" State: "+f.category+"\n";
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
		
		String fname = "C:\\Users\\Rutger\\Desktop\\ECON REPO\\econTools\\java\\economics\\src\\results\\Transitions.txt";
		
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
							tmp = "CUSIP: "+firm.cusip+" Date: "+dM2.get(firm.datadate)+" Quarter: "+qM2.get(dM2.get(firm.datadate)) + " State: "+firm.category;
							//System.out.println(txt+tmp);
							retVal += (txt+tmp+"\n");
							//try {
								//writeList(fname,(txt+tmp));
							//} catch (IOException e) {
							//	System.out.println("BAD FILENAME IN FIRM TRANSITION OBJECT");
							//	e.printStackTrace();
							//}
						}
						txt = "";				
					}
				}				

			}
		}
		
		return retVal;
		
	}

	
	
}
