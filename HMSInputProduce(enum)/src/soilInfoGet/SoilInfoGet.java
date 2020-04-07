package soilInfoGet;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
/**
 * @purpose this class will analyze the pdf file, find the valid data,
 *          and calculate the final value.
 * @author Zhao
 * @date 12/25/2015
 */
public class SoilInfoGet{
    private String filePath;
    private SoilDataList soilDataList;
    private PDFManager pdfManager;
    private static SoilInfoGet soilInfoGet;
    
    public SoilInfoGet(){
    	pdfManager = new PDFManager();
    	soilDataList = new SoilDataList();
    	filePath = GUI.Utility.defaultSoilFilePath;
    }
    public static SoilInfoGet getInstance(){
    	if(soilInfoGet == null){
    		synchronized (SoilInfoGet.class){
    			if(soilInfoGet == null){
    				return soilInfoGet = new SoilInfoGet();
    			}
    		}
    	}
    	return soilInfoGet;
    }
    public void setFilePath(String fp){
    	filePath = fp;
    }
    public SoilDataNode getFinalSoilInfo() throws IOException{
    	process();
    	return soilDataList.getFinalSoilData();
    }
    private boolean isNumeric(String str){  
      try{  
        Double.parseDouble(str);  
      }catch(NumberFormatException nfe){  
        return false;  
      }  
      return true;  
    }
    private void process() throws IOException {
    	soilDataList = new SoilDataList();
    	String[] counties = new String[GUI.Utility.ITEMCAPACITY];
    	String[] ids = new String[GUI.Utility.ITEMCAPACITY];
    	float[] percents = new float[GUI.Utility.ITEMCAPACITY];
    	int indexArray = 0;
    	StringTokenizer st;
    	String reportInfo=null;
		try {
			pdfManager.setFilePath(filePath);
			reportInfo = pdfManager.ToText();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Soil input file Error!");
			e.printStackTrace();
		}
    	
    	String keyWord = "Map Unit Legend";
    	int indexBegin = reportInfo.indexOf(keyWord)+keyWord.length();
    	reportInfo = reportInfo.substring(indexBegin, reportInfo.length());
    	indexBegin = reportInfo.indexOf(keyWord)+keyWord.length();
    	reportInfo = reportInfo.substring(indexBegin, reportInfo.length());
    	keyWord = "Map Unit Descriptions";
    	int indexEnd = reportInfo.indexOf(keyWord);
    	String percentInfo = reportInfo.substring(0, indexEnd);
    	String valueInfo = reportInfo.substring(indexEnd+22,reportInfo.length());
    	//System.out.println(percentInfo);
    	//Analyze and catch the the percent data
    	while(percentInfo.contains("100.0%")){
    		int indexPiece = percentInfo.indexOf("100.0%")+6;
    		String percentInfoPiece = percentInfo.substring(0,indexPiece);
        	String tempCounty = percentInfoPiece.substring(0,percentInfoPiece.indexOf("County, Minnesota")+17);
        	int indexIdBegin = percentInfoPiece.indexOf("Percent of AOI")+16;
        	String temp = percentInfoPiece.substring(indexIdBegin,indexIdBegin+50);
            st = new StringTokenizer(temp);
        	String tempId = st.nextToken()+'—'+st.nextToken();
        	int indexPercentEnd = percentInfoPiece.indexOf('%');
        	int i;
        	for(i=0;percentInfoPiece.charAt(indexPercentEnd-i)!=32;i++){}
        	float tempPercent = Float.parseFloat(percentInfoPiece.substring(indexPercentEnd-i,indexPercentEnd));
        	counties[indexArray] = tempCounty;
        	ids[indexArray] = tempId.trim();
        	percents[indexArray] = tempPercent/100;
        	indexArray++;
        	temp = percentInfoPiece.substring(indexPercentEnd+3,indexPercentEnd+50);
        	st = new StringTokenizer(temp);
        	tempId = st.nextToken()+'—'+st.nextToken();
        	while(!tempId.contains("Subtot") && !tempId.contains("Totals")){
        		percentInfoPiece = percentInfoPiece.substring(indexPercentEnd+2,percentInfoPiece.length());
        		indexPercentEnd = percentInfoPiece.indexOf('%');
        		for(i=0;percentInfoPiece.charAt(indexPercentEnd-i)!=32;i++){}
            	tempPercent = Float.parseFloat(percentInfoPiece.substring(indexPercentEnd-i,indexPercentEnd));
            	counties[indexArray] = tempCounty;
            	ids[indexArray] = tempId.trim();
            	percents[indexArray] = tempPercent/100;
            	indexArray++;
            	temp = percentInfoPiece.substring(indexPercentEnd+3,indexPercentEnd+50);
            	st = new StringTokenizer(temp);
            	tempId = st.nextToken()+'—'+st.nextToken();
        	}
        	percentInfo = percentInfo.substring(indexPiece+2,percentInfo.length());
    	}
    	//the following for loop is for testing, keep it please.
    	/*for(int j=0;j<indexArray;j++){
    		System.out.println("county: " + counties[j] + " id: "+ ids[j]+ " percent: "+percents[j]);
    	}*/
    	//Analyze and catch value data
    	//System.out.println(valueInfo);
    	String[] diffCounties = new String[GUI.Utility.MAXOFCOUNTIES];
    	diffCounties[0] = counties[0];
    	int numOfCounties = 1;
    	for(int i=1;i<indexArray;i++){
    		if(counties[i] != diffCounties[numOfCounties-1]){
    			diffCounties[numOfCounties] = counties[i];
    			numOfCounties++;
    		}
    	}
    	int countyCount = 0, itemCount = 0;
    	int indexReferences = valueInfo.indexOf("References");
    	int indexCountyBegin = valueInfo.indexOf(diffCounties[countyCount]);
		valueInfo = valueInfo.substring(indexCountyBegin, indexReferences);
    	//System.out.println(valueInfo);
		
    	while(countyCount<numOfCounties){
    		String valueInfoPiece;
    		int indexOfCounty = 0;
    		if(countyCount+1<numOfCounties){
    			indexOfCounty= valueInfo.indexOf(diffCounties[countyCount+1]);
    			valueInfoPiece = valueInfo.substring(0, indexOfCounty);
    		}else
    			valueInfoPiece = valueInfo;
    		//System.out.println("the following is piece: " + countyCount +"\n\n"+valueInfoPiece);
    		int numOfItems = 0;
    		for(int i = itemCount; counties[i] == diffCounties[countyCount];i++,numOfItems++){}
    		int iCount = 0;
    		//System.out.println("numOfItems = "+numOfItems);
    		while(iCount<numOfItems){
    			String littlePiece;
    			float high = 0;
    			float veryHigh = 0;
    			float depth = 0;
    			int indexOfId = 0;
    			//System.out.println("itemCount = "+itemCount);
    			if(iCount+1 < numOfItems){
    				indexOfId = valueInfoPiece.indexOf(ids[itemCount+1]);
    				//System.out.println("indexOfId = "+ indexOfId);
    				//System.out.println("ids[itemCount+1] = "+ids[itemCount+1]);
    				littlePiece = valueInfoPiece.substring(0,indexOfId);
    			}else
    				littlePiece = valueInfoPiece;
    			String key = "Capacity of the most limiting layer to transmit water (Ksat):";
    			if(littlePiece.contains(key)){
    				int indexOfHigh = littlePiece.indexOf(key)+key.length();
    				String line = littlePiece.substring(indexOfHigh, indexOfHigh+60);
    				line = line.substring(line.indexOf('(')+1, line.length());
    				st = new StringTokenizer(line);
    				high = Float.parseFloat(st.nextToken());
    				st.nextToken();
    				veryHigh = Float.parseFloat(st.nextToken());
    			}else{
    				high = veryHigh = 0;
    			}
    			key = "Depth to water table: ";
    			if(littlePiece.contains(key)){
    				int indexOfDepth = littlePiece.indexOf(key)+key.length();
    				String line = littlePiece.substring(indexOfDepth, indexOfDepth+30);
    				st = new StringTokenizer(line);
    				String value = st.nextToken();
    				while(!isNumeric(value)){value = st.nextToken();}
    				depth = Float.parseFloat(value);
    			}else{
    				depth = 0;
    			}
    			
    			SoilDataNode tempNode = new SoilDataNode(high,veryHigh,depth,percents[itemCount]);
    			soilDataList.inertSoilDataNode(tempNode);
    			valueInfoPiece = valueInfoPiece.substring(indexOfId, valueInfoPiece.length());
    			itemCount++; iCount++;
    		}
    		valueInfo = valueInfo.substring(indexOfCounty+20, valueInfo.length());
    		countyCount++;
    	}
    	//the following two line is for check if the result is correct, keep them
    	/*String log = soilDataList.toString();
    	System.out.println(log);*/
    	
    	soilDataList.calFinalSoilData();
    	//System.out.println(soilDataList.getFinalSoilData().toString());
   	}
}
