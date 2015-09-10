import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 
 */

/**
 * @author Gurkan Solmaz
 *
 */
public class MobileNode {
	// class variables
	double velocity;
	double direction;
	double currentX;
	double currentY; // current x,y coordinates
	double targetX; // target x,y coordinates
	double targetY;
	int waitCounter;
	
	int numberOfWaitPoints;
	List<Double> flightLengthList;
	// constructor
	public MobileNode(double velocity) {
		super();
		this.velocity = velocity;
		flightLengthList = new ArrayList<Double>();
		waitCounter =0;numberOfWaitPoints=0;
	}
	// getters and setters
	public double getCurrentX() {
		return currentX;
	}
	public void setCurrentX(double currentX) {
		this.currentX = currentX;
	}
	public double getCurrentY() {
		return currentY;
	}
	public void setCurrentY(double currentY) {
		this.currentY = currentY;
	}
	public double getTargetX() {
		return targetX;
	}
	
	
	public int getNumberOfWaitPoints() {
		return numberOfWaitPoints;
	}
	public void setNumberOfWaitPoints(int numberOfWaitPoints) {
		this.numberOfWaitPoints = numberOfWaitPoints;
	}
	public void setTargetX(double targetX) {
		this.targetX = targetX;
	}
	public double getTargetY() {
		return targetY;
	}
	public void setTargetY(double targetY) {
		this.targetY = targetY;
	}
	
	// mobility computation for the mobile node
	public void move(double samplingTime, double lengthOfRegion){
		double xDistance, yDistance;
		xDistance = targetX-currentX;
		yDistance = targetY-currentY;
		double euclideanDistance= Math.sqrt(xDistance*xDistance+yDistance*yDistance);
		if(euclideanDistance==0){
			// find a new random point -- no pausing time
			if(waitCounter!=10){
				waitCounter++;
				return;
			}
			else{
				waitCounter = 0;
				numberOfWaitPoints ++;
				
			}
			Random r= new Random();
			targetX= r.nextDouble() * lengthOfRegion;
			targetY = r.nextDouble() * lengthOfRegion;
			double flightLength = (targetX-currentX) * (targetX-currentX);
			flightLength+= (targetY-currentY) * (targetY-currentY) ;
			flightLength = Math.sqrt(flightLength);
			flightLengthList.add(flightLength);
			move(samplingTime, lengthOfRegion);
		}
		else if(euclideanDistance<samplingTime*velocity){
			// move to the target point
			currentX=targetX;
			currentY=targetY;
		}
		else{ // move with direction
			double sinAngle = yDistance/euclideanDistance;
			double cosAngle =Math.sqrt( 1- sinAngle*sinAngle );
			if(currentX<targetX){
				currentX += cosAngle * velocity * samplingTime;
			}
			else{
				currentX -= cosAngle * velocity * samplingTime;
			}
			currentY += sinAngle * velocity * samplingTime;
		}
		return;
	}
	public List<Double> getFlightLengthList() {
		return flightLengthList;
	}
	public void setFlightLengthList(List<Double> flightLengthList) {
		this.flightLengthList = flightLengthList;
	}
	
	
}
