package precipitationFrequencyGet;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileReader;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

/**
 * @purpose this class is to get the precipitation frequency info of specific location 
 *          as Rainfall input for the whole software.
 * @author Zhao Xie
 * @date 12/16/2015
 * @fileName PFGet.java
 */
public class PFGet {
	private PFManager pfManager;
	private String filePath;
	private final int maxOfThread = 10;
	
	public PFGet(){
		filePath = GUI.Utility.defaultRainFilePath;
	}
	public void setFilePath(String fp){
		filePath = fp;
	}
	public String getResult(){
		return pfManager.getResultValue();
	}
	public void processInput() throws Exception{
		String line=null;
		try {
			File file = new File(filePath);
			BufferedReader br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			br.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Rainfall input file Error!");
			e.printStackTrace();
		}
		StringTokenizer st = new StringTokenizer(line.toString());
		
		String latitudeStart = st.nextToken();
		String longitudeStart = st.nextToken();
		String duration = st.nextToken();
		String interval = st.nextToken();
		String latitudeEnd = st.nextToken();
		String longitudeEnd = st.nextToken();
		//input checking
		GUI.Utility.inputCheck(latitudeStart,longitudeStart,duration,interval);
		GUI.Utility.inputCheck(latitudeEnd,longitudeEnd,duration,interval);
		//calculate how many threads we need, 0.01 is the distance
		int numOfThread = (int)(100*(Float.parseFloat(longitudeEnd) - Float.parseFloat(longitudeStart)));
		numOfThread = numOfThread > maxOfThread ? maxOfThread : numOfThread;
		//generate threads
		pfManager = new PFManager.Builder(latitudeStart,longitudeStart).duration(duration).
			      interval(interval).build();
		for(int i=0;i<numOfThread;i++){
			PFManager pfManagerClone = pfManager.clone();
			PFThread thread = new PFThread(latitudeEnd, pfManagerClone);
			thread.start();
			float longitudeStartInt = Integer.parseInt(longitudeStart);
			longitudeStartInt += 0.01;
			longitudeStart = ((Float)longitudeStartInt).toString();
			pfManager.setLongitude(longitudeStart);
		}
		 
	}
	public String toString(){
		return pfManager.toString();
	}
}
