package test;

import java.util.ArrayList;
import java.util.List;


public class Firm 
{	
	int dateIndex;
	
	boolean GC;
	boolean MA;
	boolean TA;
	boolean AQ;
	boolean BK;
	
	String category;	
	String gvkey;
	String datadate;
	String fyearq;
	String fqtr;
	String cusip;
	String cshpry;
	String txditcq;
	String sic;
	String deflator;
	String atq;
	String dlcq;
	String dlttq;
	String dpactq; //add
	String dpq;		//add
	String oibdpq;
	String ppegtq;	//add
	String prccq;
	String pstkq;
	String saleq;
	String Profitability;
	String Market_value_equity;
	String Equity_book_value;
	String Tobins_Q;
	String ATO;
	String NW;
	
	ArrayList<Bankrupcy> bankrupcy = new ArrayList<Bankrupcy>();
	ArrayList<Merger> merger = new ArrayList<Merger>();
	String labor;	
	List<Firm> entries;
	
	public Firm(){
		gvkey="";
		datadate="";
		fyearq="";
		fqtr="";
		cusip="";
		cshpry="";
		txditcq="";
		sic="";
		deflator="";
		atq="";
		dlcq="";
		dlttq="";
		oibdpq="";
		prccq="";
		pstkq="";
		saleq="";
		Profitability="";		
		Market_value_equity="";
		Equity_book_value="";
		Tobins_Q="";
		ATO= "";
		NW= "";
		
		GC = false;
		MA = false;
		BK = false;
		TA = false;
		AQ = false;
		//dateIndex = 0;
		
		bankrupcy = new ArrayList<Bankrupcy>();
		merger = new ArrayList<Merger>();		
		entries=new ArrayList<Firm>();
	}
	
	public Firm(String c, int d, String e, String s){
		cusip=c;
		dateIndex=d;
		labor=e;
		sic=s;
		GC = false;
		MA = false;
		BK = false;
		TA = false;
		AQ = false;
	}
	
	public void setMerger(Merger m){
		merger.add(m);
		MA = true;
		if(m.targetcusip == this.cusip){
			TA = true;
		}
		if(m.acquirercusip == this.cusip){
			AQ = true;
		}
	}	
	
	public Merger getMerger(String type){
		Merger tmp = new Merger();
		
		for(int i =0; i<merger.size();i++){
			
			if(type == "target"){

			if(((merger.get(i).targetcusip == cusip)) && 
			  ((merger.get(i).announcedIndex <= dateIndex) && 
			   (merger.get(i).effectiveIndex >= dateIndex))){
					System.out.println("You got target: "+merger.get(i).targetcusip);
					tmp= merger.get(i);		
					return tmp;
			}
			}
			else if (type == "acquirer"){
				 if(((merger.get(i).acquirercusip == cusip)) &&
				    ((merger.get(i).announcedIndex < dateIndex) && 
					 (merger.get(i).effectiveIndex > dateIndex))){
					System.out.println("You got acquirer: "+merger.get(i).acquirercusip);
					tmp= merger.get(i);		
					return tmp;
				 }				 
			}
			else{
				
			}
		}
		
		return tmp;
		//return merger;
	}
	public void setBankrupcy(Bankrupcy b){
		bankrupcy.add(b);
	}
	public ArrayList<Bankrupcy> getBankrupcy(){
//		Bankrupcy tmp = new Bankrupcy();		
//		for(int i =0; i<bankrupcy.size();i++){
//			if(((bankrupcy.get(i).cusip == cusip) ) && 
//			  ((bankrupcy.get(i).filedIndex <= dateIndex) && 
//			   (bankrupcy.get(i).effectiveIndex >= dateIndex))){
//				System.out.println("You got BK: "+bankrupcy.get(i).cusip);
//					tmp= bankrupcy.get(i);				
//			}
//			else{}
//		}		
		return bankrupcy;
	}
	
	public boolean setCategory(String c){
		category = c;
		return true;
	}
	public String getCategory(){
		return category;
	}	
}
