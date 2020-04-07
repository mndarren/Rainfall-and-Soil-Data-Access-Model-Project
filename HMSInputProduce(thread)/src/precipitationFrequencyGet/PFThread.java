package precipitationFrequencyGet;
/**
 * @purpose This class is for thread, and each thread is in charge of
 *          a line of geographic points which have same longitude
 * @author Zhao Xie
 * @date 1/6/2016
 */
public class PFThread extends Thread {
	private PFManager pfManager;
	private String latitudeEnd;
	private int numOfLoop;
	
	public PFThread(String end, PFManager pfm){
		latitudeEnd = end;
		pfManager = pfm;
		numOfLoop = 100*(Integer.parseInt(latitudeEnd) 
				    - Integer.parseInt(pfm.getLatitude()));

	}
	public void run(){
		
		for(int i=0;i<numOfLoop;i++){
			try {
				pfManager.calResultValue();
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("the result: "+ pfManager.toString());
			Float nextLatitude = (float) (Float.parseFloat(pfManager.getLatitude())+0.01);
			pfManager.setLatitude(nextLatitude.toString());
		}
	}
}
