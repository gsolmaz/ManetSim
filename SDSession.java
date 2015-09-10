/**
 * 
 */

/**
 * @author Gurkan Solmaz
 *
 */
public class SDSession {
	double startTime;//same as the index
	double totalSecondsStaticPathExists;  // will be used to find time average
	double sumOfTimeWithHopCounts; // will be used to find time average
	int sourceVertex; // source vertex label
	int destinationVertex;

	
	public SDSession(double startTime, int sourceVertex, int destinationVertex) {
		this.startTime = startTime;
		this.sourceVertex=sourceVertex;
		this.destinationVertex = destinationVertex;
	}
	
	public double getTotalSecondsStaticPathExists() {
		return totalSecondsStaticPathExists;
	}
	public void setTotalSecondsStaticPathExists(double totalSecondsStaticPathExists) {
		this.totalSecondsStaticPathExists = totalSecondsStaticPathExists;
	}
	public double getSumOfTimeWithHopCounts() {
		return sumOfTimeWithHopCounts;
	}
	public void setSumOfTimeWithHopCounts(double sumOfTimeWithHopCounts) {
		this.sumOfTimeWithHopCounts = sumOfTimeWithHopCounts;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}

	public int getSourceVertex() {
		return sourceVertex;
	}

	public void setSourceVertex(int sourceVertex) {
		this.sourceVertex = sourceVertex;
	}

	public int getDestinationVertex() {
		return destinationVertex;
	}

	public void setDestinationVertex(int destinationVertex) {
		this.destinationVertex = destinationVertex;
	}
	
}
