package GUI;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

public class Utility {
	/*the min max values are for US mainland*/
	public static final float latMin = 24.3115f;
	public static final float latMax = 49.2304f;
	public static final float lonMin = 66.57f;
	public static final float lonMax = 124.46f;
	//max num of pages constraint for soil input
	public static final int maxPages = 100;
	//default file path for two inputs
	public final static String defaultRainFilePath = "d:/rainfallInput.txt";
	public final static String defaultSoilFilePath = "d:/soilReport.pdf";
	//default max num of items and counties
	public final static int ITEMCAPACITY = 30;
    public final static int MAXOFCOUNTIES = 10;
	//the following two hash map is to get the element id of the result table
	public final static Map<String,Integer> DURATION = new HashMap<String,Integer>(){
		private static final long serialVersionUID = 1L;
	{
    	put("5-min",0);  	put("10-min",1);  	put("15-min",2);
    	put("30-min",3); 	put("60-min",4); 	put("2-hr",5);
    	put("3-hr",6);	    put("6-hr",7);	    put("12-hr",8);	
    	put("24-hr",9);     put("2-day",10);     put("3-day",11);
    	put("4-day",12);     put("7-day",13);  	put("10-day",14);
    	put("20-day",15);    put("30-day",16); 	put("45-day",17);
    	put("60-day",18);
    }};
    public final static Map<String,Integer> INTERVAL = new HashMap<String,Integer>(){
		private static final long serialVersionUID = 1L;
	{
	    	put("1",0);  	put("2",1);  	put("5",2);
	    	put("10",3); 	put("25",4); 	put("50",5);
	    	put("100",6);	put("200",7);	put("500",8);	put("1000",9);
	}};
	//private constructor make this not to be instantiated
	private Utility(){}
	//PFGet input check method
	public static void inputCheck(String lat,String lon,String dur,String inte) throws Exception{
		float lat1 = Float.parseFloat(lat);
		float lon1 = Float.parseFloat(lon)*(-1);
		if(lat1<latMin || lat1>latMax){
			String msg = "Latitude input is out of US mainland!";
			JOptionPane.showMessageDialog(null, msg);
			throw new IllegalArgumentException(msg);
		}
		if(lon1<lonMin || lon1>lonMax){
			String msg = "Longitude input is out of US mainland!";
			JOptionPane.showMessageDialog(null, msg);
			throw new IllegalArgumentException(msg);
		}
		if(!DURATION.containsKey(dur)){
			String msg = "Duration input is illegal!";
			JOptionPane.showMessageDialog(null, msg);
			throw new IllegalArgumentException(msg);
		}
		if(!INTERVAL.containsKey(inte)){
			String msg = "Interval input is illegal!";
			JOptionPane.showMessageDialog(null, msg);
			throw new IllegalArgumentException(msg);
		}
	}
	
}
