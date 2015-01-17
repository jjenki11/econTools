package test;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

//  Data structure to hold intermediate results for our Econ evaluation


/**
 * 
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
 * 
 * 
 * 
 *
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
	float beforeAverageProfFirm;
	float afterAverageProfFirm;
	float beforeAverageTQSIC;
	float afterAverageTQSIC;
	float beforeAverageProfSIC;
	float afterAverageProfSIC;			
	float quarterlyIntervalTQDifference; 
	float quarterlyIntervalProfDifference;		
	float quarterlyIntervalTQDifferenceSIC;
	float quarterlyIntervalProfDifferenceSIC;
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
		beforeAverageProfFirm=0;
		afterAverageProfFirm=0;		
		beforeAverageTQSIC=0;
		afterAverageTQSIC=0;		
		beforeAverageProfSIC=0;
		afterAverageProfSIC=0;		
		quarterlyIntervalTQDifference=0;
		quarterlyIntervalProfDifference=0;		
		quarterlyIntervalTQDifferenceSIC=0;
		quarterlyIntervalProfDifferenceSIC=0;
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
	public static ArrayList<Firm> getFirmsInQuarterRangeWithSIC(int start, int end, String sic)
	{				
		ArrayList<ArrayList<Firm>> firmsInQuarterRange = new ArrayList<ArrayList<Firm>>();
		firmsInQuarterRange = utils.createGCRangeList(econo, start, end);
		ArrayList<Firm> firmsWithSIC = new ArrayList<Firm>();		
		for(int i = 0; i < firmsInQuarterRange.size(); i ++)
		{
			if((firmsInQuarterRange.get(i) != null))
			{
				for(int j = 0; j < firmsInQuarterRange.get(i).size(); j ++)
				{
					//check if the firm evaluated has desired sic
					if(firmsInQuarterRange.get(i).get(j).sic == sic)
					{
						if(
								Float.isNaN(Float.parseFloat(firmsInQuarterRange.get(i).get(j).Profitability)) ||
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
	public static int[] makeinterval(ArrayList<Firm> list)
	{
		int last = utils.qM2.get(utils.dM2.get((list.get(list.size()-1).datadate)));
		int first = utils.qM2.get(utils.dM2.get((list.get(0).datadate)));		
		int mid = (first+last)/2;
		int[] x = new int[3];
		x[0]=first;
		x[1]=mid;
		x[2]=last;		
		return x;	
	}
	
	public static int[] makeDuringInterval(ArrayList<Firm> list)
	{
		Integer dis2 = 1;
		int first = 0;
		
		Integer dis = 10957;		
		int last = 0;
		
		if(utils.qM2.get(list.get(0).getBankrupcy().get(0).disposedIndex) != null){
			last = utils.qM2.get(list.get(0).getBankrupcy().get(0).disposedIndex);
		} else {
			last = dis;
		}
		if(utils.qM2.get(list.get(0).getBankrupcy().get(0).filedIndex) != null){
			first = utils.qM2.get(list.get(0).getBankrupcy().get(0).filedIndex);
		} else {
			first = dis2;
		}		
		System.out.println(list.get(0).cusip + ", " + list.get(0).getBankrupcy().get(0).filedIndex + ", " + list.get(0).getBankrupcy().get(0).disposedIndex);
		
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
		
		ArrayList<Float> beforeProfAvg = new ArrayList<Float>();
		ArrayList<Float> afterProfAvg = new ArrayList<Float>();
		int[] interval;
		if(state != "during"){
		    interval = makeinterval(list);		
		} else {
			interval = makeDuringInterval(list);
		}
		boundedValue value = new boundedValue();
		
		value.start = interval[0];
		value.mid = interval[1];
		value.end = interval[2];
		value.quarterSpan = (value.end - value.start);
		
		for(int i = 0; i < list.size();i++)
		{
			if( (utils.qM2.get(utils.dM2.get((list.get(i).datadate))) >= value.start) &&
				(utils.qM2.get(utils.dM2.get((list.get(i).datadate))) < value.mid))
			{				
				beforeTQAvg.add(Float.parseFloat(list.get(i).Tobins_Q));
				beforeProfAvg.add(Float.parseFloat(list.get(i).Profitability));
			}
			else if( (utils.qM2.get(utils.dM2.get((list.get(i).datadate))) >= value.mid) &&
				(utils.qM2.get(utils.dM2.get(list.get(i).datadate)) <= value.end))
			{				
				afterTQAvg.add(Float.parseFloat(list.get(i).Tobins_Q));
				afterProfAvg.add(Float.parseFloat(list.get(i).Profitability));
			}			
		}
		
		float[] resultTQ = new float[3];
		resultTQ[0] = utils.averageN(beforeTQAvg);
		resultTQ[1] = utils.averageN(afterTQAvg);
		// beforeAverage - afterAverage (interval)
		resultTQ[2] = (resultTQ[1] - resultTQ[0]) / value.quarterSpan;
		
		value.beforeAverageTQFirm = resultTQ[0];		
		value.afterAverageTQFirm = resultTQ[1];		
		value.quarterlyIntervalTQDifference = resultTQ[2];
		

		
		float[] resultProf = new float[3];
		resultProf[0] = utils.averageN(beforeProfAvg);
		resultProf[1] = utils.averageN(afterProfAvg);
		// beforeAverage - afterAverage (interval)
		resultProf[2] = (resultProf[1] - resultProf[0]) / value.quarterSpan;	
		
		value.beforeAverageProfFirm = resultProf[0];		
		value.afterAverageProfFirm = resultProf[1];		
		value.quarterlyIntervalProfDifference = resultProf[2];
		
		value.cusip = list.get(0).cusip;
		value.sic = list.get(0).sic;
		
		ArrayList<Firm> beforeSIC = getFirmsInQuarterRangeWithSIC(value.start, value.mid, list.get(0).sic);
		ArrayList<Firm> afterSIC = getFirmsInQuarterRangeWithSIC(value.mid, value.end, list.get(0).sic);
		
		value.beforeAverageTQSIC = utils.averageTQList(beforeSIC);
		value.afterAverageTQSIC = utils.averageTQList(afterSIC);		
		value.beforeAverageProfSIC = utils.averageProfList(beforeSIC);
		value.afterAverageProfSIC = utils.averageProfList(afterSIC);	
		
		value.quarterlyIntervalTQDifferenceSIC = (value.afterAverageTQSIC - value.beforeAverageTQSIC) / (float)value.quarterSpan;
		value.quarterlyIntervalProfDifferenceSIC = (value.afterAverageProfSIC - value.beforeAverageProfSIC) / (float)value.quarterSpan;
		
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
					(Float.isNaN((value.afterAverageProfFirm))) &&
					(Float.isNaN((value.beforeAverageProfFirm))) &&
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
	public static void writeQuarterlyIntervalDiff(Object valList, 
												  String outFile1, 
												  String outFile2, 
												  String outFile3, 
												  String outFile4,
												  String outFile5,
												  String outFile6,
												  String outFile7,
												  String outFile8,
												  String outFile9,
												  String outFile10,
												  String outFile11,
												  String outFile12) throws IOException
	{
		Object[] vals = (Object[]) valList;			
		
		ArrayList<boundedValue> boundedBeforeBKVals = ((ArrayList<boundedValue>) vals[0]);
		ArrayList<boundedValue> boundedDuringBKVals = ((ArrayList<boundedValue>) vals[1]);
		ArrayList<boundedValue> boundedAfterBKVals = ((ArrayList<boundedValue>) vals[2]);		
		
		String filePath = "OverallDataSet";
		
		String[] categories = {"cusip", "firmTqIntervalDiff", "firmProfIntervalDiff", "sicTqIntervalDiff", "sicProfIntervalDiff", "TimeBlock"};		
		String[] dataTypes  = {"STRING", "NUMERIC", "NUMERIC", "NUMERIC", "NUMERIC", "STRING"};
		
			String[][] types = new String[2][categories.length];			
			types[0] = categories;
			types[1] =  dataTypes;
			
		utils.constructARFFFile(filePath, types);
		
		utils.writeToARFFFile(evaluateFirmSicQuery(((ArrayList<boundedValue>) vals[0]), outFile1, outFile2, outFile3, outFile4, "BEFORE"), filePath);
		utils.writeToARFFFile(evaluateFirmSicQuery(((ArrayList<boundedValue>) vals[1]), outFile5, outFile6, outFile7, outFile8, "DURING"), filePath);
		utils.writeToARFFFile(evaluateFirmSicQuery(((ArrayList<boundedValue>) vals[2]), outFile9, outFile10, outFile11, outFile12, "AFTER"), filePath);
		
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
				resTree.get(boundedBeforeBKVals.get(i).cusip).add(boundedBeforeBKVals.get(i));
			}		
		}
		
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
				resTree.get(boundedDuringBKVals.get(i).cusip).add(boundedDuringBKVals.get(i));
			}		
		}
		
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
				resTree.get(boundedAfterBKVals.get(i).cusip).add(boundedAfterBKVals.get(i));
			}		
		}
		
		
		for(int i = 0; i < listo.size(); i++){
			
			ArrayList<boundedValue> val = resTree.get(listo.get(i));
			
			resultMatrix tmpMat = new resultMatrix();
			
			for(int j = 0; j < val.size(); j++){
				
				
				if(val.get(j).state == "before"){
					
					tmpMat = treeToWrite.get(listo.get(i));
					
					tmpMat.ave_after_before = val.get(j).afterAverageTQFirm;
					tmpMat.ave_before_before = val.get(j).beforeAverageTQFirm;
					tmpMat.qint_diff_before = val.get(j).quarterlyIntervalTQDifference;
					tmpMat.sic_ave_after_before = val.get(j).afterAverageTQSIC;
					tmpMat.sic_ave_before_before = val.get(j).beforeAverageTQFirm;
					tmpMat.sic_qint_diff_before = val.get(j).quarterlyIntervalTQDifferenceSIC;
					
					treeToWrite.put(listo.get(i), tmpMat);
					
				}
				if(val.get(j).state == "during"){
					
					tmpMat = treeToWrite.get(listo.get(i));
					
					tmpMat.ave_after_during = val.get(j).afterAverageTQFirm;
					tmpMat.ave_before_during = val.get(j).beforeAverageTQFirm;
					tmpMat.qint_diff_during = val.get(j).quarterlyIntervalTQDifference;
					tmpMat.sic_ave_after_during = val.get(j).afterAverageTQSIC;
					tmpMat.sic_ave_before_during = val.get(j).beforeAverageTQFirm;
					tmpMat.sic_qint_diff_during = val.get(j).quarterlyIntervalTQDifferenceSIC;
					
					treeToWrite.put(listo.get(i), tmpMat);
				}
				if(val.get(j).state == "after"){
					
					tmpMat = treeToWrite.get(listo.get(i));
					
					tmpMat.ave_after_after = val.get(j).afterAverageTQFirm;
					tmpMat.ave_before_after = val.get(j).beforeAverageTQFirm;
					tmpMat.qint_diff_after = val.get(j).quarterlyIntervalTQDifference;
					tmpMat.sic_ave_after_after = val.get(j).afterAverageTQSIC;
					tmpMat.sic_ave_before_after = val.get(j).beforeAverageTQFirm;
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
			 
			 val += t.ave_before_before + ",";
			 val += t.ave_after_before + ",";
			 val += t.qint_diff_before + ",";
			 
			 val += t.ave_before_during + ",";
			 val += t.ave_after_during + ",";
			 val += t.qint_diff_during + ",";
			
			 val += t.ave_before_after + ",";
			 val += t.ave_after_after + ",";
			 val += t.qint_diff_after + ",";
			 
			 val += t.sic_ave_before_before + ",";
			 val += t.sic_ave_after_before + ",";
			 val += t.sic_qint_diff_before + ",";
			 
			 val += t.sic_ave_before_during + ",";
			 val += t.sic_ave_after_during + ",";
			 val += t.sic_qint_diff_during + ",";
			
			 val += t.sic_ave_before_after + ",";
			 val += t.sic_ave_after_after + ",";
			 val += t.sic_qint_diff_after + ",";
			 
			 utils.writeList(resultFile, val);
		}
		
		
		
		
		/*
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
		*/
		
		
		
	}
	
	// Perform query
	public static String evaluateFirmSicQuery(ArrayList<boundedValue> vals, String file1, String file2, String file3, String file4, String period) throws IOException
	{
		boolean skip = false; float x = 0; float y = 0; float a = 0; float b = 0;
		ArrayList<Float> tqFirm = new ArrayList<Float>();
		ArrayList<Float> profFirm = new ArrayList<Float>();
		ArrayList<Float> tqSIC = new ArrayList<Float>();
		ArrayList<Float> profSIC = new ArrayList<Float>();
		
		ArrayList<String> arffLine = new ArrayList<String>();
		
		String all = "";
		
		String txt = "";
		
		for(int i = 0; i < vals.size();i++){
			
			
			
			
			
			skip = false;
			txt = "";
			x = (float)vals.get(i).quarterlyIntervalTQDifference;
			y = (float)vals.get(i).quarterlyIntervalProfDifference;
			
			if(
					(Float.isNaN(x) || Float.isNaN(y)) || 
					((x==0.0f) || (y==0.0f))  
			  ){
				skip = true;
			}
			else{}	
			//sic
			a = (float)vals.get(i).afterAverageTQSIC - (float)vals.get(i).beforeAverageTQSIC / (float)vals.get(i).quarterSpan;
			b = (float)vals.get(i).afterAverageProfSIC - (float)vals.get(i).beforeAverageProfSIC / (float)vals.get(i).quarterSpan;
			if(
					(Float.isNaN(a) || Float.isNaN(b)) || 
					((a==0.0f) || (b==0.0f))  ||
					((float)vals.get(i).quarterSpan == 0.0f)
			  ){
				skip = true;
			}
			else{
				if(!skip){			
					
					
					txt = (vals.get(i).cusip + "," + x + "," + y + "," + a + "," + b + "," + period + "\r\n");
					all += txt;
					//tqFirm.add(x);
					//profFirm.add(y);
					//tqSIC.add(a);
					//profSIC.add(b);
				}
			}	
		}		
		//utils.writeList(file1, getStringFromList(tqFirm));
		//utils.writeList(file2, getStringFromList(profFirm));
		//utils.writeList(file3, getStringFromList(tqSIC));
		//utils.writeList(file4, getStringFromList(profSIC));		
		
		return all;		
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
		
		//define file paths
		String beforeTQDist = econo.filePath+"results\\beforeTQDist.txt"; 			//1
		String beforeProfDist = econo.filePath+"results\\beforeProfDist.txt"; 		//2
		String beforeSICTQDist = econo.filePath+"results\\beforeTQSICDist.txt"; 	//3
		String beforeSICProfDist = econo.filePath+"results\\beforeProfSICDist.txt"; //4		
		String duringTQDist = econo.filePath+"results\\duringTQDist.txt"; 			//5
		String duringProfDist = econo.filePath+"results\\duringProfDist.txt"; 		//6
		String duringSICTQDist = econo.filePath+"results\\duringTQSICDist.txt"; 	//7
		String duringSICProfDist = econo.filePath+"results\\duringProfSICDist.txt"; //8		
		String afterTQDist = econo.filePath+"results\\afterTQDist.txt";   			//9		
		String afterProfDist = econo.filePath+"results\\afterProfDist.txt";   		//10
		String afterSICTQDist = econo.filePath+"results\\afterTQSICDist.txt";   	//11		
		String afterSICProfDist = econo.filePath+"results\\afterProfSICDist.txt";   //12
		
		String resultFile = econo.filePath+"results2\\resultFile.txt";

		//perform econ exploration
		writeQuarterlyIntervalDiff(vals, 
								   beforeTQDist,  		//1
								   beforeProfDist,  	//2
								   beforeSICTQDist,   	//3
								   beforeSICProfDist,	//4 
								   duringTQDist,		//5 
								   duringProfDist, 		//6
								   duringSICTQDist,		//7
								   duringSICProfDist, 	//8
								   afterTQDist,			//9
								   afterProfDist,		//10
								   afterSICTQDist, 		//11
								   afterSICProfDist); 	//12	
		
		writeResultMatrix(vals, resultFile);
	}	
}


