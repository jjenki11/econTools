import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Bankrupcy {	

	
	 /*
	 * XNameCorp	CikBefore	CikEmerging	CUSIP	
	 * Date363Sale	DateConfirmed	DateConvDismiss	DateDisposed
	 * 	DateEffective	DateEmerging	DateFiled	DaysTo363
	 * 	DaysToRefile	Emerge	GvkeyBefore	GvkeyEmerging	NameEmerging
	 * 	Refile	Sale363	SalesCurrDollar	SICPrimary	XChapter	XConfirmed
	 * 	XDaysIn	XNumEmplBefore	XPrepackaged 
	 * 
	 */
	static ArrayList<Bankrupcy> bankrupcyList = new ArrayList<Bankrupcy>();
	
	String 	XNameCorp="";
	String 	CikBefore="";
	String 	CikEmerging="";
	String 	cusip="";
	String 	date363sale="";
	String 	dateConfirmed="";
	String 	dateConvDismiss="";
	String 	dateDisposed="";
	String 	dateEffective="";
	String 	dateEmerging="";
	String 	dateFiled="";
	int 	daysTo363=0;
	int		daysToRefile=0;
	String 	emerge = "";
	String 	gvKeyBefore="";
	String 	gvKeyEmerging="";
	String 	nameEmerging="";
	String 	refile="";
	String 	sale363="";
	double 	salesCurrDollar=0.0;
	String 	SIC = "";
	int 	chapter = 0;
	String 	confirmed="";
	int 	daysIn = 0;
	int 	NumEmployedBefore = 0;
	String 	prepackaged="";
	//date indices
	int		filedIndex=0;
	int		disposedIndex=0;
	int		_363Index=0;
	int		confirmedIndex=0;
	int 	convDismissedIndex=0;
	int 	effectiveIndex=0;
	int 	emergingIndex=0;
	boolean[] state= {false,false,false,false,false,false};

	public Bankrupcy(	String name, String cb, String ce, String cu, String d3, String dc,
					 	String dcd, String dd, String de, String dm, String df, int dt3, int dtr, 
					 	String em, String gb, String ge,String ne, String re, String s363, double scd, String sic,
					 	int ch, String cn, int di, int emp, String pre)
	{
		XNameCorp=name;CikBefore=cb;CikEmerging=ce;cusip=cu;date363sale=d3;dateConfirmed=dc;
		dateConvDismiss=dcd;dateDisposed=dd;dateEffective=de;dateEmerging=dm;dateFiled=df;daysTo363=dt3;daysToRefile=dtr;emerge=em;
		gvKeyBefore=gb;gvKeyEmerging=ge;nameEmerging=ne;refile=re;sale363=s363;salesCurrDollar=scd;SIC=sic;chapter=ch;confirmed=cn;
		daysIn=di;NumEmployedBefore=emp;prepackaged=pre;
		
		filedIndex=0;disposedIndex=0;_363Index=0;confirmedIndex=0;effectiveIndex=0;emergingIndex=0;
	}
	Bankrupcy(){
		filedIndex = 0;
		effectiveIndex = 0;
		disposedIndex = 0;
		confirmedIndex = 0;		
	}
	// within BKnow daterange checker boolean : ADDED third possibility for beginning of period where already in but no filedindex
	
	public boolean withinBankrupcyNow(int datadate){
		if( ( (datadate >= filedIndex) && (datadate <= disposedIndex))
		|| ((datadate >= filedIndex) && (disposedIndex == 0))
		|| ((datadate <= disposedIndex) && (filedIndex == 0 ))
		
		) {
			return true;
		}		
		return false;
	}
	
	// before BK within range checker boolean ; 
	public boolean BankrupcyBefore (int datadate, int years) {
		if (((filedIndex - datadate) < (years*365)) && ((filedIndex - datadate) >= 0 )) {
			return true; 
		}
		return false;
	}
	
	//after BK within range checker boolean ; FLIPPED around datadate and disposedindex to make it right
	public boolean BankrupcyAfter (int datadate, int years) {
		if (((datadate - disposedIndex) >= 0) && ((datadate - disposedIndex) < (years*365))) {
			return true;
		}
		return false;
	
	}
	
	// boolean checker for BK ever in whole data range: ADDED disposed index
	public boolean withinBankrupcyEver(int datadate){
		if ((filedIndex == 0)
		|| (disposedIndex == 0)) {
			return true;
		}		
		return false;
	}
	
	/**
	 * 
	 * enumeration for returned boolean array
	 * 
	 * index		description
	 *   0			  before bankruptcy filed
	 *   1			  during bankruptcy
	 *   2			  after bankruptcy disposed
	 *  // 3			  ever in bankruptcy
	 *   3			  never in bankruptcy
	 * 
	 * @param data
	 * @return
	 */
	public boolean[] evaluateBK(int data){
		state[0] = BankrupcyBefore(data, 2); 			// if firm is in BK now
		state[1] = withinBankrupcyNow(data); 			// if firm is preceding BK
		state[2] = BankrupcyAfter(data,2); 				// if firm emerges out of BK
		//state[3] = (state[0] || state[1] || state[2]); 	// if firm is ever in BK		
		state[3] = !(state[0] || state[1] || state[2]);							// if firm was never in BK, GC
		return state;
	}
	
	//public void addBKNOWToList(Bankrupcy bank){
	//	bankrupcyList.add(bank);
	//}
	public void addBKBEFORETolist (Bankrupcy bank){
		bankrupcyList.add(bank);
	}		
	public void addBKAFTERTolist (Bankrupcy bank) {	
		bankrupcyList.add(bank);
	}
	
}
	

