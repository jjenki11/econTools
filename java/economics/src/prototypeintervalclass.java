package test;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

//  Data structure to hold intermediate results for our Econ evaluation


/**
 *  This is the following output order (column names)
 * 
 * CUSIP	
 * AVE_before(before)	
 * AVE_after(before)	
 * Qint_difference_before	
 * AVE_before(during)	
 * AVE_after(during)	
 * Qint_difference_During	
 * AVE_before(After)	
 * AVE_after(After)	
 * Qint_difference_After	
 * SIC_AVE_before(before)	
 * SIC_AVE_after(before)	
 * SIC_Qint_difference_before	
 * SIC_AVE_before(during)	
 * SIC_AVE_after(during)	
 * SIC_Qint_difference_During	
 * SIC_AVE_before(After)	
 * SIC_AVE_after(After)	
 * SIC_Qint_difference_After
 */


class resultMatrix
{
	
	String cusip;
	
	
	float ave_before_before;
	float ave_after_before;
	float qint_diff_before;
	
	float ave_before_during;
	float ave_after_during;
	float qint_diff_during;
	
	float ave_before_after;
	float ave_after_after;
	float qint_diff_after;
	
	float sic_ave_before_before;
	float sic_ave_after_before;
	float sic_qint_diff_before;
	
	float sic_ave_before_during;
	float sic_ave_after_during;
	float sic_qint_diff_during;
	
	float sic_ave_before_after;
	float sic_ave_after_after;
	float sic_qint_diff_after;
	
	public resultMatrix(){
		
		cusip = "";
		
		ave_before_before = 0;
		ave_after_before = 0;
		qint_diff_before = 0;
		
		ave_before_during = 0;
		ave_after_during = 0;
		qint_diff_during = 0;
		
		ave_before_after = 0;
		ave_after_after = 0;
		qint_diff_after = 0;
		
		sic_ave_before_before = 0;
		sic_ave_after_before = 0;
		sic_qint_diff_before = 0;
		
		sic_ave_before_during = 0;
		sic_ave_after_during = 0;
		sic_qint_diff_during = 0;
		
		sic_ave_before_after = 0;
		sic_ave_after_after = 0;
		sic_qint_diff_after = 0;		
	}	
}

class boundedValue
{	
	int start;
	int mid;
	int end;			
	float beforeAverageTQFirm;
	float afterAverageTQFirm;	
	float beforeAverageTQSIC;
	float afterAverageTQSIC;		
	float quarterlyIntervalTQDifference; 	
	float quarterlyIntervalTQDifferenceSIC;
	String sic;	String cusip;	
	String state;	
	int quarterSpan;
	
	public boundedValue()
	{
		start=0;
		mid=0;
		end=0;		
		state="";		
		beforeAverageTQFirm=0;
		afterAverageTQFirm=0;			
		beforeAverageTQSIC=0;
		afterAverageTQSIC=0;				
		quarterlyIntervalTQDifference=0;	
		quarterlyIntervalTQDifferenceSIC=0;
		sic = "";
		cusip = "";
		quarterSpan = 0;
	}	
}

public class prototypeintervalclass 
{	
	//static String path = "C:\\Users\\Rutger\\Desktop\\ECON REPO\\econTools\\java\\economics\\src\\";
	static String path = "C:\\Users\\blackhole\\Desktop\\econRepo\\java\\economics\\src\\";
	static ArrayList<Firm> firms = new ArrayList<Firm>();
	static Economy econo;
	static EconUtils utils = new EconUtils(path);
	static int cusips = 100;
	static int timeBlock = 3;
	static int dataPoints = 120;
	static ArrayList<ArrayList<ArrayList<Firm>>> firmTimeseries;		
	static BTree<String, ArrayList<boundedValue>> resTree;	
	static BTree<String, resultMatrix> treeToWrite;	
	static resultMatrix mat;
	
	// As the name suggests, find all firms within a range of quarter indices having a specified SIC
	public static ArrayList<Firm> getFirmsInQuarterRangeWithSIC(int start, int end, String sic, String state)
	{				
		ArrayList<ArrayList<Firm>> firmsInQuarterRange = new ArrayList<ArrayList<Firm>>();
		firmsInQuarterRange = utils.createGCRangeList(econo, start, end, state);
		ArrayList<Firm> firmsWithSIC = new ArrayList<Firm>();		
		for(int i = 0; i < firmsInQuarterRange.size(); i ++)
		{
			if((firmsInQuarterRange.get(i) != null))
			{
				for(int j = 0; j < firmsInQuarterRange.get(i).size(); j ++)
				{					
					float tmpVal = 0.0f;					
					//check if the firm evaluated has desired sic
					if(Integer.parseInt(firmsInQuarterRange.get(i).get(j).sic) == Integer.parseInt(sic) &&							
					  (econo.bankTree.get(firmsInQuarterRange.get(i).get(j).cusip) == null))
					{						
						if(
							Float.isNaN(Float.parseFloat(firmsInQuarterRange.get(i).get(j).Tobins_Q))
						  ){}
						else
						{
							firmsWithSIC.add(firmsInQuarterRange.get(i).get(j));
						}						
					}					
				}
			}
		}		
		return firmsWithSIC;
	}
	
	// Take average on both sides of 3 element interval to return an arraylist
	// of floats, the 'before midpoint average' and 'after midpoint average'	
	
	public static int[] makeBeforeInterval(ArrayList<Firm> list)
	{
		int dis = 1;
		int first = 0;
		
		int dis2 = 120;		
		int last = 0;
		
		int years = (int)((float)(2*366));
		int quarter = 92;  // # days in a qtr
		
		Integer filed       = (Integer)(utils.qM2.get(list.get(0).getBankrupcy().get(0).filedIndex - quarter));
		Integer beforeFiled = (Integer)(utils.qM2.get(list.get(0).getBankrupcy().get(0).filedIndex - years));

		if(filed != null &&
		   beforeFiled != null){
			last = filed;
			first = beforeFiled;
		} else {
			last = dis2;
			first = dis2 - years;
		}		
		
		int mid = (first+last)/2;
		int[] x = new int[3];
		x[0]=first;
		x[1]=mid;
		x[2]=last;		
		return x;	
	}
	
	public static int[] makeDuringInterval(ArrayList<Firm> list)
	{
		int dis2 = 1;
		int first = 0;
		
		int dis = 120;		
		int last = 0;
		
		if(	utils.qM2.get(list.get(0).getBankrupcy().get(0).disposedIndex) != null){
			last = utils.qM2.get(list.get(0).getBankrupcy().get(0).disposedIndex);
		} else {
			last = dis;
		}
		
		if(utils.qM2.get(list.get(0).getBankrupcy().get(0).filedIndex) != null){			
			first = utils.qM2.get(list.get(0).getBankrupcy().get(0).filedIndex);
		} else {
			first = dis2;
		}

		int mid = (first+last)/2;
		int[] x = new int[3];
		x[0]=first;
		x[1]=mid;
		x[2]=last;		
		return x;	
	}
	
	public static int[] makeAfterInterval(ArrayList<Firm> list)
	{
		int dis2 = 1;
		int first = 0;
		
		int dis = 120;		
		int last = 0;
		
		int years = (int)((float)(2*366));
		int quarter = 92;
		
		Integer disposed      = (utils.qM2.get(list.get(0).getBankrupcy().get(0).disposedIndex + quarter));
		Integer afterDisposed = (Integer)(utils.qM2.get(list.get(0).getBankrupcy().get(0).disposedIndex + years));
		
		if(disposed != null &&
		   afterDisposed != null){
			first = disposed;
			last = afterDisposed;
		} else {
			first = dis2;
			last = dis;
		}
		
		int mid = (first+last)/2;
		int[] x = new int[3];
		x[0]=first;
		x[1]=mid;
		x[2]=last;		
		return x;	
	}
	
	// Find the average value for both sides of an interval
	public static boundedValue findAverage(ArrayList<Firm> list, String state)
	{		
		ArrayList<Float> beforeTQAvg = new ArrayList<Float>();
		ArrayList<Float> afterTQAvg = new ArrayList<Float>();
		
		ArrayList<Float> beforeTQSICAvg = new ArrayList<Float>();
		ArrayList<Float> afterTQSICAvg = new ArrayList<Float>();
		
		ArrayList<Float> beforeProfSICAvg = new ArrayList<Float>();
		ArrayList<Float> afterProfSICAvg = new ArrayList<Float>();
		
		ArrayList<Float> beforeProfAvg = new ArrayList<Float>();
		ArrayList<Float> afterProfAvg = new ArrayList<Float>();
		
		int[] interval = new int[3];
		if(state == "before"){
		    interval = makeBeforeInterval(list);	
		} else if(state == "after") {
			interval = makeAfterInterval(list);
		} else if(state == "during") {
			interval = makeDuringInterval(list);
		} else {
			System.out.println("hmmm we shouldn't be here!");
		}
		
		boundedValue value = new boundedValue();
		
		value.start = interval[0];
		value.mid = interval[1];
		value.end = interval[2];
		value.quarterSpan = value.end - value.start;		

		ArrayList<Firm> tmp = new ArrayList<Firm>();
		
		for(int i = 0; i < list.size();i++){
			value.cusip = list.get(i).cusip;
			value.sic = list.get(i).sic;

			if( (utils.qM2.get(list.get(i).dateIndex) >= value.start) &&
				(utils.qM2.get(list.get(i).dateIndex) < value.mid))
			{				
				beforeTQAvg.add(Float.parseFloat(list.get(i).Tobins_Q));
				tmp = getFirmsInQuarterRangeWithSIC(value.start, value.mid, value.sic, state);				
				for(int k = 0; k < tmp.size(); k++){
					beforeTQSICAvg.add(Float.parseFloat(tmp.get(k).Tobins_Q));					
				}				
			}
			else if( (utils.qM2.get(list.get(i).dateIndex) > (value.mid +1)) &&
					 (utils.qM2.get(list.get(i).dateIndex) <= value.end))
			{				
				afterTQAvg.add(Float.parseFloat(list.get(i).Tobins_Q));
				tmp = getFirmsInQuarterRangeWithSIC((value.mid +1), value.end, value.sic, state);				
				for(int k = 0; k < tmp.size(); k++){
					afterTQSICAvg.add(Float.parseFloat(tmp.get(k).Tobins_Q));
				}				
			}			
		}
		
		float[] resultTQ = new float[3];
		resultTQ[0] = utils.averageN(beforeTQAvg);
		resultTQ[1] = utils.averageN(afterTQAvg);
		// 			 beforeAverage - afterAverage (interval)
		resultTQ[2] = (resultTQ[1] - resultTQ[0]) / value.quarterSpan;		
		value.beforeAverageTQFirm = resultTQ[0];		
		value.afterAverageTQFirm = resultTQ[1];		
		value.quarterlyIntervalTQDifference = resultTQ[2];		
		
		float[] resultSIC2 = new float[3];
		resultSIC2[0] = utils.averageN(beforeTQSICAvg);
		resultSIC2[1] = utils.averageN(afterTQSICAvg);
		//		 beforeAverage - afterAverage (interval)
		resultSIC2[2] = (resultSIC2[1] - resultSIC2[0]) / value.quarterSpan;		
		value.beforeAverageTQSIC = resultSIC2[0];
		value.afterAverageTQSIC = resultSIC2[1];
		value.quarterlyIntervalTQDifferenceSIC = resultSIC2[2];

		return value;
	}
	
	// Given an array list of floats, form a concatenated string and return it
	public static String getStringFromList(ArrayList<Float> list){
		String t = "";
		for(int i = 0; i < list.size(); i++){
			if(Float.isNaN((float)list.get(i))){
				//t += "";
			}
			else{
				t+=BigDecimal.valueOf((double)list.get(i))+",";
			}
		}
		return t;
	}
	
	// Performs a routine over all cusips
	public static Object[] doRoutine()
	{
		Object[] boundedFirmsObject = new Object[6];
		ArrayList<Firm> bkTmp;
		boundedValue valueTQ;
		
		ArrayList<boundedValue> list1 = new ArrayList<boundedValue>();
		ArrayList<boundedValue> list2 = new ArrayList<boundedValue>();
		ArrayList<boundedValue> list3 = new ArrayList<boundedValue>();
		
		ArrayList<String> cusips = econo.cusipList;
		
		for(int i = 0;i<cusips.size();i++)
		{
			list1.add(checkNaNForBV("before", list1, econo.BeforeTree.get(cusips.get(i)), cusips.get(i)));
			list2.add(checkNaNForBV("during", list2,  econo.DuringTree.get(cusips.get(i)), cusips.get(i)));
			list3.add(checkNaNForBV("after", list3,  econo.AfterTree.get(cusips.get(i)), cusips.get(i)));			
		}
		
		boundedFirmsObject[0] = list1; // list 1 is vals[0] -> before
		boundedFirmsObject[1] = list2; // list 2 is vals[1] -> during
		boundedFirmsObject[2] = list3; // list 3 is vals[2] -> after
		
		return boundedFirmsObject;
	}	
	
	// Check not a number for a bounded value entry
	public static boundedValue checkNaNForBV(String state, ArrayList<boundedValue> bList, ArrayList<Firm> tmp, String cu)
	{
		boundedValue value = new boundedValue();
		if(tmp != null)
		{
			value  = new boundedValue();
			value = findAverage(tmp, state);		
			value.state = state;
			if(
					!(
					(Float.isNaN((value.afterAverageTQFirm))) &&
					(Float.isNaN((value.beforeAverageTQFirm)))
					)){
				
				return value;
			}
		}		
		return new boundedValue();
	}
	
	// Write all the results from intermediate step to respective files
	// RUTGER COMMENT: the values generated in the prototype are seperated into three categories: before (vals (0)) , during (vals(1)) and after (vals(2))
	// HOwever for some (NOT ALL...) analysis sake it is better to have those values generated per CUSIP.
	// THUS per row, thus per CUSIP; the following columns exist: beforeAVETQ, afterAVETQ, QintdifTQ (these three are all for the before category), the same three for the during category, same for after; FOR the SIC we have the same three values; before AVETQ, afterAVETQ and QintdifTQ (for each category:before, during, after)
	//the values I am talking about are 17-26; we dont have to use the profatibility values since the TQ are enough to test the robustness of the program
	
	public static void writeQuarterlyIntervalDiff(Object valList)
	{
		Object[] vals = (Object[]) valList;			
		
		ArrayList<boundedValue> boundedBeforeBKVals = ((ArrayList<boundedValue>) vals[0]);
		ArrayList<boundedValue> boundedDuringBKVals = ((ArrayList<boundedValue>) vals[1]);
		ArrayList<boundedValue> boundedAfterBKVals = ((ArrayList<boundedValue>) vals[2]);		
		
		String filePath = "OverallDataSet";
		
		String[] categories = {"cusip", "firmTqIntervalDiff", "sicTqIntervalDiff", "TimeBlock"};		
		String[] dataTypes  = {"STRING", "NUMERIC", "NUMERIC", "NUMERIC"};
		
		String[][] types = new String[2][categories.length];			
		types[0] = categories;
		types[1] =  dataTypes;	
	}
	
	public static void writeResultMatrix(Object valList, String resultFile) throws IOException
	{
		Object[] vals = (Object[]) valList;		
		ArrayList<boundedValue> boundedBeforeBKVals = ((ArrayList<boundedValue>) vals[0]);
		ArrayList<boundedValue> boundedDuringBKVals = ((ArrayList<boundedValue>) vals[1]);
		ArrayList<boundedValue> boundedAfterBKVals = ((ArrayList<boundedValue>) vals[2]);			
		ArrayList<boundedValue> temp;		
		ArrayList<String> listo = new ArrayList<String>();		
		
		resTree = new BTree<String, ArrayList<boundedValue>>();		
		treeToWrite = new BTree<String, resultMatrix>();		
		
		// Before 
		for(int i = 0;  i < boundedBeforeBKVals.size(); i++){			
			if(treeToWrite.get(boundedBeforeBKVals.get(i).cusip) == null){
				listo.add(boundedBeforeBKVals.get(i).cusip);
				treeToWrite.put(boundedBeforeBKVals.get(i).cusip, new resultMatrix());
			}			
			if(resTree.get(boundedBeforeBKVals.get(i).cusip) == null){
				temp = new ArrayList<boundedValue>();
				temp.add(boundedBeforeBKVals.get(i));
				resTree.put(boundedBeforeBKVals.get(i).cusip, temp);
			} else {
				temp = resTree.get(boundedBeforeBKVals.get(i).cusip);
				temp.add(boundedBeforeBKVals.get(i));
				resTree.put(boundedBeforeBKVals.get(i).cusip, temp);
			}		
		}
		// During
		for(int i = 0;  i < boundedDuringBKVals.size(); i++){			
			if(treeToWrite.get(boundedDuringBKVals.get(i).cusip) == null){
				listo.add(boundedDuringBKVals.get(i).cusip);
				treeToWrite.put(boundedDuringBKVals.get(i).cusip, new resultMatrix());
			}			
			if(resTree.get(boundedDuringBKVals.get(i).cusip) == null){
				temp = new ArrayList<boundedValue>();
				temp.add(boundedDuringBKVals.get(i));
				resTree.put(boundedDuringBKVals.get(i).cusip, temp);
			} else {
				temp = resTree.get(boundedDuringBKVals.get(i).cusip);
				temp.add(boundedDuringBKVals.get(i));
				resTree.put(boundedDuringBKVals.get(i).cusip, temp);
			}		
		}
		// After
		for(int i = 0;  i < boundedAfterBKVals.size(); i++){			
			if(treeToWrite.get(boundedAfterBKVals.get(i).cusip) == null){				
				listo.add(boundedAfterBKVals.get(i).cusip);
				treeToWrite.put(boundedAfterBKVals.get(i).cusip, new resultMatrix());
			}			
			if(resTree.get(boundedAfterBKVals.get(i).cusip) == null){
				temp = new ArrayList<boundedValue>();
				temp.add(boundedAfterBKVals.get(i));
				resTree.put(boundedAfterBKVals.get(i).cusip, temp);				
			} else {
				temp = resTree.get(boundedAfterBKVals.get(i).cusip);
				temp.add(boundedAfterBKVals.get(i));
				resTree.put(boundedAfterBKVals.get(i).cusip, temp);
			}		
		}		
		
		// For all cusips we have found
		for(int i = 0; i < listo.size(); i++){			
			ArrayList<boundedValue> val = resTree.get(listo.get(i));			
			resultMatrix tmpMat = new resultMatrix();	
			
			for(int j = 0; j < val.size(); j++){		
				// Before
				if(val.get(j).state == "before"){					
					tmpMat = treeToWrite.get(listo.get(i));					
					tmpMat.ave_after_before = val.get(j).afterAverageTQFirm;
					tmpMat.ave_before_before = val.get(j).beforeAverageTQFirm;
					tmpMat.qint_diff_before = val.get(j).quarterlyIntervalTQDifference;
					tmpMat.sic_ave_after_before = val.get(j).afterAverageTQSIC;
					tmpMat.sic_ave_before_before = val.get(j).beforeAverageTQSIC;
					tmpMat.sic_qint_diff_before = val.get(j).quarterlyIntervalTQDifferenceSIC;					
					treeToWrite.put(listo.get(i), tmpMat);					
				}
				// During
				if(val.get(j).state == "during"){					
					tmpMat = treeToWrite.get(listo.get(i));					
					tmpMat.ave_after_during = val.get(j).afterAverageTQFirm;
					tmpMat.ave_before_during = val.get(j).beforeAverageTQFirm;
					tmpMat.qint_diff_during = val.get(j).quarterlyIntervalTQDifference;
					tmpMat.sic_ave_after_during = val.get(j).afterAverageTQSIC;
					tmpMat.sic_ave_before_during = val.get(j).beforeAverageTQSIC;
					tmpMat.sic_qint_diff_during = val.get(j).quarterlyIntervalTQDifferenceSIC;					
					treeToWrite.put(listo.get(i), tmpMat);
				}
				// After
				if(val.get(j).state == "after"){					
					tmpMat = treeToWrite.get(listo.get(i));					
					tmpMat.ave_after_after = val.get(j).afterAverageTQFirm;
					tmpMat.ave_before_after = val.get(j).beforeAverageTQFirm;
					tmpMat.qint_diff_after = val.get(j).quarterlyIntervalTQDifference;
					tmpMat.sic_ave_after_after = val.get(j).afterAverageTQSIC;
					tmpMat.sic_ave_before_after = val.get(j).beforeAverageTQSIC;
					tmpMat.sic_qint_diff_after = val.get(j).quarterlyIntervalTQDifferenceSIC;					
					treeToWrite.put(listo.get(i), tmpMat);
				}						
			}				
		}
		resultMatrix t = new resultMatrix();
		for(int i = 0; i < listo.size(); i ++){			
			String val = "";			
			val += listo.get(i) + ","; // cusip			
			t = treeToWrite.get(listo.get(i));
			 
			// FIRM
			//		Before
			val += t.ave_before_before + ",";
			val += t.ave_after_before + ",";
			val += t.qint_diff_before + ",";
			//		During
			val += t.ave_before_during + ",";
			val += t.ave_after_during + ",";
			val += t.qint_diff_during + ",";
			//		After
			val += t.ave_before_after + ",";
			val += t.ave_after_after + ",";
			val += t.qint_diff_after + ",";
			
			// SIC
			//		Before
			val += t.sic_ave_before_before + ",";
			val += t.sic_ave_after_before + ",";
			val += t.sic_qint_diff_before + ",";
			//		During
			val += t.sic_ave_before_during + ",";
			val += t.sic_ave_after_during + ",";
			val += t.sic_qint_diff_during + ",";
			//		After
			val += t.sic_ave_before_after + ",";
			val += t.sic_ave_after_after + ",";
			val += t.sic_qint_diff_after;			 
			utils.writeList(resultFile, val);
		}		
	}

	// Main function
	public static void main(String[] args) throws IOException 
	{
		System.out.println("HERE WE GO");
		mat = new resultMatrix();		
		//setup elements
		ReadFile f = new ReadFile(path);		
		econo = f.getEconomy();
		econo.createFirmTransitionObj(econo.cusipList);		
		Object[] vals = doRoutine();
		
		//define file paths  TBD are these necessary?  I'm commenting them out
		
		String tobinsqResultFile = econo.filePath+"TOBINSQresults\\resultFile.txt";
		String atResultFile = econo.filePath+"ATresults\\resultFile.txt";		
		String oibdpResultFile = econo.filePath+"OIBDPresults\\resultFile.txt";
		String ppegtResultFile = econo.filePath+"PPEGTresults\\resultFile.txt";
		String mveResultFile = econo.filePath+"MVEresults\\resultFile.txt";
		String profResultFile = econo.filePath+"PROFresults\\resultFile.txt";
		String saleResultFile = econo.filePath+"SALEresults\\resultFile.txt";
		String prccResultFile = econo.filePath+"PRCCresults\\resultFile.txt";		
		String atoResultFile = econo.filePath+"ATOresults\\resultFile.txt";
		String nwResultFile = econo.filePath+"NETWORTHresults\\resultFile.txt";
		
		//  Change resultFile = X to any of the above.		
		String resultFile = atoResultFile;		
		
		//perform econ exploration		
		writeQuarterlyIntervalDiff(vals);		
		writeResultMatrix(vals, resultFile);
	}	
}