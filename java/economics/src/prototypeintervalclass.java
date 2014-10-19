

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


class boundedValue{
	
	int start;
	int mid;
	int end;	
	float beforeAverageFirm;
	float afterAverageFirm;
	
	float beforeAverageSIC;
	float afterAverageSIC;
	
	float firmSicBeforeDifference;
	float firmSicAfterDifference;
	
	float quarterlyIntervalDifference;
	
	String sic;
	String cusip;
	
	String state;
	
	public boundedValue()
	{
		start = 0;
		mid = 0;
		end = 0;
		
		state = "";
		
		beforeAverageFirm = 0;
		afterAverageFirm = 0;
		
		beforeAverageSIC=0;
		afterAverageSIC=0;
		
		sic = "";
		cusip = "";
	}
	
	
	
}


public class prototypeintervalclass {
	
	static String path = "C:\\Users\\Jeff\\Desktop\\econTools\\java\\economics\\src\\";

	static ArrayList<Firm> firms = new ArrayList<Firm>();
	static Economy econo;
	static EconUtils utils = new EconUtils(path);
	static int cusips = 100;
	static int timeBlock = 3;
	static int dataPoints = 120;
	static ArrayList<ArrayList<ArrayList<Firm>>> firmTimeseries;
	
	
	public static ArrayList<Firm> getFirmsInQuarterRangeWithSIC(int start, int end, String sic)
	{		
		ArrayList<ArrayList<Firm>> firmsInQuarterRange = new ArrayList<ArrayList<Firm>>();
		
		for(int i = (start-1); i < (end-1); i++)
		{
			firmsInQuarterRange.add(econo.quarterTree.get(i));
		}
		
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
						firmsWithSIC.add(firmsInQuarterRange.get(i).get(j));
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
		System.out.println(list.get(finalIndex-1).datadate);
		System.out.println("List size: "+finalIndex);
		
		int last = utils.qM2.get(utils.dM2.get((list.get(list.size()-1).datadate)));
		int first = utils.qM2.get(utils.dM2.get((list.get(0).datadate)));		
		int firstlast = (first+last)/2;
		System.out.println("Interval start: "+first+", mid: "+firstlast+", end: "+last);
		int[] x = new int[3];
		x[0]=first;
		x[1]=firstlast;
		x[2]=last;		
		return x;	
	}
	
	public static boundedValue findAverages(ArrayList<Firm> list){
		
		ArrayList<Float> beforeAvg = new ArrayList<Float>();
		ArrayList<Float> afterAvg = new ArrayList<Float>();
		
		int[] interval = makeinterval(list);
		
		boundedValue value = new boundedValue();
		
		int start = interval[0];
		int mid = interval[1];
		int end = interval[2];
		
		value.start = interval[0];
		value.mid = interval[1];
		value.end = interval[2];
		
		for(int i = 0; i < list.size();i++){
			
			if(Float.parseFloat(list.get(i).ppegtq) == 0)
			{
				
			}
			else{
			
				if( (utils.qM2.get(utils.dM2.get((list.get(i).datadate))) >= start) &&
					(utils.qM2.get(utils.dM2.get((list.get(i).datadate))) < mid))
				{				
					beforeAvg.add(Float.parseFloat(list.get(i).ppegtq));
				}
				if( (utils.qM2.get(utils.dM2.get((list.get(i).datadate))) >= mid) &&
					(utils.qM2.get(utils.dM2.get(list.get(i).datadate)) <= end))
				{				
					afterAvg.add(Float.parseFloat(list.get(i).ppegtq));
				}
			}
		}
		
		float[] result = new float[3];
		result[0] = utils.sumN(beforeAvg) / beforeAvg.size();
		result[1] = utils.sumN(afterAvg) / afterAvg.size();
		result[2] = (result[1] - result[0]) / (float) (end - start);	
		
		value.beforeAverageFirm = result[0];		
		value.afterAverageFirm = result[1];
		
		value.quarterlyIntervalDifference = result[2];
		
		value.cusip = list.get(0).cusip;
		value.sic = list.get(0).sic;
		
		ArrayList<Firm> beforeSIC = getFirmsInQuarterRangeWithSIC(value.start, value.mid, list.get(0).sic);
		ArrayList<Firm> afterSIC = getFirmsInQuarterRangeWithSIC(value.mid, value.end, list.get(0).sic);
		
		value.beforeAverageSIC = averageList(beforeSIC);
		value.afterAverageSIC = averageList(afterSIC);
		
		value.firmSicBeforeDifference = (value.beforeAverageSIC - value.beforeAverageFirm);
		value.firmSicAfterDifference = (value.afterAverageSIC - value.afterAverageFirm);
		
		
		return value;

	}
	
	public static float averageList(ArrayList<Firm> list)
	{
		float res = 0;
		for(int i = 0;i<list.size();i++)
		{
			res += Float.parseFloat(list.get(i).ppegtq);
		}
		
		return (res / list.size());	
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
	
	
	public static Object[] doRoutine()
	{
		Object[] boundedFirmsObject = new Object[3];
		ArrayList<Firm> bkTmp;
		boundedValue value;
		
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
				value  = new boundedValue();
				value = findAverages(bkTmp);		
				value.state = "before";
				list1.add(value);
			}
			bkTmp = new ArrayList<Firm>();
			bkTmp = econo.DuringTree.get(
					cusips.get(
							i));
			if(bkTmp!=null){
					
				value  = new boundedValue();
				value = findAverages(bkTmp);	
				value.state = "during";
				list2.add(value);
			}
			bkTmp = new ArrayList<Firm>();
			bkTmp = econo.AfterTree.get(
								cusips.get(
										i));
			if(bkTmp!=null){	
				value  = new boundedValue();
				value = findAverages(bkTmp);	
				value.state = "after";
				list3.add(value);
			}
			
		}		
		boundedFirmsObject[0] = list1;
		boundedFirmsObject[1] = list2;
		boundedFirmsObject[2] = list3;
		
		return boundedFirmsObject;
	}
	
	public static void writeResult(Object valList, String outFile) throws IOException
	{
		System.out.println("Start  |  Mid  |  End  |  averageDifference  |  cusip  |  sic");
		ArrayList<boundedValue> vals = (ArrayList<boundedValue>) valList;
		for(int i = 0;i<vals.size();i++)
		{
			double averageDifference = 0;
			if(Float.isNaN(vals.get(i).firmSicBeforeDifference) ){
				if(Float.isNaN(vals.get(i).firmSicAfterDifference) ){
					averageDifference = -123.456;
				} else {
					averageDifference = vals.get(i).firmSicAfterDifference;
				}
			}
			else if(Float.isNaN(vals.get(i).firmSicAfterDifference) ){
				if(Float.isNaN(vals.get(i).firmSicBeforeDifference) ){
					averageDifference = -123.456;
				} else {
					averageDifference = vals.get(i).firmSicBeforeDifference;
				}
			}
			else {
				averageDifference = (vals.get(i).firmSicBeforeDifference + vals.get(i).firmSicAfterDifference) / 2;
			}
			String text = vals.get(i).start+"  |  "+
					vals.get(i).mid+"  |  "+
					vals.get(i).end+" |  "+
					averageDifference+"   |   "+
					vals.get(i).cusip+"  |  "+
					vals.get(i).sic;
			
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
		
		float[] quarters;			
		ArrayList<float[]> firmBeforeValues = new ArrayList<float[]>();	
		ArrayList<float[]> firmDuringValues = new ArrayList<float[]>();	
		ArrayList<float[]> firmAfterValues = new ArrayList<float[]>();	
		for(int i = 0; i < cusips.size(); i++)
		{
			quarters = new float[120];
			
			if(queryBefore.get(cusips.get(i)) != null)
			{
				for(int j = 0; j < queryBefore.get(cusips.get(i)).size(); j++)
				{
					if(queryBefore.get(cusips.get(i)).get(j).state == "before")
					{
						int s = queryBefore.get(cusips.get(i)).get(j).start;
						int e = queryBefore.get(cusips.get(i)).get(j).end;
					
						for(int a = (s-1); a <= (e-1); a++)
						{
							if( Float.isNaN(queryBefore.get(cusips.get(i)).get(j).quarterlyIntervalDifference))
							{
								quarters[a] = 0;
							}	
							else 
							{
								quarters[a] = queryBefore.get(cusips.get(i)).get(j).quarterlyIntervalDifference;
							}
						}				
					
						firmBeforeValues.add(quarters);
					}
					if(queryDuring.get(cusips.get(i)).get(j).state == "during")
					{
						int s = queryDuring.get(cusips.get(i)).get(j).start;
						int e = queryDuring.get(cusips.get(i)).get(j).end;
					
						for(int a = (s-1); a <= (e-1); a++)
						{
							if( Float.isNaN(queryDuring.get(cusips.get(i)).get(j).quarterlyIntervalDifference))
							{
								quarters[a] = 0;
							}
							else 
							{
								quarters[a] = queryDuring.get(cusips.get(i)).get(j).quarterlyIntervalDifference;
							}
						}				
					
						firmDuringValues.add(quarters);
					}
					
					if(queryAfter.get(cusips.get(i)).get(j).state == "after")
					{
						int s = queryAfter.get(cusips.get(i)).get(j).start;
						int e = queryAfter.get(cusips.get(i)).get(j).end;
					
						for(int a = (s-1); a <= (e-1); a++)
						{
							if( Float.isNaN(queryAfter.get(cusips.get(i)).get(j).quarterlyIntervalDifference))
							{
								quarters[a] = 0;
							}
							else 
							{
								quarters[a] = queryAfter.get(cusips.get(i)).get(j).quarterlyIntervalDifference;
							}
						}				
					
						firmAfterValues.add(quarters);
					}
				}
			}
			else
			{
				System.out.println("Undiscovered cusip :(");
			}
		}	
		printMatrix(firmBeforeValues);
		printMatrix(firmDuringValues);
		printMatrix(firmAfterValues);
		
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
		
		writeResult(vals[0], before);
		
		writeResult(vals[1], during);
		
		writeResult(vals[2], after);
		
		
		ArrayList<float[]> result = constructMatrix(vals);
		
		//printMatrix(result);
		
		
		
		//ArrayList<ArrayList<ArrayList<Firm>>> yourObject = createFirmintervaldifferencetransobject(firms);
		
		//System.out.println(yourObject.toString());
	}
		
	

	}


