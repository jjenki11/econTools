// Mapping.js


var Mapping = function()
{
	return {	
		dateMap: 				function(fPath)
										{
											var dmap = new HashMap();
											var values = [];
											var fileName = fPath+"dateMap.txt";
											try
											{
												var fs = require('fs');
												fs.readFile(filename, function(err, data) {
													if(err) throw err;
													var array = data.toString().split("\n");
														
													for(var i in array) 
													{
														values = array[i].split("	");
														dmap.put(values[1], values[0]);
													}
												});
											}
											catch (err) 
											{
        								console.log('There was an error reading date map.');
        								console.log('Error description: ', err.message);
        								return ;
    									}
											console.log("Date mapping success");
											return dmap;
										},
		quartermap: 		function(fPath)
										{
											var qmap = new HashMap();
											var values = [];
											var fileName = fPath+"quarters.txt";
											try
											{
												var fs = require('fs');
												fs.readFile(filename, function(err, data) {
													if(err) throw err;
													var array = data.toString().split("\n");
														
													for(var i in array) 
													{
														values = array[i].split(",");
														qmap.put(values[0], values[1]);
													}
												});
											}
											catch (err) 
											{
        								console.log('There was an error reading quarter map.');
        								console.log('Error description: ', err.message);
        								return ;
    									}
											console.log("Date mapping success");
											return qmap;
										},
	};

};
/*	
	public BTree<String, Integer> cusipMap(String filename){
		BTree<String,Integer> cusipMap =new BTree<String,Integer>();

		String[] values=new String[2];
		//String filename = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\cusip_map.txt";
		//String filename1 = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\acquirer_data_reduced.txt";
		//String filename2 = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\bankrupcyreduced.txt";
		//String filename3 = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\target_data_reduced.txt";
		//String gcFile = "C:\\Users\\Jeff\\Desktop\\Laptop-Migration\\econ_shiznot\\econ_project\\june_2013\\ECON PAPER\\Going_Concern_reduced.txt";
		Integer i = 0;
		try {
		    BufferedReader in = new BufferedReader(new FileReader(filename));
		    String str;
		    str = in.readLine();   
		    
		    while ((str = in.readLine()) != null) 
		    {
		        values = str.split(",");   
		        cusipMap.put(((String)values[0]), Integer.parseInt(values[1]));
		    	i++;
		    }
		    in.close();		    
		}
		catch (NullPointerException e){
			System.out.println("null");
		}
		catch (IOException e) 
		{
		    System.out.println("File Read Error");
		}
		System.out.println("Cusip mapping success");
		return cusipMap;
	}
*/
