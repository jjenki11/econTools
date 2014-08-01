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
		filedIndex=-1;
		effectiveIndex=-1;
		disposedIndex=-1;
		confirmedIndex=-1;		
	}
	
	public boolean withinBankrupcyNow(int datadate){
		if((datadate >= filedIndex) && (datadate < disposedIndex)){
			return true;
		}		
		return false;
	}
	public boolean withinBankrupcyEver(int datadate){
		if(filedIndex != -1){
			return true;
		}		
		return false;
	}
	
	public boolean[] evaluateBK(int data){
		state[0] = withinBankrupcyNow(data);
		state[1] = false;
		state[2] = false;
		state[3] = false;
		state[4] = false;
		state[5] = true;
		
		return state;
	}
	
	public void addBKToList(Bankrupcy bank){
		
		bankrupcyList.add(bank);
	}
	
}
