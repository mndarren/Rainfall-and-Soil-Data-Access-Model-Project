package soilInfoGet;
/**
 * @purpose this class is to contain the basic data of the soil data
 *          and provide the calculation method to get the final result
 * @author Zhao
 * @date 12/24/2015 created
 */
public class SoilDataNode {
    private float highCapacity;
    private float veryHighCapacity;
    private float depth;
    private float percent;
    
    public SoilDataNode(){}
    public SoilDataNode(float h,float vh,float d,float p){
    	highCapacity = h;
    	veryHighCapacity = vh;
    	depth = d;
    	percent = p;
    }
    public SoilDataNode(SoilDataNode sd){
    	this.highCapacity = sd.highCapacity;
    	this.veryHighCapacity = sd.veryHighCapacity;
    	this.depth = sd.depth;
    	this.percent = sd.percent;
    }
    
    public float getHighCapacity(){return highCapacity;}
    public float getVeryHighCapacity(){return veryHighCapacity;}
    public float getDepth(){return depth;}
    public float getPercent(){return percent;}
    
    public void setHighCapacity(float h){highCapacity=h;}
    public void setVeryHighCapacity(float vh){veryHighCapacity=vh;}
    public void setDepth(float d){depth=d;}
    public void setPercent(float p){percent=p;}
    
    public void calData(){//calculate the final soil data
    	highCapacity *= percent;
    	veryHighCapacity *= percent;
    	depth *= percent;
    }
    public void plus(SoilDataNode sd){//two object data plus together
    	this.highCapacity += sd.highCapacity;
    	this.veryHighCapacity += sd.veryHighCapacity;
    	this.depth += sd.depth;
    }
    public String toString(){
    	return "(" + highCapacity + ", " + veryHighCapacity + ", " + depth + ")";
    }
}
