import java.util.*;


public class Stats {

	int entries;
	ArrayList<Double> Xdata;
	double mean;
	double variance;
	double skewness;
	double kurtosis;
	double predicted;
	
	public Stats(){
		
		Xdata = new ArrayList<Double>();
		mean = 0;
		variance = 0;
		skewness = 0;
		kurtosis = 0;
		predicted = 0;
	}
	public Stats(ArrayList<Double> d){
		Xdata = d;
		mean = mean(d);
		variance = variance(d);
		skewness = skewness(d);
		kurtosis = kurtosis(d);
	}
	
	public double mean(ArrayList<Double> list){
		double sum=0;
		for(int i=0;i<list.size();i++){
			sum+=list.get(i);
		}
		return (sum/list.size());
	}
	
	public double variance(ArrayList<Double> list){
		
		double mu = Math.pow(mean(list), 2.0);
		list = powList(list, 2);		
		return mean(list) - mu;	
	}
	
	public double skewness(ArrayList<Double> list){
		double mu= mean(list);
		double vr= variance(list);
		double mu3= Math.pow(mean(list), 3.0);
		ArrayList<Double> data3 = powList(list, 3); 
		return mean(data3) - 3*vr*mu - mu3;		
	}
	public double kurtosis(ArrayList<Double> list){
		double RN = 0;
		double mu= mean(list);
		double mu2= Math.pow(mean(list), 2.0);
		double mu3= Math.pow(mean(list), 3.0);
		double mu4= Math.pow(mean(list), 4.0);
		double vr=variance(list);
		double sk= skewness(list);
		ArrayList<Double> data2 = powList(list, 2);
		ArrayList<Double> data3 = powList(list, 3);
		ArrayList<Double> data4 = powList(list, 4);
		
		double xj4 = mean(data4)
				- 4*mean(data3)*mu
				+ 6*mean(data2)*mu2
				- 4*mean(list)*mu3
				+ mu4;		
		RN = 4*mu*sk + 6*vr*mu2 + mu4 + 3*Math.pow(vr,2.0);		
		return (xj4 - RN);		
	}			
	
	public ArrayList<Double> powList(ArrayList<Double> list, double power){
		ArrayList<Double> powed = list;
		for(int i=0;i<list.size();i++){
			powed.set(i, Math.pow(list.get(i), power));
		}
		return powed;
	}
	
	public double[] statList(ArrayList<Double> list){
		double[] values = new double[4];
		values[0] = mean(list);
		values[1] = variance(list);
		values[2] = skewness(list);
		values[3] = kurtosis(list);
		return values;
	}
	
	
	
}
