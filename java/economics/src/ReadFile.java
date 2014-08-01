import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {
	//FILED INDEX NEEDS TO BE FIXED!
	public static void main(String[] args) throws IOException{
		
		EconUtils util = new EconUtils();		
		Economy E = new Economy();
		
		//input filenames
		System.out.println("first file");
		String bankrupcies = "src\\brd_data_set2b.txt";
		//String successfulMergers = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\sdc_processing\\m_a_data_success.txt";
		//String failedMergers = "";
		String bkCusipFile = "src\\brd_cusips.txt";
		//String taCusipFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\target_cusip.txt";		
		//String acCusipFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\acquirer_cusip.txt";	
		//String maCusipFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\ma_cusip.txt";
		
		//String tgtCusips = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\october_2013\\output\\target_data_reduced.txt";
		
		//db filenames
		String filename = "src\\crsp_quarterly_and_yearly_large.txt";				
		
		//output filenames
		String bkOutputFile = "src\\bk_k_tq_qtr.txt";		
		//String tgOutputFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\october_2013\\output\\test_results\\w_sic\\tg_k_tq_qtr.txt";		
		//String maOutputFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\october_2013\\output\\test_results\\w_sic\\ma_k_tq_qtr.txt";		
		//String acOutputFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\october_2013\\output\\test_results\\w_sic\\aq_k_tq_qtr.txt";		
		String gcOutputFile = "src\\gc_k_tq_qtr.txt";		
		
		E = util.readCRSP(E, filename);		
		System.out.println("Creating Economy Table: done!");		

//	Read in bankrupcy
		ArrayList<String> bkList = util.readList(bkCusipFile);

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
 Mapping m2= new Mapping();
 m2.quartermap();
 //System.exit(0);
 
 while(idx<E.AllFirms.size())
 {	
	 f = new Firm();		 
	 f = E.AllFirms.get(idx);
	 float perDone = (float)((float)idx/(float)E.AllFirms.size())*(float)100;
	 System.out.println(perDone+" %  |  BK : "+bkCount+"  |  GC: "+gcCount /*+ "  |  TG: "+tgCount + "  |  AQ: "+aqCount*/);	 
	// found = util.writeIfFound(bkList, f, bkOutputFile);
	 if(util.writeIfFound(E, bkList, f, bkOutputFile))
		 bkCount++;
	// else if(util.writeIfFound(targetList, f, tgOutputFile))
		// tgCount++;
	 //else if(util.writeIfFound(acquirerList, f, acOutputFile))
		// aqCount++;
	 else{
		 util.writeUnconditionally(f, gcOutputFile);
		 gcCount++;	 
	 }
	// found = util.writeIfFound(targetList, f, tgOutputFile);
	// found = util.writeIfFound(acquirerList, f, acOutputFile);	 	
	idx++; 	
 }
 System.out.println("NUM UNIQUE BK: "+bkUniqueSize);
 System.out.println("NUM FOUND BK OVERALL"+bkFoundSize);
 System.out.println("NUM UNIQUE GC: "+gcCount); 
 System.out.println("NUM UNIQUE AQ: "+aqCount);
 System.out.println("NUM UNIQUE TG: "+tgCount);
 System.out.println("NUM UNIQUE MA: "+ (aqCount + tgCount));
 System.out.println("Done writing files!");
//now filter all the categories by date in institution
String outFolder = "src\\";
String bkFiltered = "bankrupcy.txt";
String tgFiltered = "target.txt";
String aqFiltered = "acquirer.txt";
String gcFiltered = "going_concern.txt";
    
System.out.println("MOVING TO LABOR");
	}
}
