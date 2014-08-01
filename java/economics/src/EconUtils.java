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
	 BTree<String, Integer> dM2 = m2.dateMap();
	 BTree<Integer, Integer> qM2 = m2.quartermap();
		Mapping map = new Mapping();
		BTree<Integer, Integer> qtrmap = map.quartermap();
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
	
	public void writeList( String filename, String text ) throws IOException{
    	Writer out = new BufferedWriter(new FileWriter(filename, true));
    	out.append(text);
    	out.write("\r\n");
    	out.close();				
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
		Firm firm;
		ArrayList<Firm> fList;
		String[] values;
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
	        	
	        	if(E.firmTree.get(firm.cusip)==null){
	        		fList = new ArrayList<Firm>();
	        		fList.add(firm);
	        		E.firmTree.put(firm.cusip, fList);
	        	}
	        	
	        	else{
	        		E.firmTree.get(firm.cusip).add(firm);
	        		E.firmTree.put(firm.cusip, E.firmTree.get(firm.cusip));
	        	}
	        	
	        	//
	        	
	        	E.AllFirms.add(firm);	        	
	        	if(E.AllFirms2.get(firm.cusip)!= null){
	        		E.AllFirms2.get(firm.cusip).entries.add(firm);
	        	}
	        	else{
	        		E.AllFirms2.put(firm.cusip, firm);
	        	}
	        	//System.out.println(E.AllFirms2.get(firm.cusip).cusip+" now has "+(E.AllFirms2.get(firm.cusip).entries.size()+1)+" entry(s)!");
	        }
	        in.close();
	    } catch (IOException e) {
	        System.out.println("File Read Error");
	    }		
		return E;		
	}	
	
	public void writeUnconditionally(Firm f, String filename) throws IOException{
		writeList(filename, dM2.get(f.datadate)+", "+f.ppegtq + ", " + f.Tobins_Q + ", " + f.sic+ ","+qM2.get(dM2.get(f.datadate)));				
	}
	
	public boolean writeIfFound(Economy Eco, ArrayList<String> list, Firm f, String foundFile) throws IOException{

		String txt = "";
		//for(int i = 0;i<list.size();i++){
			//if found at all 
			//if ( (boolean)list.get(i).equals(f.cusip) ){
				
				
		for(int i = 0; i<Eco.bankTree.size();i++){
			if(Eco.bankTree.get(f.cusip) != null){
				//found == true if also within date range.
				
				for(int j = 0;j<Eco.bankTree.get(f.cusip).size();j++){

				//	if((Eco.bankTree.get(f.cusip).get(j).filedIndex == 0) ||
				//			Eco.bankTree.get(f.cusip).get(j).disposedIndex == 0){
						//return false;
						
				//	}
					//System.out.println("Filed index: " + Eco.bankTree.get(f.cusip).get(j).filedIndex);
					//System.out.println("Data index: " +dM2.get(f.datadate));
					//System.out.println("Disposed index: " +Eco.bankTree.get(f.cusip).get(j).disposedIndex);
					/*
					if( (Eco.bankTree.get(f.cusip).get(j).filedIndex <= dM2.get(f.datadate)) &&
					   (Eco.bankTree.get(f.cusip).get(j).disposedIndex >= dM2.get(f.datadate)) ){
						*/
					
						if(Eco.bankTree.get(f.cusip).get(j).withinBankrupcyNow(dM2.get(f.datadate)))
					//	if(withinDateRange(	dM2.get(f.datadate),
					//						Eco.bankTree.get(f.cusip).get(j).filedIndex,
					///						Eco.bankTree.get(f.cusip).get(j).disposedIndex))
						{
						txt = dM2.get(f.datadate)+", "+f.cusip+","+f.ppegtq + ", " + f.Tobins_Q + ", " + f.sic + ","+(qtrmap.get(dM2.get(f.datadate)));
						//write to file if within date filed and date disposedW
						writeList(foundFile, txt);
						return true;
					}
						
				}
				
							
			}
			//if found in range
		}
		return false;
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

}
