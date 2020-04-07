package soilInfoGet;

import java.util.LinkedList;
import java.util.List;

/**
 * @purpose this class is to contain soil data nodes
 *          and provide the calculation method to get the final result
 * @author Zhao
 * @date 12/24/2015 created
 */
public class SoilDataList {
   private List<SoilDataNode> soilDataNodes;
   private SoilDataNode finalSoilData;
   
   public SoilDataList(){
	   soilDataNodes = new LinkedList<>();
	   finalSoilData = new SoilDataNode();
   }
   public SoilDataNode getFinalSoilData(){return finalSoilData;}
   
   public void inertSoilDataNode(SoilDataNode sd){
	   soilDataNodes.add(sd);
   }
   public java.util.Iterator<SoilDataNode> getSoilDataNodes(){
	   return soilDataNodes.iterator();
   }
   public void calFinalSoilData(){
	   for(java.util.Iterator<SoilDataNode> iter = soilDataNodes.iterator();iter.hasNext();){
		   SoilDataNode temp = iter.next();
		   temp.calData();
		   finalSoilData.plus(temp);
	   }
   }
   public String toString(){//only return the original data without final soil data
	   String msg = null;
	   for(java.util.Iterator<SoilDataNode> iter = soilDataNodes.iterator();iter.hasNext();){
		   SoilDataNode temp = iter.next();
		   msg += temp.toString() + "*" + temp.getPercent() + "+";
	   }
	   msg = msg.substring(0,msg.length()-2);
	   return msg;
   }
}
