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
		String filename = util.filePath+"crsp_quarterly_and_yearly_large6.txt";
		
		//  Define all the data types u want
		String atResult = util.filePath+"ATresults\\";		
		String oibdpResult = util.filePath+"OIBDPresults\\";
		String ppegtResult = util.filePath+"PPEGTresults\\";
		String mveResul = util.filePath+"MVEresults\\";
		String profResult = util.filePath+"PROFresults\\";
		String saleResult = util.filePath+"SALEresults\\";
		String prccResult = util.filePath+"PRCCresults\\";
		
	//  Change bkBeforeFile (etc) = <|xxxxxxx|> + "something.txt" to any of the above folder paths.
		
		//		I chose AT to push
		
		String bkBeforeFile =         atResult     +  "bk_before.txt";
		String bkDuringFile =         atResult     +  "bk_during.txt";
		String bkAfterFile  =         atResult     +  "bk_after.txt";
		String gcOutputFile =         atResult     +  "gc_firms.txt";
		
		//output filenames
		
		String bkOutputFile = util.filePath+"bk_k_tq_qtr.txt";		// TBD do we need this?
		
		/*  not anymore with 1 folder... need to change when u change prototype 
		 *  You also need to change in 
		 * 
			String bkBeforeFile = util.filePath+"results2\\bk_before.txt";	
			String bkDuringFile = util.filePath+"results2\\bk_during.txt";
			String bkAfterFile = util.filePath+"results2\\bk_after.txt";	
			String gcOutputFile = util.filePath+"results2\\gc_firms.txt";
		*/
		
		String[] outputFileArray = {
				bkBeforeFile,
				bkDuringFile,
				bkAfterFile,
				gcOutputFile
		};
		
		E = util.readCRSP(E, filename);		
		System.out.println("Creating Economy Table: done!");		

//	Read in bankrupcy
		ArrayList<String> bkList = util.readList(bkCusipFile);
		
		E.cusipList = bkList;

		//bkList = util.readList(bkCusipFile);    
		System.out.println("Reading bankrupcy data: done!");

//	Read in all targets
		//ArrayList<String> targetList = util.readList(taCusipFile);

		//targetList = util.readList(taCusipFile);
		//System.out.println("Reading Target data: done!");

//	Read in all acquirers
		//ArrayList<String> acquirerList = util.readList(acCusipFile);

		//acquirerList = util.readList(acCusipFile);
		//System.out.println("Reading Acquirer data: done!");

		
		E.doBankrupcy(bankrupcies);
		
		
		//System.exit(0);
		//E.doMerger(successfulMergers, maCusipFile);	
		//E.doTarget(successfulMergers, taCusipFile);
		//E.doAcquirer(successfulMergers, acCusipFile);
		

System.out.println("Starting GC ... ");
//put all the rest in GC

System.out.println("GC COUNT: "+E.goingconcernCount);
System.out.println("Bankrupt COUNT: "+E.bankruptCount);


ArrayList<Firm> l;
int gcCount = 0;
Firm f;
System.out.println(E.AllFirms.size());

 int idx=0;
 int bkCount = 0;
 int bkUniqueSize = 0;
 int bkFoundSize = 0;
 int tgCount = 0;
 int aqCount = 0;
 boolean found = false;
 //System.exit(0)
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
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 counter[0] += xx[0];	 
	 counter[1] += xx[1];	
	 counter[2] += xx[2];	
	 counter[3] += xx[3];
	// counter[4] += xx[4];
	 System.out.println(counter[3]);
	 
	 
	 System.out.println(perDone+" %  |  BK(before) : "+counter[0]+"  |  BK(during): "+counter[1] +
			 					"    |  BK(after) : "+counter[2]+
			 					"    |  GC(always) :"+counter[3]+ "  |  ");//TG: "+tgCount + "  |  AQ: "+aqCount);	
	 idx++;
 }

 System.out.println("Done writing files!");
 
    
	System.out.println("MOVING TO NEW QUERY");
	
	//E.shareResults();
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
