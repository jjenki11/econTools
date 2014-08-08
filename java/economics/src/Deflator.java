import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class Deflator {
/*
	
	
void Deflator(){
	int date =0;
	float value = 0;
}

//Firm deflateValue(Firm f) throws IOException{
	public static void main(String[] args) throws IOException, IndexOutOfBoundsException{
	//Firm firm=f;
	BTree<Integer,String> deflatorMap = new BTree<Integer,String>();
	BTree<String,Integer> deflatorMap2 = new BTree<String,Integer>();

	ArrayList<Integer> 	dates=new ArrayList<Integer>();
	ArrayList<String> 	def_values=new ArrayList<String>();
	Mapping m = new Mapping();
	BTree<String, Integer> dM = m.dateMap();
	//BTree<Integer,String> conversionMap = new BTree<Integer,String>();
	
String filename = "C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\deflator_values.txt";
String file2 = "C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\crsp_dates.txt";
String[] values;
int locaiton=0;
int tempDate = 0;
try {
    BufferedReader in = new BufferedReader(new FileReader(filename));
    String str;
    str = in.readLine();    	
    while ((str = in.readLine()) != null) {
    	values=new String[2];     	
        values = str.split(",");  
        if(dM.get(values[0]) == null)
        {
        	System.exit(0);
        }
        else{
        	dates.add(dM.get(values[0]));
        	def_values.add(values[1]);
        }

        
        //deflatorMap.put(tempDate, values[1]);
       // deflatorMap2.put(values[1], tempDate);
        //firm =new Firm();
    	//firm.gvkey=values[0];	               
}
    System.out.println("date size: "+ dates.size());
    System.out.println("defs size: "+ def_values.size());

} catch (IOException e) {
    System.out.println("File Read Error");
}
ArrayList<String> the_list = new ArrayList<String>();
	Writer out;
String val;
int counter=0;
int tmpDate=0;
double numEntries = 572122.0;
int firms = 0;
try {
    BufferedReader in = new BufferedReader(new FileReader(file2));
    String str;
    str = in.readLine();    	
    while ((str = in.readLine()) != null) {
    	//values=new String[1];     
    	val= "";
    	counter = 0;
        //val = str.split(",");    
    	val = str;
        //System.out.println(dM.get(val));
        
        tmpDate = dM.get(val); //date is int value
        //System.out.print(firms+" -> ");
        //find closest int in map
       while((((Integer)tmpDate > (Integer)(dates.get(counter)))) && (counter < dates.size())){
    	   
    	  // System.out.print(tmpDate);    	   
    	   //System.out.print(" > " );
    	   //System.out.println(dates.get(counter));
    	   //System.out.println("counter: " + counter + ", datesize: " + dates.size() + ", defsize: " + def_values.size());
    	   counter++;
    	   if(counter == 119)
    	   {
    		  // counter++;
    		   break;
    		   //System.out.
    	   }
       }
       if(counter == def_values.size()){
    	   the_list.add(def_values.get(def_values.size()-1));
         //  System.out.println("date: " + tmpDate + ", deflator: " + def_values.get(def_values.size()-1));
       }
       else if (counter == 1){
       the_list.add(def_values.get(counter-1));
       //System.out.println("date: " + tmpDate + ", deflator: " + def_values.get(counter-1));
       }
       else { the_list.add(def_values.get(counter));
      // System.out.println("date: " + tmpDate + ", deflator: " + def_values.get(counter));
       }

       
       //System.out.println(dates.get(counter) + "  " + def_values.get(counter));

             firms++;
             System.out.println((firms/numEntries)*100 + " % done");
    }

} catch (IOException e) {
    System.out.println("File Read Error");
}
//firms=0;
for(int i = 0; i <= the_list.size(); i++){
	
    out = new BufferedWriter(new FileWriter("C:\\Users\\jeff\\Desktop\\econ_project\\october_2013\\output\\crsp_date_output3.txt", true));
	out.append(the_list.get(i));
	//System.out.println(the_list.get(i));
	
	out.write("\r\n");
	out.close();
	System.out.println((i/numEntries)*100 + " % done");
}



//return firm;
}
    	
    	
  */  	
}
