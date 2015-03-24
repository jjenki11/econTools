package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
	static Economy ECONOMY;
	static String text = "";
	public ReadFile(String path) {		
		EconUtils util = new EconUtils(path);		
		Economy E = new Economy(util.filePath);
		
		//input filenames
		System.out.println("first file");
		String bankrupcies = util.filePath+"brd_data_set2b.txt";
		String bkCusipFile = util.filePath+"brd_cusips.txt";
		
		//db filenames
		String filename = util.filePath+"crsp_removed_blanks_net_worth_asset_turnover.txt";
		//  Define all the data types u want to go to separate folders		
		String tobinsqResult = util.filePath+"TOBINSQresults\\";
		String atResult = util.filePath+"ATresults\\";		
		String oibdpResult = util.filePath+"OIBDPresults\\";
		String ppegtResult = util.filePath+"PPEGTresults\\";
		String mveResult = util.filePath+"MVEresults\\";
		String profResult = util.filePath+"PROFresults\\";
		String saleResult = util.filePath+"SALEresults\\";
		String prccResult = util.filePath+"PRCCresults\\";
		String atoResult = util.filePath+"ATOresults\\";
		String netWorthResult = util.filePath+"NETWORTHresults\\";
		//  Change selected = <|xxxxxxx|> + "something.txt" where xxxxxxx is any of the above folder paths.		
		String selected = atoResult;		
		String bkBeforeFile =         selected     +  "bk_before.txt";
		String bkDuringFile =         selected     +  "bk_during.txt";
		String bkAfterFile  =         selected     +  "bk_after.txt";
		String gcOutputFile =         selected     +  "gc_firms.txt";
		
		//output filenames		
		String bkOutputFile = util.filePath+"bk_k_tq_qtr.txt";		// TBD do we need this?
		
		String[] outputFileArray = {
				bkBeforeFile,
				bkDuringFile,
				bkAfterFile,
				gcOutputFile
		};
		
		// Read entire crsp dataset into Economy object (E)
		E = util.readCRSP(E, filename);		
		System.out.println("Creating Economy Table: done!");		

		//	Read in bankrupcy
		ArrayList<String> bkList = util.readList(bkCusipFile);		
		E.cusipList = bkList;
		System.out.println("Reading bankrupcy data: done!");
		E.doBankrupcy(bankrupcies);
		
		// go through each firm from the CRSP and write them to their respective category folders
		System.out.println("Starting GC ... ");
		System.out.println("GC COUNT: "+E.goingconcernCount);
		System.out.println("Bankrupt COUNT: "+E.bankruptCount);
		ArrayList<Firm> l;
		int gcCount = 0;
		Firm f;
		int idx=0;
		int bkCount = 0;
		int bkUniqueSize = 0;
		int bkFoundSize = 0;
		int tgCount = 0;
		int aqCount = 0;
		boolean found = false;
		int[] counter = {0,0,0,0};
		 while(idx<E.AllFirms.size())
		 {	
			 f = new Firm();		 
			 f = E.AllFirms.get(idx);
			 float perDone = (float)((float)idx/(float)E.AllFirms.size())*(float)100;
			 int[] xx = new int[4];
			try {
				xx = util.writeIfFound(E, bkList, f, outputFileArray);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 counter[0] += xx[0];	 
			 counter[1] += xx[1];	
			 counter[2] += xx[2];	
			 counter[3] += xx[3]; 
			 System.out.println(perDone + "\t % done");
			 //	Iff you want to see list building (makes things slower)
			 /*
			 System.out.println(perDone+" %  |  BK(before) : "+counter[0]+"  |  BK(during): "+counter[1] +
					 					"    |  BK(after) : "+counter[2]+
					 					"    |  GC(always) :"+counter[3]+ "  |  ");//TG: "+tgCount + "  |  AQ: "+aqCount);
			 */
			 idx++;
		 }		
		 System.out.println("Done writing files!");     
		 System.out.println("Printing firm transition object?");	
		//E.shareResults();  //  this will print out the results to the console -- interesting way to access data set in shareResults file found in Economy.java
		text = util.printFirmTransitionObject(E.createFirmTransitionObj(bkList));
		ECONOMY = E;
	}	
	public Economy getEconomy(){
		return ECONOMY;
	}	
	public String getText(){
		return text;		
	}
}
