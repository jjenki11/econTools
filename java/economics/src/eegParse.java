package test;


import java.util.*;
import java.io.*;

public class eegParse {
	
	
	//static String outFile = "C:/Users/jeff/Desktop/SPIE 2014/Experiment Executed/Chuck/trials/";
	static String path = "C:/Users/jeff/Desktop/SPIE 2014/Experiment Executed/Chuck/trials/";
	static double offset = 1399507200000.0;
	static Boolean processEegFile = true;
	static String eegFileName = "C:/Users/jeff/Desktop/SPIE 2014/Experiment Executed/Chuck/chucktrials.csv";
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		List<String> fileList = null;
		if(!processEegFile)
			fileList = getFilenames(path);
		else
			fileList = getProcessedFileNames(path);
		int size = fileList.size();
		for(int i=0;i<size;i++){
			readCSV(fileList.get(i), processEegFile);
		}
	}
	
	public static void readCSV(String filename, Boolean processEegFile) throws FileNotFoundException{
		String text = "";
        Scanner scanner;
        int index = 0;
        String[] set = new String[5];
        long startDate = Long.MAX_VALUE;
        long endDate = Long.MIN_VALUE;
		try {
			scanner = new Scanner(new File(path+filename));	
			System.out.println("HERE:" + path+filename);
	        while(scanner.hasNext()){
				String line = scanner.nextLine();
				set = line.split(",");
				String fn = (filename.substring(0, filename.length() - 4));
				System.out.println("LINE:" + line + ", INDEX:" + index);
				//converts files to EEG file
				if (!processEegFile) {
					if (index == 0) {
						System.out.println("THERE:" + set[2]);
						text += set[0] + ",";
						System.out.println(path + fn);
						String[] x = new String[5];
						x[0] = set[0];
						x[1] = "ignored";
						x[2] = set[2];
						x[3] = "clicked";
						x[4] = set[4];
						set = x;
						text += convertDate(set[2]) + ",";
					} else {
						text += set[0] + ",";
						System.out.println(path + filename);
						text += convertDate(set[1]) + ",";
					}
					text += set[3] + ",";
					text += set[4];
					System.out.println(text);

					writeList(path + fn + ".txt", text);
					text = "";
				}else{
					//get the current date / assume good format
					long cdate = Long.parseLong(set[1]);
					if(cdate < startDate)
						startDate = cdate;
					if(cdate > endDate)
						endDate = cdate;
				}
	        	index ++;
	        }
	        scanner.close();			
	        if(processEegFile){
	        	extractEegDataForSession(eegFileName, startDate, endDate, (filename.substring(0, filename.length() - 4)));
	        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String[] clean(final String[] v) {
		int r, w;
		final int n = r = w = v.length;
		while (r > 0) {
			final String s = v[--r];
			if (!s.equals("null")) {
				v[--w] = s;
			}
		}
		return Arrays.copyOfRange(v, w, n);
	}
	
	public static String extractEegDataForSession(String eegFullFileName, long startDate, long endDate, String sessionName){
		//open the eeg file
		try {
			String outputFileName = eegFullFileName.substring(0, eegFullFileName.length() - 4) + "-" + sessionName + ".txt";
			Scanner scanner = new Scanner(new File(eegFullFileName));	
	        while(scanner.hasNext()){
	        	String line = scanner.nextLine();
	        	String[] data = clean(line.split(","));
	        	//1399557482,11904,128,44224,33024,26368,54400,8704,10112
	        	//String[] good = new String[9];
	        	//good
	        	//get date time
	        	long dateFromEegFile = Long.parseLong(data[0]) * 1000;
	        	if(dateFromEegFile > startDate && dateFromEegFile <= endDate){
	        		writeList(outputFileName, line);
	        	}
	        }
	        return "Success.";
	    }catch(IOException ex){
	    	System.out.println("Failed to process file " + eegFullFileName);
	    }
		return "Writing file succeeded.";
		
	}
	
	
	public static String convertDate(String theDate){
		System.out.println("inthere" + theDate);
		//=1399507200000 + INT(MID(B1,12,2)) * 60*60*1000 + INT(MID(B1, 15,2)) * 60 * 1000 + INT(MID(B1, 18, 2)) * 1000 + INT(MID(B1, 21, 3))
		System.out.println(theDate);
		Double hour = (Double.parseDouble(theDate.substring(11,13)));
		Double min = Double.parseDouble(theDate.substring(14,16));
		Double sec = Double.parseDouble(theDate.substring(17,19));
		Double mil = Double.parseDouble(theDate.substring(20,23));
		//System.out.println(hour+", "+min+", "+sec+", "+mil);
				
		//String x = 
		return
				Long.toString( 
						((long)(offset + (hour*60*60*1000) + (min*60*1000) + (sec*1000) + mil))
					);		
	}
	
	public static List<String> getProcessedFileNames(String dir){
		List<String> results = new ArrayList<String>();
		File[] files = new File(dir).listFiles(
					new FilenameFilter(){
						@Override
						public boolean accept(File dir, String name) {
				            return name.toLowerCase().endsWith(".txt");
				        }
					}
				);

		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;		
	}
	
	public static List<String> getFilenames(String dir){
		List<String> results = new ArrayList<String>();
		File[] files = new File(dir).listFiles();

		for (File file : files) {
		    if (file.isFile()) {
		        results.add(file.getName());
		    }
		}
		return results;
	}
	
	public static void writeList( String filename, String text ) throws IOException{
    	Writer out = new BufferedWriter(new FileWriter(filename, true));
    	out.append(text);
    	out.write("\r\n");
    	out.close();				
	}	
	
}
