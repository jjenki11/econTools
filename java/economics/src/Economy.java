package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Economy 
{
	public String filePath;
	
	public List<Firm> AllFirms;
	public BTree<String,Firm> AllFirms2;
    public EconUtils utilities;
	
	public BTree<String,ArrayList<Firm>> bankruptTree;
	public BTree<String,ArrayList<Firm>> goingConcernTree;

	
	public BTree<String,ArrayList<Bankrupcy>> bankTree; 
	public BTree<String,ArrayList<Firm>> firmTree;
	public BTree<String,ArrayList<Firm>> BeforeTree;
	public BTree<String,ArrayList<Firm>> DuringTree;
	public BTree<String,ArrayList<Firm>> AfterTree;
	
	public BTree<String,ArrayList<Firm>> sicTree;	
	public BTree<String,ArrayList<Firm>> categoryTree;
	
	public BTree<Integer,ArrayList<Firm>> quarterTree;
	
	public ArrayList<String> cusipList;	
	
//	Describe data in terms of bk, tgt, acq, gc
		int goingconcernCount = 0;
		int targetCount = 0;
		int acquirerCount = 0;
		int bankruptCount = 0;	
		Mapping m = new Mapping();
		BTree<String, Integer> dM;
		BTree<Integer, Integer> qM;
	
	public Economy(String path)
	{
		filePath = path;
		dM = m.dateMap(path);			
		qM = m.quartermap(path);
		utilities = new EconUtils(path);
		
		AllFirms = new ArrayList<Firm>(); //All CRSP entries
		AllFirms2 = new BTree<String,Firm>(); //
		
		firmTree = new BTree<String,ArrayList<Firm>>();		
		
		bankTree = new BTree<String,ArrayList<Bankrupcy>>();		

		bankruptTree = new BTree<String,ArrayList<Firm>>();
		goingConcernTree = new BTree<String,ArrayList<Firm>>();		
		sicTree = new BTree<String,ArrayList<Firm>>();
		categoryTree = new BTree<String,ArrayList<Firm>>();
		quarterTree = new BTree<Integer,ArrayList<Firm>>();

		BeforeTree = new BTree<String,ArrayList<Firm>>();
		DuringTree = new BTree<String,ArrayList<Firm>>();
		AfterTree = new BTree<String,ArrayList<Firm>>();
		
	}
	
	public void doBankrupcy(String filename){
		
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
	            	b._363Index = 0;	            	
	            }
	            else{
	            	b._363Index = dM.get(b.date363sale);
	            }
	            if(dM.get(b.dateConfirmed) == null){
	            	b.confirmedIndex = 0;
	            }
	            else{
	            	b.confirmedIndex = dM.get(b.dateConfirmed);
	            }
	            if(dM.get(b.dateConvDismiss) == null){
	            	b.convDismissedIndex = 0;
	            }
	            else{
	            	b.convDismissedIndex = dM.get(b.dateConvDismiss);
	            }
	            if(dM.get(b.dateEffective) == null){
	            	b.effectiveIndex = 0;
	            }
	            else{
	            	b.effectiveIndex = dM.get(b.dateEffective);
	            }
	            if(dM.get(b.dateEmerging) == null){
	            	b.emergingIndex = 0;
	            }
	            else{
	            	b.emergingIndex = dM.get(b.dateEmerging);
	            }
	            
	            if(dM.get(b.dateFiled) == null){
	            	b.filedIndex = 0;
	            }
	            else{	
	            	b.filedIndex = dM.get(b.dateFiled);
	            }
	            if(dM.get(b.dateDisposed) == null){
	            	b.disposedIndex = 0;
	            }
	            else{
	            	 b.disposedIndex = dM.get(b.dateDisposed);
	            }
	            /**
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
	            
	            if(tree.get(b.cusip) != null){ //the cusip has a prior entry
	            	tree.get(b.cusip).add(b); // so add the new entry to the bk tree	            	
	            	bankruptCount++;
	            }
	            else{ //we havent seen this cusip yet
	            	ArrayList<Bankrupcy> l = new ArrayList<Bankrupcy>(); //make a new list to insert with the cusip
	            	l.add(b);	  	//add the bk to the new list
	            	tree.put(b.cusip, l);	//put <cusip, ArrayList<Bankrupcy>> key,value pair into tree
	            	bankruptCount++;
	            }
		    }		    
		    in.close();		    
		} catch (IOException e) {
			System.out.println("BAD FILE WAS > " + filename);
		    System.out.println("File Read Error in doBankrupcy");
		    System.exit(0);
		}	    
		System.out.println("Reading bankrupcy data: done!");		
		setBKTree(tree); //this sets the 
	}	
	
	public void setBKTree(BTree<String,ArrayList<Bankrupcy>> bt){
		bankTree = bt;
	}
	
	public ArrayList<Firm> getFirmsInSIC(String sic)
	{	
		ArrayList<Firm> fInSIC = new ArrayList<Firm>();
		
		for(int i = 0;i < sicTree.get(sic).size(); i++){
			fInSIC.add(sicTree.get(sic).get(i));
		}
		
		return fInSIC;	
	}
	
	public ArrayList<Firm> getFirmsInQuarter(ArrayList<Firm> tree, Integer quarter)
	{
		ArrayList<Firm> fInQuarter = new ArrayList<Firm>();
		
		for(int i=0; i<tree.size();i++){
			if(quarter == utilities.getQuarterIndex(qM, dM.get(tree.get(i).datadate))){
				fInQuarter.add(tree.get(i));
			}			
		}
		return fInQuarter;
	}
	
	public float[] compareSICandFirm(Firm f)
	{
		ArrayList<Firm> inSic = getFirmsInSIC(f.sic);		
		Integer qtr = utilities.getQuarterIndex(qM, dM.get(f.datadate));		
		ArrayList<Firm> inQuarter = getFirmsInQuarter(inSic, qtr);		
		float avgK = utilities.averageK(inQuarter);		
		float avgQ = utilities.averageTQ(inQuarter);
		
		float[] result = 
		{				
			(Float.parseFloat(f.ppegtq) - avgK),
			(Float.parseFloat(f.Tobins_Q) - avgQ)				
		};		
		return result;		
	}
	
	public void shareResults(){	
		ArrayList<Firm> before = categoryTree.get("BEFORE");
		ArrayList<Firm> during = categoryTree.get("DURING");
		ArrayList<Firm> after = categoryTree.get("AFTER");

		System.out.println("BEFORE BEING COMPARED");
		for(int i = 0;i<before.size();i++){
			float[] tmp = compareSICandFirm(before.get(i));
			System.out.println("difference in K: "+ tmp[0] + " difference in TQ: "+ tmp[1]);
		}
		System.out.println("DURING BEING COMPARED");
		for(int i = 0;i<during.size();i++){
			float[] tmp = compareSICandFirm(during.get(i));
			System.out.println("difference in K: "+ tmp[0] + " difference in TQ: "+ tmp[1]);
		}
		System.out.println("AFTER BEING COMPARED");
		for(int i = 0;i<after.size();i++){
			float[] tmp = compareSICandFirm(after.get(i));
			System.out.println("difference in K: "+ tmp[0] + " difference in TQ: "+ tmp[1]);
		}		
	}
	
	/**
	 * 
	 * 	createFirmTransitionObj returns a nested list structure
	 * 	
	 * 			1) list of all cusips in argument
	 * 			2) list of before during and after
	 * 			3) list of firm entries
	 * 
	 * @param cusips
	 * @return
	 */
	public ArrayList<ArrayList<ArrayList<Firm>>> createFirmTransitionObj(ArrayList<String> cusips) 
	{		
		ArrayList<Firm> beforeList = new ArrayList<Firm>();
		ArrayList<Firm> duringList = new ArrayList<Firm>();
		ArrayList<Firm> afterList= new ArrayList<Firm>();

		ArrayList<ArrayList<ArrayList<Firm>>> firmTimeSeries = new ArrayList<ArrayList<ArrayList<Firm>>>();
		ArrayList<ArrayList<Firm>> cusipTimeSeries;

		for(int i = 0; i<cusips.size();i++)
		{			
			cusipTimeSeries = new ArrayList<ArrayList<Firm>>();
			
			beforeList = BeforeTree.get(cusips.get(i));
			duringList = DuringTree.get(cusips.get(i));
			afterList  = AfterTree.get(cusips.get(i));
			
			utilities.printList(beforeList);
			utilities.printList(duringList);
			utilities.printList(afterList);
			
			cusipTimeSeries.add(beforeList);
			cusipTimeSeries.add(duringList);
			cusipTimeSeries.add(afterList);
			
			firmTimeSeries.add(cusipTimeSeries);			
		}
		return firmTimeSeries;
	}	
}
