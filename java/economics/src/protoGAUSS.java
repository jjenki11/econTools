import java.util.ArrayList;


public class protoGAUSS {
	
	// calculating the mean of whatever list
	float averageN(ArrayList<Float> list){
		float sum = sumN(list);
		return (sum / list.size());
	}
	
	float sumN(ArrayList<Float> list){
		float x = 0;
		for(int i = 0; i< list.size(); i++){
			x+=(list.get(i));
		}		
		return x;
	}	
	
	 float averagewhatever(ArrayList<Firm> list){
		float x = 0;
		for(int i = 0; i< list.size(); i++){
			x+=Double.parseDouble(list.get(i).Tobins_Q);
		}
		return (x / list.size());		
	}
	
	// calculating the std.dev of whatever list
	double standarddevwhatever (Arraylist<Firm> list)
		double y = 0 
			for(int i = 0; i < list.length; i++){
				y+=Double.parseDouble(list.get(i).Tobins_Q);
	
		}
		return (y = Math.sqrt(Math.pow((list(i) - x),2));
		
	
	// return phi(x) = standard Gaussian pdf
    public static double phi(double x) {
        return Math.exp(-x*x / 2) / Math.sqrt(2 * Math.PI);
    }

	
}
