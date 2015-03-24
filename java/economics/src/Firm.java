package test;

import java.util.ArrayList;
import java.util.List;


public class Firm 
{	
	int dateIndex;
	
	boolean GC;
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
		BK = false;
		
		bankrupcy = new ArrayList<Bankrupcy>();
		entries=new ArrayList<Firm>();
	}
	
	public Firm(String c, int d, String e, String s){
		cusip=c;
		dateIndex=d;
		labor=e;
		sic=s;
		GC = false;
		BK = false;
	}
	
	
	public void setBankrupcy(Bankrupcy b){
		bankrupcy.add(b);
	}
	public ArrayList<Bankrupcy> getBankrupcy(){

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
