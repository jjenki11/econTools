package test;



import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;


class boundedValue{
	
	int start;
	int mid;
	int end;	
		
	float beforeAverageTQSIC;
	float afterAverageTQSIC;
	float beforeAverageProfSIC;
	float afterAverageProfSIC;	
	
	float beforeAverageProfFirm;
	float afterAverageProfFirm;
	float beforeAverageTQFirm;
	float afterAverageTQFirm;	
	
	float firmSicBeforeProfDifference;
	float firmSicAfterProfDifference;
	float firmSicBeforeTQDifference;
	float firmSicAfterTQDifference;
	
	float quarterlyIntervalTQDifference; 
	float quarterlyIntervalProfDifference;
	
	float zScoreTQ;
	float zScoreProf;
	
	ArrayList<Float> zScoreBeforeTQ;
	ArrayList<Float> zScoreAfterTQ;
	ArrayList<Float> zScoreBeforeProf;
	ArrayList<Float> zScoreAfterProf;
	
	String sic;
	String cusip;
	
	String state;
	
	int quarterSpan;
	
	public boundedValue()
	{
		start = 0;
		mid = 0;
		end = 0;
		
		state = "";
		
		beforeAverageTQFirm = 0;
		afterAverageTQFirm = 0;		
		beforeAverageProfFirm=0;
		afterAverageProfFirm=0;
		
		firmSicBeforeTQDifference = 0;
		firmSicAfterTQDifference = 0;
		firmSicBeforeProfDifference = 0;
		firmSicAfterProfDifference = 0;		
		
		beforeAverageTQSIC=0;
		afterAverageTQSIC=0;		
		beforeAverageProfSIC=0;
		afterAverageProfSIC=0;
		
		quarterlyIntervalTQDifference=0;
		quarterlyIntervalProfDifference=0;
		
		zScoreTQ=0;
		zScoreProf=0;
		zScoreBeforeTQ=new ArrayList<Float>();
		zScoreAfterTQ=new ArrayList<Float>();
		zScoreBeforeProf=new ArrayList<Float>();
		zScoreAfterProf=new ArrayList<Float>();
		
		sic = "";
		cusip = "";
		quarterSpan = 0;
	}
	
	
	
}


public class prototypeintervalclass {
	
	static String path = "C:\\Users\\blackhole\\Desktop\\econRepo\\java\\economics\\src\\";

	static ArrayList<Firm> firms = new ArrayList<Firm>();
	static Economy econo;
	static EconUtils utils = new EconUtils(path);
	static int cusips = 100;
	static int timeBlock = 3;
	static int dataPoints = 120;
	static ArrayList<ArrayList<ArrayList<Firm>>> firmTimeseries;
	
	
	public static ArrayList<Firm> getFirmsInQuarterRangeWithSIC(int start, int end, String sic)
	{		
		//ArrayList<ArrayList<Firm>> firmsInQuarterRange = new ArrayList<ArrayList<Firm>>();
		
		ArrayList<ArrayList<Firm>> firmsInQuarterRange = new ArrayList<ArrayList<Firm>>();
		firmsInQuarterRange = utils.createGCRangeList(econo, start, end);
		/*
		for(int i = (start-1); i < (end-1); i++)
		{
			firmsInQuarterRange.add(econo.quarterTree.get(i));
		}
		*/
		ArrayList<Firm> firmsWithSIC = new ArrayList<Firm>();
		
		for(int i = 0; i < firmsInQuarterRange.size(); i ++)
		{
			if((firmsInQuarterRange.get(i) != null)	
			)
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
						else{
							firmsWithSIC.add(firmsInQuarterRange.get(i).get(j));
						}
						
					}				
				}
			}
		}
		
		return firmsWithSIC;
	}
	
	// take average on both sides of 3 element interval to return an arraylist
	// of floats, the 'before midpoint average' and 'after midpoint average'
	
	public static int[] makeinterval(ArrayList<Firm> list){
		
		int finalIndex = list.size();
		//System.out.println(list.get(finalIndex-1).datadate);
		//System.out.println("List size: "+finalIndex);
		
		int last = utils.qM2.get(utils.dM2.get((list.get(list.size()-1).datadate)));
		int first = utils.qM2.get(utils.dM2.get((list.get(0).datadate)));		
		int firstlast = (first+last)/2;
		//System.out.println("Interval start: "+first+", mid: "+firstlast+", end: "+last);
		int[] x = new int[3];
		x[0]=first;
		x[1]=firstlast;
		x[2]=last;		
		return x;	
	}
	
	public static boundedValue findAverage(ArrayList<Firm> list){
		
		ArrayList<Float> beforeTQAvg = new ArrayList<Float>();
		ArrayList<Float> afterTQAvg = new ArrayList<Float>();
		
		ArrayList<Float> beforeProfAvg = new ArrayList<Float>();
		ArrayList<Float> afterProfAvg = new ArrayList<Float>();
		
		//ArrayList<Float> bigTQList = new ArrayList<Float>();
		//ArrayList<Float> bigProfList = new ArrayList<Float>();
		
		
		int[] interval = makeinterval(list);
		
		boundedValue value = new boundedValue();
		
		int start = interval[0];
		int mid = interval[1];
		int end = interval[2];
		
		value.start = interval[0];
		value.mid = interval[1];
		value.end = interval[2];
		
		for(int i = 0; i < list.size();i++){
			if( (utils.qM2.get(utils.dM2.get((list.get(i).datadate))) >= start) &&
				(utils.qM2.get(utils.dM2.get((list.get(i).datadate))) < mid))
			{				
				beforeTQAvg.add(Float.parseFloat(list.get(i).Tobins_Q));
				beforeProfAvg.add(Float.parseFloat(list.get(i).Profitability));
				
				//bigTQList.add(Float.parseFloat(list.get(i).Tobins_Q));
				//bigProfList.add(Float.parseFloat(list.get(i).Profitability));
			}
			else if( (utils.qM2.get(utils.dM2.get((list.get(i).datadate))) >= mid) &&
				(utils.qM2.get(utils.dM2.get(list.get(i).datadate)) <= end))
			{				
				afterTQAvg.add(Float.parseFloat(list.get(i).Tobins_Q));
				afterProfAvg.add(Float.parseFloat(list.get(i).Profitability));
				
				//bigTQList.add(Float.parseFloat(list.get(i).Tobins_Q));
				//bigProfList.add(Float.parseFloat(list.get(i).Profitability));
			}			
		}
		
		float[] resultTQ = new float[3];
		resultTQ[0] = utils.averageN(beforeTQAvg);
		resultTQ[1] = utils.averageN(afterTQAvg);
		// beforeAverage - afterAverage (interval)
		resultTQ[2] = (resultTQ[1] - resultTQ[0]) / ((end - start));
		
		value.beforeAverageTQFirm = resultTQ[0];		
		value.afterAverageTQFirm = resultTQ[1];		
		value.quarterlyIntervalTQDifference = resultTQ[2];
		
		float[] resultProf = new float[3];
		resultProf[0] = utils.averageN(beforeProfAvg);
		resultProf[1] = utils.averageN(afterProfAvg);
		// beforeAverage - afterAverage (interval)
		resultProf[2] = (resultProf[1] - resultProf[0]) / ((end - start));	
		
		value.beforeAverageProfFirm = resultProf[0];		
		value.afterAverageProfFirm = resultProf[1];		
		value.quarterlyIntervalProfDifference = resultProf[2];
		
		value.cusip = list.get(0).cusip;
		value.sic = list.get(0).sic;
		
		ArrayList<Firm> beforeSIC = getFirmsInQuarterRangeWithSIC(value.start, value.mid, list.get(0).sic);
		ArrayList<Firm> afterSIC = getFirmsInQuarterRangeWithSIC(value.mid, value.end, list.get(0).sic);
		
		value.beforeAverageTQSIC = averageTQList(beforeSIC);
		value.afterAverageTQSIC = averageTQList(afterSIC);		
		value.beforeAverageProfSIC = averageProfList(beforeSIC);
		value.afterAverageProfSIC = averageProfList(afterSIC);			
		
		value.quarterSpan = (value.end - value.start);
		
		System.out.println("after minus before average sic TQ: "+(value.beforeAverageTQSIC));
		System.out.println("after minus before average sic PROF: "+(value.beforeAverageProfSIC));
		
		
		if(Float.isNaN((value.beforeAverageTQSIC))){}
		else{
			value.firmSicBeforeTQDifference = (value.beforeAverageTQSIC);
		}
		if(Float.isNaN((value.afterAverageTQSIC))){}
		else{
			value.firmSicAfterTQDifference = (value.afterAverageTQSIC);
		}
		if(Float.isNaN((value.beforeAverageProfSIC))){}
		else{
			value.firmSicBeforeProfDifference = (value.beforeAverageProfSIC);
		}		
		if(Float.isNaN((value.afterAverageProfSIC))){}
		else{
			value.firmSicAfterProfDifference = (value.afterAverageProfSIC);
		}
		
		return value;
	}
	
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
	
	public static float averageTQList(ArrayList<Firm> list)
	{
		float res = 0;
		int badValues = 0;
		for(int i = 0;i<list.size();i++)
		{
			if(Float.isNaN(Float.parseFloat(list.get(i).Tobins_Q))){
				badValues++;
			}
			else{
				res += Float.parseFloat(list.get(i).Tobins_Q);
				
			}
		}
		
		return (res / (list.size()+1));	
	}
	
	public static float averageProfList(ArrayList<Firm> list)
	{
		float res = 0;
		int badValues = 0;
		for(int i = 0;i<list.size();i++)
		{
			if(Float.isNaN(Float.parseFloat(list.get(i).Profitability))){
				badValues++;
			}
			else{
				res += Float.parseFloat(list.get(i).Profitability);
			}
			
		}
		System.out.println((float)(res / (list.size()+1)));
		System.out.println((float)((list.size())));
		return (res / (list.size()+1));	
	}
	

	public static ArrayList<ArrayList<ArrayList<Firm>>> firmintervaldifferenceseries (){
		
		firmTimeseries = econo.createFirmTransitionObj(econo.cusipList);
		System.out.println("Readcrsp");
		Firm firm = null;
		ArrayList<Firm> fList = new ArrayList<Firm>();
		String[] values;
		ArrayList<String> sics = new ArrayList<String>();
		ArrayList<Integer> qtrs = new ArrayList<Integer>();
		/*
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(firmintervaldifferenceseries));
	        String str;
	        str = in.readLine();    	
	        while ((str = in.readLine()) != null) {
	        	values=new String[8]; 
	        	
	            values = str.split(",");
	            
	            firm =new Firm();
	            //if (Float.parseFloat(values[2]) == 0){
	            	
	            //}
	            //else{
		        	firm.datadate=values[0];
		        	firm.cusip=values[1];
		        	firm.ppegtq=values[2];
		        	firm.Tobins_Q=values[3];
		        	firm.sic=values[4];
		        	//firm.sic=values[5];
		        	//firm.txditcq=values[6];
		        	//firm.sic=values[7];	
		        	System.out.println("Found entry on date "+values[0]+" with ppegt = "+values[2]);
		        	fList.add(firm);
	        	//}
	        }
	        in.close();
	        //System.exit(0);
	    } catch (IOException e) {
	        System.out.println("File Read Error");
	    }	
	    */
	    //return firmintervaldifferenceseries;
	    return null;    	
	}
	// call firmlist
	public static ArrayList<ArrayList<ArrayList<Firm>>> createFirmintervaldifferencetransobject(ArrayList<Firm> cusips) {
		
		
		ArrayList<Firm> beforeListintervaldifference = new ArrayList<Firm>();
		ArrayList<Firm> duringListintervaldifference = new ArrayList<Firm>();
		ArrayList<Firm> afterListintervaldifference= new ArrayList<Firm>();

		ArrayList<ArrayList<ArrayList<Firm>>> firmintervaldifferenceseries = new ArrayList<ArrayList<ArrayList<Firm>>>();
		ArrayList<ArrayList<Firm>> cusipintervaldifferenceseries;

		for(int i = 0; i<cusips.size();i++)
		{
			cusipintervaldifferenceseries = new ArrayList<ArrayList<Firm>>();
			
			beforeListintervaldifference = econo.BeforeTree.get(cusips.get(i).cusip);
			duringListintervaldifference = econo.DuringTree.get(cusips.get(i).cusip);
			afterListintervaldifference  = econo.AfterTree.get(cusips.get(i).cusip);
			
			utils.printList(beforeListintervaldifference);
			utils.printList(duringListintervaldifference);
			utils.printList(afterListintervaldifference);
			
			cusipintervaldifferenceseries.add(beforeListintervaldifference);
			cusipintervaldifferenceseries.add(duringListintervaldifference);
			cusipintervaldifferenceseries.add(afterListintervaldifference);
			
			firmintervaldifferenceseries.add(cusipintervaldifferenceseries);
			
		}

		return firmintervaldifferenceseries;
	}
	
	
	// getting SIC interval differences
	
	public ArrayList<Firm> getFirmsInSICandQuarters(ArrayList<Firm> firmintervaldifferenceseries, Integer SIC, Integer quarter){
		
		ArrayList<Firm> FirmsInSIC = new ArrayList<Firm>();
		
		for(int i=0; i<firmintervaldifferenceseries.size();i++){
			
		}
		return FirmsInSIC;
	}
	
	
	
	// prof and tq for firms coming out of bk
	
	
	public static Object[] doRoutine()
	{
		Object[] boundedFirmsObject = new Object[6];
		ArrayList<Firm> bkTmp;
		boundedValue valueTQ;
		boundedValue valueProf;
		
		//tobins q
		ArrayList<boundedValue> list1 = new ArrayList<boundedValue>();
		ArrayList<boundedValue> list2 = new ArrayList<boundedValue>();
		ArrayList<boundedValue> list3 = new ArrayList<boundedValue>();
		
		ArrayList<String> cusips = econo.cusipList;
		
		for(int i = 0;i<cusips.size();i++)
		{
			bkTmp = new ArrayList<Firm>();
			bkTmp = econo.BeforeTree.get(
					cusips.get(
							i));
			if(bkTmp != null)
			{
				valueTQ  = new boundedValue();
				valueTQ = findAverage(bkTmp);		
				valueTQ.state = "before";
				if(
						!(
						(Float.isNaN((valueTQ.afterAverageProfFirm))) &&
						(Float.isNaN((valueTQ.beforeAverageProfFirm))) &&
						(Float.isNaN((valueTQ.afterAverageTQFirm))) &&
						(Float.isNaN((valueTQ.beforeAverageTQFirm)))
						)){
					list1.add(valueTQ);
				}
			}
			bkTmp = new ArrayList<Firm>();
			bkTmp = econo.DuringTree.get(
					cusips.get(
							i));
			if(bkTmp!=null){
					
				valueTQ  = new boundedValue();
				valueTQ = findAverage(bkTmp);	
				valueTQ.state = "during";
				if(
						!(
						(Float.isNaN((valueTQ.afterAverageProfFirm))) &&
						(Float.isNaN((valueTQ.beforeAverageProfFirm))) &&
						(Float.isNaN((valueTQ.afterAverageTQFirm))) &&
						(Float.isNaN((valueTQ.beforeAverageTQFirm)))
						)){
					list2.add(valueTQ);
				}
			}
			bkTmp = new ArrayList<Firm>();
			bkTmp = econo.AfterTree.get(
								cusips.get(
										i));
			if(bkTmp!=null){	
				valueTQ  = new boundedValue();
				valueTQ = findAverage(bkTmp);	
				valueTQ.state = "after";
				if(
						!(
						(Float.isNaN((valueTQ.afterAverageProfFirm))) &&
						(Float.isNaN((valueTQ.beforeAverageProfFirm))) &&
						(Float.isNaN((valueTQ.afterAverageTQFirm))) &&
						(Float.isNaN((valueTQ.beforeAverageTQFirm)))
						)){
					list3.add(valueTQ);
				}
			}
			
		}		
		boundedFirmsObject[0] = list1;
		boundedFirmsObject[1] = list2;
		boundedFirmsObject[2] = list3;
		
		return boundedFirmsObject;
	}
	
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
		// quarterlyIntervalTQDifference
		// quarterlyIntervalProfDifference
		Object[] vals = (Object[]) valList;	
		
		// list 1 is vals[0] -> before
		// list 2 is vals[1] -> during		
		
		ArrayList<Float> tqBeforeDiffs = new ArrayList<Float>();
		ArrayList<Float> profBeforeDiffs = new ArrayList<Float>();
		
		ArrayList<Float> tqDuringDiffs = new ArrayList<Float>();
		ArrayList<Float> profDuringDiffs = new ArrayList<Float>();
		
		ArrayList<Float> tqAfterDiffs = new ArrayList<Float>();
		ArrayList<Float> profAfterDiffs = new ArrayList<Float>();
		
		ArrayList<Float> tqBeforeSICDiffs = new ArrayList<Float>();
		ArrayList<Float> tqDuringSICDiffs = new ArrayList<Float>();
		ArrayList<Float> tqAfterSICDiffs = new ArrayList<Float>();
		ArrayList<Float> profBeforeSICDiffs = new ArrayList<Float>();
		ArrayList<Float> profDuringSICDiffs = new ArrayList<Float>();
		ArrayList<Float> profAfterSICDiffs = new ArrayList<Float>();
		
		// BEFORE BK
		for(int i = 0; i < ((ArrayList<boundedValue>) vals[0]).size();i++){
			float x = ((ArrayList<boundedValue>) vals[0]).get(i).quarterlyIntervalTQDifference;
			float y = ((ArrayList<boundedValue>) vals[0]).get(i).quarterlyIntervalProfDifference;
			if(Float.isNaN(x)){}
			else{
				tqBeforeDiffs.add(x);
			}
			if(Float.isNaN(y)){}
			else{
				profBeforeDiffs.add(y);
			}
			
			//sic
			float a = (float)((ArrayList<boundedValue>) vals[0]).get(i).afterAverageTQSIC - (float)((ArrayList<boundedValue>) vals[0]).get(i).beforeAverageTQSIC / (float)((ArrayList<boundedValue>) vals[0]).get(i).quarterSpan;
			float b = ((float)((ArrayList<boundedValue>) vals[0]).get(i).afterAverageProfSIC - (float)((ArrayList<boundedValue>) vals[0]).get(i).beforeAverageProfSIC) / (float)((ArrayList<boundedValue>) vals[0]).get(i).quarterSpan;
			System.out.println("BEFORE: a = "+a+" b = "+b+", Quarter span = "+(float)((ArrayList<boundedValue>) vals[0]).get(i).quarterSpan);
			if(Float.isNaN(a)){}
			else{
				tqBeforeSICDiffs.add(a);
			}
			if(Float.isNaN(b)){}
			else{
				profBeforeSICDiffs.add(b);
			}
		}
		
		// DURING BK
		for(int i = 0; i < ((ArrayList<boundedValue>) vals[1]).size();i++){
			float x = (float)((ArrayList<boundedValue>) vals[1]).get(i).quarterlyIntervalTQDifference;
			float y = (float)((ArrayList<boundedValue>) vals[1]).get(i).quarterlyIntervalProfDifference;
			if(Float.isNaN(x)){}
			else{
				tqDuringDiffs.add(x);
			}
			if(Float.isNaN(y)){}
			else{
				profDuringDiffs.add(y);
			}
			//sic
			float a = (float)((ArrayList<boundedValue>) vals[1]).get(i).afterAverageTQSIC - (float)((ArrayList<boundedValue>) vals[1]).get(i).beforeAverageTQSIC / (float)((ArrayList<boundedValue>) vals[1]).get(i).quarterSpan;
			float b = (float)((ArrayList<boundedValue>) vals[1]).get(i).afterAverageProfSIC - (float)((ArrayList<boundedValue>) vals[1]).get(i).beforeAverageProfSIC / (float)((ArrayList<boundedValue>) vals[1]).get(i).quarterSpan;
			System.out.println("DURING: a = "+a+" b = "+b+", Quarter span = "+(float)((ArrayList<boundedValue>) vals[1]).get(i).quarterSpan);
			if(Float.isNaN(a)){}
			else{
				tqDuringSICDiffs.add(a);
			}
			if(Float.isNaN(b)){}
			else{
				profDuringSICDiffs.add(b);
			}
		}		
		
		// AFTER BK
		for(int i = 0; i < ((ArrayList<boundedValue>) vals[2]).size();i++){
			float x = (float)((ArrayList<boundedValue>) vals[2]).get(i).quarterlyIntervalTQDifference;
			float y = (float)((ArrayList<boundedValue>) vals[2]).get(i).quarterlyIntervalProfDifference;
			if(Float.isNaN(x)){}
			else{
				tqAfterDiffs.add(x);
			}
			if(Float.isNaN(y)){}
			else{
				profAfterDiffs.add(y);
			}	
			//sic
			float a = (float)((ArrayList<boundedValue>) vals[2]).get(i).afterAverageTQSIC - (float)((ArrayList<boundedValue>) vals[2]).get(i).beforeAverageTQSIC / (float)((ArrayList<boundedValue>) vals[2]).get(i).quarterSpan;
			float b = (float)((ArrayList<boundedValue>) vals[2]).get(i).afterAverageProfSIC - (float)((ArrayList<boundedValue>) vals[2]).get(i).beforeAverageProfSIC / (float)((ArrayList<boundedValue>) vals[2]).get(i).quarterSpan;
			System.out.println("AFTER: a = "+a+" b = "+b+", Quarter span = "+(float)((ArrayList<boundedValue>) vals[2]).get(i).quarterSpan);
			if(Float.isNaN(a)){}
			else{
				tqAfterSICDiffs.add(a);
			}
			if(Float.isNaN(b)){}
			else{
				profAfterSICDiffs.add(b);
			}	
		}
		/*
			ArrayList<Float> tqB = new ArrayList<Float>();
			tqB = utils.calculateZScore(tqBeforeDiffs);
			ArrayList<Float> prB = new ArrayList<Float>();
			prB = utils.calculateZScore(profBeforeDiffs);
			ArrayList<Float> tqD = new ArrayList<Float>();
			tqD = utils.calculateZScore(tqDuringDiffs);
			ArrayList<Float> prD = new ArrayList<Float>();
			prD = utils.calculateZScore(profDuringDiffs);
		*/
		utils.writeList(outFile1, getStringFromList(tqBeforeDiffs));
		utils.writeList(outFile2, getStringFromList(tqDuringDiffs));		
		utils.writeList(outFile3, getStringFromList(tqAfterDiffs));
		
		utils.writeList(outFile4, getStringFromList(profBeforeDiffs));
		utils.writeList(outFile5, getStringFromList(profDuringDiffs));
		utils.writeList(outFile6, getStringFromList(profAfterDiffs));
		
		utils.writeList(outFile7, getStringFromList(tqBeforeSICDiffs));
		utils.writeList(outFile8, getStringFromList(tqDuringSICDiffs));
		utils.writeList(outFile9, getStringFromList(tqAfterSICDiffs));
		
		utils.writeList(outFile10, getStringFromList(profBeforeSICDiffs));
		utils.writeList(outFile11, getStringFromList(profDuringSICDiffs));
		utils.writeList(outFile12, getStringFromList(profAfterSICDiffs));
	}
	
	
	public static void writeResult(Object valList, String outFile) throws IOException
	{
		System.out.println("Start  |  Mid  |  End  |  averageTQDifference  |  averageProfDifference  |  cusip  |  sic");
		ArrayList<boundedValue> vals = (ArrayList<boundedValue>) valList;
		for(int i = 0;i<vals.size();i++)
		{
			double averageTQDifference = 0;
			double averageProfDifference = 0;
			//tobins q
			if(Float.isNaN(vals.get(i).firmSicBeforeTQDifference) ){
				if(Float.isNaN(vals.get(i).firmSicAfterTQDifference) ){
					averageTQDifference = -123.456;
				} else {
					averageTQDifference = vals.get(i).firmSicAfterTQDifference;					
				}
			}
			else if(Float.isNaN(vals.get(i).firmSicAfterTQDifference) ){
				if(Float.isNaN(vals.get(i).firmSicBeforeTQDifference) ){
					averageTQDifference = -123.456;
				} else {
					averageTQDifference = vals.get(i).firmSicBeforeTQDifference;
				}
			}
			else {
				averageTQDifference = (vals.get(i).firmSicBeforeTQDifference + vals.get(i).firmSicAfterTQDifference) / 2;
			}
			
			// profitability
			if(Float.isNaN(vals.get(i).firmSicBeforeProfDifference) ){
				if(Float.isNaN(vals.get(i).firmSicAfterProfDifference) ){
					averageProfDifference = -123.456;
				} else {					
					averageProfDifference = vals.get(i).firmSicAfterProfDifference;
				}
			}
			else if(Float.isNaN(vals.get(i).firmSicAfterProfDifference) ){
				if(Float.isNaN(vals.get(i).firmSicBeforeProfDifference) ){
					averageProfDifference = -123.456;
				} else {
					averageProfDifference = vals.get(i).firmSicBeforeProfDifference;
				}
			}
			else {
				averageProfDifference = (vals.get(i).firmSicBeforeProfDifference + vals.get(i).firmSicAfterProfDifference) / 2;
			}
			
			String text = 
					//vals.get(i).start+"  |  "+
					//vals.get(i).mid+"  |  "+
					//vals.get(i).end+" |  "+
					averageTQDifference+", "+
					averageProfDifference;//+"  |  "+
					//vals.get(i).cusip+"  |  "+
					//vals.get(i).sic;
			
			utils.writeList(outFile, text);
		}
	}
	
	public static ArrayList<float[]> constructMatrix(Object[] vals)
	{		
		ArrayList<boundedValue> before = (ArrayList<boundedValue>) vals[0];		//vals[0] is before bk
		ArrayList<boundedValue> during = (ArrayList<boundedValue>) vals[1];		//vals[1] is during bk
		ArrayList<boundedValue> after = (ArrayList<boundedValue>) vals[2];		//vals[2] is after  bk
		
		BTree<String, ArrayList<boundedValue>> queryBefore = new BTree<String, ArrayList<boundedValue>>();
		BTree<String, ArrayList<boundedValue>> queryDuring = new BTree<String, ArrayList<boundedValue>>();
		BTree<String, ArrayList<boundedValue>> queryAfter = new BTree<String, ArrayList<boundedValue>>();
		
		ArrayList<boundedValue> tmp;
		
		for(int i = 0; i < before.size(); i++)
		{
			if(queryBefore.get(before.get(i).cusip) != null)
			{
				queryBefore.get(before.get(i).cusip).add(before.get(i));
			}
			else
			{
				tmp = new ArrayList<boundedValue>();
				tmp.add(before.get(i));
				queryBefore.put(before.get(i).cusip,tmp);
			}
		}
		
		for(int i = 0; i < during.size(); i++)
		{
			if(queryDuring.get(during.get(i).cusip) != null)
			{
				queryDuring.get(during.get(i).cusip).add(during.get(i));
			}
			else
			{
				tmp = new ArrayList<boundedValue>();
				tmp.add(during.get(i));
				queryDuring.put(during.get(i).cusip,tmp);
			}
		}
		
		for(int i = 0; i < after.size(); i++)
		{
			if(queryAfter.get(after.get(i).cusip) != null)
			{
				queryAfter.get(after.get(i).cusip).add(after.get(i));
			}
			else
			{
				tmp = new ArrayList<boundedValue>();
				tmp.add(after.get(i));
				queryAfter.put(after.get(i).cusip,tmp);
			}
		}
		
		//make final obj
		ArrayList<String> cusips = econo.cusipList;		
		
		float[] quartersT = new float[120];	
		float[] quartersP = new float[120];;
		ArrayList<float[]> firmTQBeforeValues = new ArrayList<float[]>();	
		ArrayList<float[]> firmTQDuringValues = new ArrayList<float[]>();	
		ArrayList<float[]> firmTQAfterValues = new ArrayList<float[]>();	
		
		ArrayList<float[]> firmProfBeforeValues = new ArrayList<float[]>();	
		ArrayList<float[]> firmProfDuringValues = new ArrayList<float[]>();	
		ArrayList<float[]> firmProfAfterValues = new ArrayList<float[]>();
		
		for(int i = 0; i < cusips.size(); i++)
		{
			quartersT = new float[120];
			
			if(queryBefore.get(cusips.get(i)) != null)
			{
				for(int j = 0; j < queryBefore.get(cusips.get(i)).size(); j++)
				{
					if(queryBefore.get(cusips.get(i)).get(j) != null)
					{
						int s = queryBefore.get(cusips.get(i)).get(j).start;
						int e = queryBefore.get(cusips.get(i)).get(j).end;
					
						for(int a = (s-1); a <= (e-1); a++)
						{
							if( Float.isNaN(queryBefore.get(cusips.get(i)).get(j).quarterlyIntervalTQDifference))
							{
								quartersT[a] = 0;								
							}
							else 
							{
								quartersT[a] = queryBefore.get(cusips.get(i)).get(j).quarterlyIntervalTQDifference;
							}
							
							if( Float.isNaN(queryBefore.get(cusips.get(i)).get(j).quarterlyIntervalProfDifference))
							{
								quartersP[a] = 0;								
							}
							else
							{
								quartersP[a] = queryBefore.get(cusips.get(i)).get(j).quarterlyIntervalProfDifference;
							}
							
						}				
					
						firmTQBeforeValues.add(quartersT);
						firmProfBeforeValues.add(quartersP);
					}
				}
			}
			quartersT = new float[120];
			quartersP = new float[120];
			if(queryDuring.get(cusips.get(i)) != null)
			{
				for(int j = 0; j < queryDuring.get(cusips.get(i)).size(); j++)
				{		
					if(queryDuring.get(cusips.get(i)).get(j) != null)
					{
						int s = queryDuring.get(cusips.get(i)).get(j).start;
						int e = queryDuring.get(cusips.get(i)).get(j).end;
			
						for(int a = (s-1); a <= (e-1); a++)
						{
							if( Float.isNaN(queryDuring.get(cusips.get(i)).get(j).quarterlyIntervalTQDifference))
							{
								quartersT[a] = 0;
							}
							else 
							{
								quartersT[a] = queryDuring.get(cusips.get(i)).get(j).quarterlyIntervalTQDifference;
							}
							
							if( Float.isNaN(queryDuring.get(cusips.get(i)).get(j).quarterlyIntervalProfDifference))
							{
								quartersP[a] = 0;
							}
							else 
							{
								quartersP[a] = queryDuring.get(cusips.get(i)).get(j).quarterlyIntervalProfDifference;
							}
						}				
					
						firmTQDuringValues.add(quartersT);
						firmProfDuringValues.add(quartersP);						
					}
				}
			}
			quartersT = new float[120];
			quartersP = new float[120];
			if(queryAfter.get(cusips.get(i)) != null)
			{
				for(int j = 0; j < queryAfter.get(cusips.get(i)).size(); j++)
				{		
					if(queryAfter.get(cusips.get(i)).get(j) != null)
					{
						int s = queryAfter.get(cusips.get(i)).get(j).start;
						int e = queryAfter.get(cusips.get(i)).get(j).end;
					
						for(int a = (s-1); a <= (e-1); a++)
						{
							if( Float.isNaN(queryAfter.get(cusips.get(i)).get(j).quarterlyIntervalTQDifference))
							{
								quartersT[a] = 0;
							}
							else 
							{
								quartersT[a] = queryAfter.get(cusips.get(i)).get(j).quarterlyIntervalTQDifference;
							}
							
							if( Float.isNaN(queryAfter.get(cusips.get(i)).get(j).quarterlyIntervalProfDifference))
							{
								quartersP[a] = 0;
							}
							else 
							{
								quartersP[a] = queryAfter.get(cusips.get(i)).get(j).quarterlyIntervalProfDifference;
							}
						}				
					
						firmTQAfterValues.add(quartersT);		
						firmProfAfterValues.add(quartersP);
					}
				}
			}
			else
			{
				System.out.println("Undiscovered cusip :(");
			}
		}	
		printMatrix(firmTQBeforeValues);
		printMatrix(firmTQDuringValues);
		printMatrix(firmTQAfterValues);
		
		printMatrix(firmProfBeforeValues);
		printMatrix(firmProfDuringValues);
		printMatrix(firmProfAfterValues);
		
		return null;		
	}
	
	public static void printMatrix(ArrayList<float[]> vals)
	{
		
		for(int i = 0; i < vals.size(); i++){
			
			for(int j = 0; j < vals.get(i).length; j++){
				System.out.print(vals.get(i)[j]+", ");
			}
			System.out.println();
		}
	}
	
	
	public static void main(String[] args) throws IOException {
	
		
		
		
		//float[] result = findAverages(firms );
		
		
		//System.out.println("before average" +result[0] +" after average" +result[1] + "differencequarterly" +result[2]);
	
		ReadFile f = new ReadFile(path);
		econo = f.getEconomy();
		//firms = readexample((econo.createFirmTransitionObj(econo.cusipList)));
		econo.createFirmTransitionObj(econo.cusipList);
		//YOUR PROTOTYPE HERE
		
		System.out.println("HERE WE GO");
		Object[] vals = doRoutine();
		System.out.println("The size of our before result set: "+((ArrayList<Firm>) vals[0]).size());
		System.out.println("The size of our during result set: "+((ArrayList<Firm>) vals[1]).size());
		System.out.println("The size of our after result set: "+((ArrayList<Firm>) vals[2]).size());
		
		String before = econo.filePath+"results\\beforeAvgDifferece.txt";
		String during = econo.filePath+"results\\duringAvgDifferece.txt";
		String after = econo.filePath+"results\\afterAvgDifferece.txt";
		
		String beforeTQDist = econo.filePath+"results\\beforeTQDist.txt"; //1
		String duringTQDist = econo.filePath+"results\\duringTQDist.txt"; //2
		String afterTQDist = econo.filePath+"results\\afterTQDist.txt";   //3
		
		String beforeProfDist = econo.filePath+"results\\beforeProfDist.txt"; //4
		String duringProfDist = econo.filePath+"results\\duringProfDist.txt"; //5
		String afterProfDist = econo.filePath+"results\\afterProfDist.txt";   //6
		
		String beforeSICTQDist = econo.filePath+"results\\beforeTQSICDist.txt"; //7
		String duringSICTQDist = econo.filePath+"results\\duringTQSICDist.txt"; //8
		String afterSICTQDist = econo.filePath+"results\\afterTQSICDist.txt";   //9
		
		String beforeSICProfDist = econo.filePath+"results\\beforeProfSICDist.txt"; //10
		String duringSICProfDist = econo.filePath+"results\\duringProfSICDist.txt"; //11
		String afterSICProfDist = econo.filePath+"results\\afterProfSICDist.txt";   //12
		
		//writeResult(vals[0], before);		
		//writeResult(vals[1], during);		
		//writeResult(vals[2], after);
		
		// THIS IS WHAT I ADDED
		writeQuarterlyIntervalDiff(vals, 
								   beforeTQDist,  //1
								   duringTQDist,  //2
								   afterTQDist,   //3
								   beforeProfDist,//4 
								   duringProfDist,//5 
								   afterProfDist, //6
								   beforeSICTQDist,//7
								   duringSICTQDist, //8
								   afterSICTQDist,//9
								   beforeSICProfDist,//10
								   duringSICProfDist, //11
								   afterSICProfDist); //12		
		
		
	
		
		//ArrayList<float[]> result = constructMatrix(vals);		
		//printMatrix(result);
	}
		
	

	}


