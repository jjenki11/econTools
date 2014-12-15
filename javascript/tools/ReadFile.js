// ReadFile.js

var ReadFile = function(path)
{
	var ECONOMY;
	var text = "";
	var util = new EconUtils(path);
	var E = new Economy().init(util.getFilePath());
	
	
	return {
	
		init: 			function()
								{
									console.log("first file");
									var bankrupcies = util.getFilePath()+"brd_data_set2b.txt";
									var bkCusipFile = util.getFilePath()+"brd_cusips.txt";
									var filename = util.getFilePath()+"crsp_quarterly_and_yearly_large.txt";
									var bkOutputFile = util.getFilePath()+"bk_k_tq_qtr.txt";		
									var bkBeforeFile = util.getFilePath()+"results\\bk_before.txt";	
									var bkDuringFile = util.getFilePath()+"results\\bk_during.txt";
									var bkAfterFile = util.getFilePath()+"results\\bk_after.txt";
									var bkEverFile = util.getFilePath()+"results\\bk_ever.txt";
									var gcOutputFile = util.getFilePath()+"results\\gc_firms.txt";
									var outputFileArray = [bkBeforeFile, bkDuringFile, bkAfterFile, bkEverFile,	gcOutputFile];
									E = util.readCRSP(E, filename);		
									console.log("Creating Economy Table: done!");
									var bkList = util.readList(bkCusipFile); //ArrayList<String>
									E.cusipList = bkList;
									console.log("Reading bankrupcy data: done!");
									E.doBankrupcy(bankrupcies);
									
									console.log("GC COUNT: "+E.goingconcernCount);
									console.log("Bankrupt COUNT: "+E.bankruptCount);
									
									var l; //ArrayList<Firm>
									var gcCount = 0;
									var f; //Firm
									console.log(E.AllFirms.length);
									var idx=0;
									var bkCount = 0;
									var bkUniqueSize = 0;
									var bkFoundSize = 0;
									var tgCount = 0;
									var aqCount = 0;
									var found = false;
									var counter = [0,0,0,0];
									while(idx<E.AllFirms.size())
									{
										f = new Firm();
										f = E.AllFirms.get(idx);
										var perDone = (idx/E.AllFirms.length)*100;
										var xx = [];
										try 
										{
											xx = util.writeIfFound(E, bkList, f, outputFileArray);
										} 
										catch (err) 
										{
        							console.log('There was an error writing to the file in Readfile.');
        							console.log('Error description: ', err.message);
        							return ;
    								}
    								counter[0] += xx[0];
    								counter[1] += xx[1];
    								counter[2] += xx[2];
    								counter[3] += xx[3];
    								console.log(perDone+" %  |  BK(before) : "+counter[0]+
    																		"    |  BK(during): " +counter[1]+
    																		"    |  BK(after) : " +counter[2]+
    																		"    |  GC(always) :" +counter[3]+ 
    																		"  |  ");
    								idx++;
    							}
    							console.log("Done writing files!");								
								},
		getEconomy: function()
								{
									return ECONOMY;
								},

		getText: 		function()
								{
									return text;
								},	
	};
};
