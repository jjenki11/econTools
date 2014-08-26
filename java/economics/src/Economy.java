import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Economy {
	
	
	public List<Firm> AllFirms;
	public BTree<String,Firm> AllFirms2;
    public EconUtils utilities = new EconUtils();
	
	public BTree<String,ArrayList<Firm>> targetTree;
	public BTree<String,ArrayList<Firm>> acquirerTree;
	public BTree<String,ArrayList<Firm>> bankruptTree;
	public BTree<String,ArrayList<Firm>> goingConcernTree;

	public BTree<String,ArrayList<Merger>> mergeTree;
	
	public BTree<Integer,ArrayList<Merger>> successfulMergerTree;
	public BTree<Integer,ArrayList<Merger>> failedMergerTree;
	
	public BTree<String,ArrayList<Merger>> tarTree;
	public BTree<String,ArrayList<Merger>> acqTree;
	public BTree<String,ArrayList<Bankrupcy>> bankTree; 
	public BTree<String,ArrayList<Firm>> firmTree;
	
	// add category specific trees and create trees for intersection of those trees
	
	//public BTree <String,Btree> int , BTree > String, ArrayList<Firm>>> BKGCalwaysComp ;//
	
	//public BTree<String, BTree<String, BTree<String, ArrayList<Firm>>>> bigTree = new BTree<String, BTree<String, BTree<String, ArrayList<Firm>>>>();
	
	//ArrayList<Firm> firmList = bigTree.get("SIC").get("BKNOW").get("1");
	
	//public Btree <String, withinBanrupcyNow > ;
	//public void Bankrupcy(BTree<String, ArrayList<Firm>> bt1){
		
		
		
	//}
	
	public BTree<String,ArrayList<Firm>> sicTree;
	
	public BTree<String,ArrayList<Firm>> categoryTree;
	
	public BTree<Integer,ArrayList<Firm>> quarterTree;
	
	
	
//	Describe data in terms of bk, tgt, acq, gc
		int goingconcernCount = 0;
		int targetCount = 0;
		int acquirerCount = 0;
		int bankruptCount = 0;	
		Mapping m = new Mapping();
		BTree<String, Integer> dM = m.dateMap();	
	
	public Economy(){

		AllFirms = new ArrayList<Firm>(); //All CRSP entries
		AllFirms2 = new BTree<String,Firm>(); //
		
		firmTree = new BTree<String,ArrayList<Firm>>();		
		
		bankTree = new BTree<String,ArrayList<Bankrupcy>>();		
		mergeTree = new BTree<String,ArrayList<Merger>>();
		
		successfulMergerTree = new BTree<Integer,ArrayList<Merger>>();
		failedMergerTree = new BTree<Integer,ArrayList<Merger>>();

		tarTree = new BTree<String,ArrayList<Merger>>();
		acqTree = new BTree<String,ArrayList<Merger>>();

		bankruptTree = new BTree<String,ArrayList<Firm>>();
		targetTree = new BTree<String,ArrayList<Firm>>();
		acquirerTree = new BTree<String,ArrayList<Firm>>();		
		goingConcernTree = new BTree<String,ArrayList<Firm>>();		
		sicTree = new BTree<String,ArrayList<Firm>>();
		categoryTree = new BTree<String,ArrayList<Firm>>();
		quarterTree = new BTree<Integer,ArrayList<Firm>>();
		
	}
	
	public ArrayList<String> setupTarget(String filename){
		ArrayList<String> tList = new ArrayList<String>();
		
		
		return tList;		
		
	}
	
	public ArrayList<String> setupAcquirer(String filename){
		ArrayList<String> aList = new ArrayList<String>();
		
		
		return aList;
	}
	
	public void doTarget(String filename, String cusips){
		BTree<String,ArrayList<Merger>> treeTarget = new BTree<String,ArrayList<Merger>>();
		//int INDEX = 0;		
		int index =0;
		ArrayList<String> tcusips = utilities.readList(cusips);
		
		Merger b;
	 	String text="";		
	 	//Writer out;
		Mapping m = new Mapping();
		BTree<String, Integer> dM = m.dateMap();	
		String[] values = new String[4];
		//read in successful mergers
		//String filename = "C:\\Users\\jeff\\Desktop\\econ_project\\sdc_processing\\m_a_data_success.txt";
		//i = 0;
		ArrayList<Merger> l;	
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;    
		    str = in.readLine();  
		    
		    while ((str = in.readLine()) != null) 
		    {	        	
	            values = str.split(",");	 
	            
	            b = new Merger();
	            b.acquirercusip = values[0];
	            b.targetcusip = values[1];
	            b.dateAnnounced = values[2];
	            b.dateEffective = values[3];
	            
	            b.merge = "yes";
	            if(dM.get(b.dateAnnounced) == null){
	            	b.announcedIndex = -1;
	            }
	            else{
	            	b.announcedIndex = dM.get(b.dateAnnounced);
	            }
	            if(dM.get(b.dateEffective) == null){
	            	b.effectiveIndex = -1;
	            	b.daysIn = 10957-b.announcedIndex;
	            }
	            else{
	            	 b.effectiveIndex = dM.get(b.dateEffective);
	            	 b.daysIn = b.effectiveIndex - b.announcedIndex;
	            }            
	            
	            text = b.targetcusip+","+b.acquirercusip+","+b.daysIn+","+b.merge+","+b.announcedIndex+","+b.effectiveIndex;
	            
	            for(int i=0;i<tcusips.size();i++){
			        if(tcusips.get(i) == b.targetcusip){
		            	treeTarget.get(b.targetcusip).add(b);
		            	//System.out.println("FOUND TARGET DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
		            	//System.out.println("Target FIRM: "+b.targetcusip+" has "+treeTarget.get(b.targetcusip).size()+" entries.");
		            	targetCount++;
		            }
		            else if(treeTarget.get(b.targetcusip) == null){
		            	l = new ArrayList<Merger>();
		            	l.add(b);
		            	treeTarget.put(b.targetcusip, l);
		            	//System.out.println("NEW TARGET DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
		            	//System.out.println("NEW TGT: "+b.targetcusip);
		            	targetCount++;
		            }  
	            }
		            index++;
		    }
		    in.close();
			//tarTree = treeTarget;
			//acqTree = treeAcquirer;
		} catch (IOException e) {
		    System.out.println("File Read Error");
		}	    
		setTGTree(treeTarget);
		System.out.println("Reading successful target data: done!");		
	}
	
	public void doAcquirer(String filename, String cusips){
		
		BTree<String,ArrayList<Merger>> treeAcquirer = new BTree<String,ArrayList<Merger>>();
		ArrayList<String> acquirers = setupAcquirer(cusips);
		int INDEX = 0;	
		ArrayList<String> acusips = utilities.readList(cusips);
		Merger b;
	 	String text="";		
	 	//Writer out;
		Mapping m = new Mapping();
		BTree<String, Integer> dM = m.dateMap();	
		String[] values = new String[4];
		//read in successful mergers
		//String filename = "C:\\Users\\jeff\\Desktop\\econ_project\\sdc_processing\\m_a_data_success.txt";
		//i = 0;
		ArrayList<Merger> l;	
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;    
		    str = in.readLine();  
		    
		    while ((str = in.readLine()) != null) 
		    {	        	
	            values = str.split(",");	 
	            
	            b = new Merger();
	            b.acquirercusip = values[0].substring(values[0].length());
	            b.targetcusip = values[1].substring(values[1].length());
	            b.dateAnnounced = values[2];
	            b.dateEffective = values[3];
	            
	            b.merge = "yes";
	            if(dM.get(b.dateAnnounced) == null){
	            	b.announcedIndex = -1;
	            }
	            else{
	            	b.announcedIndex = dM.get(b.dateAnnounced);
	            }
	            if(dM.get(b.dateEffective) == null){
	            	b.effectiveIndex = -1;
	            	b.daysIn = 10957-b.announcedIndex;
	            }
	            else{
	            	 b.effectiveIndex = dM.get(b.dateEffective);
	            	 b.daysIn = b.effectiveIndex - b.announcedIndex;
	            }
	            
	            
	           // text = b.targetcusip+","+b.acquirercusip+","+b.daysIn+","+b.merge+","+b.announcedIndex+","+b.effectiveIndex;
		            for(int i=0;i<acusips.size();i++){
			            if((acusips.get(i) == b.acquirercusip) || 
			            	(acusips.get(i) == b.acquirercusip)){
			            	b.acquirercusip = acusips.get(i);
			            	treeAcquirer.get(b.acquirercusip).add(b);
			            	//System.out.println("FOUND ACQ DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
			            	//System.out.println("Acquirer FIRM: "+b.acquirercusip+" has "+treeAcquirer.get(b.acquirercusip).size()+" entries.");	
			            	acquirerCount++;
			            }
			            else{
			            	l = new ArrayList<Merger>();
			            	l.add(b);
			            	treeAcquirer.put(b.acquirercusip, l);
			            	//System.out.println("NEW ACQ DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
			            	//System.out.println("NEW ACQ: "+b.acquirercusip);
			            	acquirerCount++;
			            }		            	
		            }

		            /*
		            if(type.equals("S")){
		            	l = new ArrayList<Merger>();
		            	l.add(b);
		            	getSuccessMATree().put(INDEX, l);		            	
		            } */  
		            INDEX++;
		    }
		    in.close();
			//tarTree = treeTarget;
			//acqTree = treeAcquirer;
		} catch (IOException e) {
		    System.out.println("File Read Error");
		}	    
		setAQTree(treeAcquirer);
		System.out.println("Reading successful target data: done!");		
	}
    /*	
	public void doMerger(String filename)
	{
		


		
		int INDEX = 0;
			

		
		Merger b;
	 	String text="";		
	 	//Writer out;
		Mapping m = new Mapping();
		BTree<String, Integer> dM = m.dateMap();	
		String[] values = new String[4];
		//read in successful mergers
		//String filename = "C:\\Users\\jeff\\Desktop\\econ_project\\sdc_processing\\m_a_data_success.txt";
		//i = 0;
		ArrayList<Merger> l;	
		
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;    
		    str = in.readLine();  
		    
		    while ((str = in.readLine()) != null) 
		    {	        	
	            values = str.split(",");	 
	            
	            b = new Merger();
	            b.acquirercusip = values[0];
	            b.targetcusip = values[1];
	            b.dateAnnounced = values[2];
	            b.dateEffective = values[3];
	            
	            b.merge = "yes";
	            if(dM.get(b.dateAnnounced) == null){
	            	b.announcedIndex = -1;
	            }
	            else{
	            	b.announcedIndex = dM.get(b.dateAnnounced);
	            }
	            if(dM.get(b.dateEffective) == null){
	            	b.effectiveIndex = -1;
	            	b.daysIn = 10957-b.announcedIndex;
	            }
	            else{
	            	 b.effectiveIndex = dM.get(b.dateEffective);
	            	 b.daysIn = b.effectiveIndex - b.announcedIndex;
	            }
	            
	            
	            text = b.targetcusip+","+b.acquirercusip+","+b.daysIn+","+b.merge+","+b.announcedIndex+","+b.effectiveIndex;
	            
		            if(treeTarget.get(b.targetcusip) != null){
		            	treeTarget.get(b.targetcusip).add(b);
		            	System.out.println("FOUND TARGET DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
		            	//System.out.println("Target FIRM: "+b.targetcusip+" has "+treeTarget.get(b.targetcusip).size()+" entries.");
		            	targetCount++;
		            }
		            else if(treeTarget.get(b.targetcusip) == null){
		            	l = new ArrayList<Merger>();
		            	l.add(b);
		            	treeTarget.put(b.targetcusip, l);
		            	System.out.println("NEW DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
		            	//System.out.println("NEW TGT: "+b.targetcusip);
		            	targetCount++;
		            }
		            
		            if(treeAcquirer.get(b.acquirercusip) != null){
		            	treeAcquirer.get(b.acquirercusip).add(b);
		            	System.out.println("FOUND ACQ DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
		            	//System.out.println("Acquirer FIRM: "+b.acquirercusip+" has "+treeAcquirer.get(b.acquirercusip).size()+" entries.");	
		            	acquirerCount++;
		            }
		            else if(treeAcquirer.get(b.acquirercusip) == null){
		            	l = new ArrayList<Merger>();
		            	l.add(b);
		            	treeAcquirer.put(b.acquirercusip, l);
		            	System.out.println("NEW DATE ANNOUNCED: "+b.dateAnnounced +" | INDEX FOUND: "+dM.get(b.dateAnnounced));	 
		            	//System.out.println("NEW ACQ: "+b.acquirercusip);
		            	acquirerCount++;
		            }
		            INDEX++;
		    }
		    in.close();
			//tarTree = treeTarget;
			//acqTree = treeAcquirer;
		} catch (IOException e) {
		    System.out.println("File Read Error");
		}	    
		setTGTree(treeTarget);
		setAQTree(treeAcquirer);
		System.out.println("Reading successful merger data: done!");
	}	
	
	
	*/
	public void doBankrupcy(String filename)
	{
		
		
//		Read in bankrupcy
		BTree<String,ArrayList<Bankrupcy>> tree = new BTree<String,ArrayList<Bankrupcy>>();
		Bankrupcy b;
	 	String text="";		

		String[] v = new String[26];
		

		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;    
		    str = in.readLine();  

		    while ((str = in.readLine()) != null) 
		    {	        	
	            v = str.split(",");	 
	            b = new Bankrupcy(
	            		v[0],v[1],v[2],v[3],v[4],v[5],v[6],v[7],v[8],v[9],v[10],Integer.parseInt(v[11]),
	            		Integer.parseInt(v[12]),v[13],v[14],v[15],v[16],v[17],v[18],Double.parseDouble(v[19]),
	            		v[20],Integer.parseInt(v[21]),v[22],Integer.parseInt(v[23]),Integer.parseInt(v[24]),v[25]
	            		);
	           
	            if(dM.get(b.date363sale) == null){
	            	b._363Index = -1;	            	
	            }
	            else{
	            	b._363Index = dM.get(b.date363sale);
	            }
	            if(dM.get(b.dateConfirmed) == null){
	            	b.confirmedIndex = -1;
	            }
	            else{
	            	b.confirmedIndex = dM.get(b.dateConfirmed);
	            }
	            if(dM.get(b.dateConvDismiss) == null){
	            	b.convDismissedIndex = -1;
	            }
	            else{
	            	b.convDismissedIndex = dM.get(b.dateConvDismiss);
	            }
	            if(dM.get(b.dateEffective) == null){
	            	b.effectiveIndex = -1;
	            }
	            else{
	            	b.effectiveIndex = dM.get(b.dateEffective);
	            }
	            if(dM.get(b.dateEmerging) == null){
	            	b.emergingIndex = -1;
	            }
	            else{
	            	b.emergingIndex = dM.get(b.dateEmerging);
	            }
	            
	            if(dM.get(b.dateFiled) == null){
	            	b.filedIndex = -1;
	            }
	            else{
	            	///System.out.println(b.dateFiled+" | INDEX FOUND: "+dM.get(b.dateFiled));	            	
	            	b.filedIndex = dM.get(b.dateFiled);
	            }
	            if(dM.get(b.dateDisposed) == null){
	            	b.disposedIndex = -1;
	            }
	            else{
	            	 b.disposedIndex = dM.get(b.dateDisposed);
	            }
	            /*
	            String text2 ="";
	            text2= b.cusip+","+b.SIC+","+b.chapter+","+b.daysIn+","+b.filedIndex+","+b.effectiveIndex;
	        	 text=	b.XNameCorp+","+
	        			 b.CikBefore+","+
	        			 b.CikEmerging+","+
	        			 b.cusip+","+
	        			 b.date363sale+","+
	        			 b.dateConfirmed+","+
	        			 b.dateConvDismiss+","+
	        			 b.dateDisposed+","+
	        			 b.dateEffective+","+
	        			 b.dateEmerging+","+
	        			 b.dateFiled+","+
	        			 b.daysTo363+","+
	        			 b.daysToRefile+","+
	        			 b.emerge+","+
	        			 b.gvKeyBefore+","+
	        			 b.gvKeyEmerging+","+
	        			 b.nameEmerging+","+
	        			 b.refile+","+
	        			 b.sale363+","+
	        			 b.salesCurrDollar+","+
	        			 b.SIC+","+
	        			 b.chapter+","+
	        			 b.confirmed+","+
	        			 b.daysIn+","+
	        			 b.NumEmployedBefore+","+
	        			 b.prepackaged+","+
	        			 b._363Index+","+
	        			 b.confirmedIndex+","+
	        			 b.convDismissedIndex+","+
	        			 b.effectiveIndex+","+
	        			 b.emergingIndex+","+
	        			 b.filedIndex+","+
	        			 b.disposedIndex;    */
	        	 //System.out.println(text);
	            
	            if(tree.get(b.cusip) != null){ //the cusip has a prior entry
	            	tree.get(b.cusip).add(b); // so add the new entry to the bk tree

	            	
	            	//System.out.println("BK FIRM: "+b.cusip+" has "+tree.get(b.cusip).size()+" entries.");	
	            	bankruptCount++;
	            }
	            else{ //we havent seen this cusip yet
	            	ArrayList<Bankrupcy> l = new ArrayList<Bankrupcy>(); //make a new list to insert with the cusip
	            	l.add(b);	  	//add the bk to the new list
	            	tree.put(b.cusip, l);	//put <cusip, ArrayList<Bankrupcy>> key,value pair into tree
	            	//System.out.println("NEW BK");
	            	bankruptCount++;
	            }
	            
	            System.out.println(b.filedIndex);
	           // BT.put(b.cusip, )
		    	//bankrupcyList.add(b);
		    }		    
		    in.close();		    
		} catch (IOException e) {
		    System.out.println("File Read Error");
		}	    
		System.out.println("Reading bankrupcy data: done!");
		
		setBKTree(tree); //this sets the 
	}	
	
	public BTree<Integer,ArrayList<Merger>> getSuccessMATree(){
		return successfulMergerTree;
	}		
	public void setSuccessMATree(BTree<Integer,ArrayList<Merger>> t){
		successfulMergerTree = t;
	}	
	public BTree<String,ArrayList<Merger>> getFailMATree(){
		return mergeTree;
	}
	public void setBKTree(BTree<String,ArrayList<Bankrupcy>> bt){
		bankTree = bt;
	}
	public BTree<String,ArrayList<Merger>> getMATree(){
		return mergeTree;
	}
	
	public void setTGTree(BTree<String,ArrayList<Merger>> f){
	
		tarTree = f;
	}
	public void setAQTree(BTree<String,ArrayList<Merger>> f){
		acqTree = f;
	}
	
}
