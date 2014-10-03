

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class prototypeintervalclass {
	static ArrayList<Integer> kinterval = new ArrayList<Integer>();
	
	static ArrayList<Firm> firms = new ArrayList<Firm>();
	static Economy econo;
	static EconUtils utils = new EconUtils();
	
	// take average on both sides of 3 element interval to return an arraylist
	// of floats, the 'before midpoint average' and 'after midpoint average'
	
	public static int[] makeinterval(ArrayList<Firm> list){
		
		int finalIndex = list.size();
		System.out.println(list.get(finalIndex-1).datadate);
		System.out.println("List size: "+finalIndex);
		
		int last = utils.qM2.get(Integer.parseInt(list.get(list.size()-1).datadate));
		int first = utils.qM2.get(Integer.parseInt(list.get(0).datadate));		
		int firstlast = (first+last)/2;
		System.out.println("Interval start: "+first+", mid: "+firstlast+", end: "+last);
		int[] x = new int[3];
		x[0]=first;
		x[1]=firstlast;
		x[2]=last;		
		return x;	
	}
	
	public static float[] findAverages(ArrayList<Firm> list){
		
		ArrayList<Float> beforeAvg = new ArrayList<Float>();
		ArrayList<Float> afterAvg = new ArrayList<Float>();
		
		int[] interval = makeinterval(list);
		
		int start = interval[0];
		int mid = interval[1];
		int end = interval[2];
		
		for(int i = 0; i < list.size();i++){
			
			if(Float.parseFloat(list.get(i).ppegtq) == 0)
			{
				
			}
			else{
			
				if( (utils.qM2.get(Integer.parseInt(list.get(i).datadate)) >= start) &&
					(utils.qM2.get(Integer.parseInt(list.get(i).datadate)) < mid))
				{				
					beforeAvg.add(Float.parseFloat(list.get(i).ppegtq));
				}
				if( (utils.qM2.get(Integer.parseInt(list.get(i).datadate)) >= mid) &&
					(utils.qM2.get(Integer.parseInt(list.get(i).datadate)) <= end))
				{				
					afterAvg.add(Float.parseFloat(list.get(i).ppegtq));
				}
			}
		}
		
		float[] result = new float[3];
		result[0] = utils.sumN(beforeAvg) / beforeAvg.size();
		result[1] = utils.sumN(afterAvg) / afterAvg.size();
		result [2] = (result[1] - result[0]) / (float) (end - start);	
		
		return result;
		
	}

	public static ArrayList<Firm> readexample (String file){
		
		System.out.println("REadcrsp");
		Firm firm = null;
		ArrayList<Firm> fList = new ArrayList<Firm>();
		String[] values;
		ArrayList<String> sics = new ArrayList<String>();
		ArrayList<Integer> qtrs = new ArrayList<Integer>();
	    try {
	        BufferedReader in = new BufferedReader(new FileReader(file));
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
	    
	    return fList;
	        	
	}
	// call firmlist
	public static ArrayList<ArrayList<ArrayList<Firm>>> createFirmintervaldifferencetransobject(ArrayList<Firm> cusips) {
		// TODO Auto-generated method stub
		
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
	
	
	
	public static void main(String[] args) {
	
		String filename = "C:\\Users\\Rutger\\Desktop\\ECON REPO\\econTools\\java\\economics\\src\\results\\onefirmexample.txt";				

		
		System.out.println("Adding integers to list");
	
		kinterval.add(2);
		kinterval.add(4);
		kinterval.add(6);
		kinterval.add(9);
		kinterval.add(10);
		kinterval.add(14);
	
		firms = readexample(filename);
		//System.out.println(kinterval.toString());
		
		
		float[] result = findAverages(firms );
		
		
		System.out.println("before average" +result[0] +" after average" +result[1] + "differencequarterly" +result[2]);
	
		ReadFile f = new ReadFile();
		econo = f.getEconomy();
		
		//YOUR PROTOTYPE HERE
		ArrayList<ArrayList<ArrayList<Firm>>> yourObject = createFirmintervaldifferencetransobject(firms);
		
		System.out.println(yourObject.toString());
	}
		
	

	}


