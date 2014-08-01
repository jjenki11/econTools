import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

public class Merger {	
	
	ArrayList<Merger> mergerList = new ArrayList<Merger>();
	

	
	String 	targetcusip;
	String 	acquirercusip;
	//int 	chapter = 0;
	int 	daysIn;
	//String 	SIC = "";
	String 	merge;
	String 	dateAnnounced;
	String 	dateEffective;
	String 	dateWithdrawn;
	int 	announcedIndex;
	int		effectiveIndex;
	int		withdrawnIndex;
	
	
	public Merger(){
		announcedIndex=-1;
		effectiveIndex=-1;
		withdrawnIndex=-1;
	}
	
	public Merger(String tcu, String acu, int dI, String me, String da, String de, String dw)
	{
		targetcusip=tcu;
		acquirercusip=acu;
		//chapter = ch;
		daysIn = dI;
		//SIC = sic;
		merge = me;
		dateAnnounced = da;
		dateEffective = de;
		dateWithdrawn = dw;
		announcedIndex = 0;
		effectiveIndex = 0;
		withdrawnIndex = 0;
	}
	
	public String[] getCusips(){
		//String s = targetcusip+","+acquirercusip;
		return ( new String( targetcusip + "," + acquirercusip ).split( "," ) ) ;
	}
	
	public int[] getDates(){
		int[] arr = new int[3];
		arr[0]=announcedIndex;
		arr[1]=effectiveIndex;
		arr[2]=withdrawnIndex;
		return arr;
	}
	
	public void addMergerToList(Merger merge){
		
		mergerList.add(merge);		
	}
	
//	public Merger(){}
	

}
