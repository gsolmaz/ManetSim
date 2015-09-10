import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Gurkan Solmaz
 * 	Discrete Event Simulation for 
 * 		Optimum Routing Approach using Random Waypoint Mobility Model
 */
public class Simulator {
	// simulator parameters
	public int numberOfNodes;
	public double nodeVelocity; // in meters/seconds 
	private double samplingTime; // in seconds
	private double transmissionRange; // in meters - transmission range between nodes
	private double totalSimulationTime; // in seconds
	private double dimensionLength; // in meters - length of a one dimension of the square region
	private double numberOfPairs; // number of source-destination pairs
	private double currentSimulationTime; // in seconds
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// read simulation inputs
		Scanner s = new Scanner(System.in);
		System.out.println("Please enter number of mobile nodes: ");
		int tmpNumberOfNodes= s.nextInt();
		System.out.println("Please enter velocity of mobile nodes(m/sec): ");
		double tmpNodeVelocity= s.nextDouble();
		// create Simulator with specified parameters
		Simulator sim = new Simulator(tmpNumberOfNodes, tmpNodeVelocity, 0.25, 250, 1000, 	1000, 3);
		
		List<MobileNode> nodeList = sim.initializeSystem();
		List<Double> avgTimeHopCountList= sim.simulate(nodeList);
		// call output function for every hopcount for every session
		sim.outputAverageHopCountsOfEachSession(avgTimeHopCountList);
		
	}
	// output total averagehopcount

	private void outputAverageHopCountsOfEachSession(List<Double> avgHopCountList){
		double sumAvgHopCount=0;
		for(int i=0; i<avgHopCountList.size();i++){
			System.out.print(" Average hop count of s-d session " + (i+1) + " : ");
			System.out.println(avgHopCountList.get(i));
			sumAvgHopCount += avgHopCountList.get(i);
		}
		double minimumAverageHopCount = sumAvgHopCount/avgHopCountList.size();
		System.out.println();
		System.out.println(" Minimum average hop count of the system: " + minimumAverageHopCount);

	}
	
	// discrete event simulation
	private List<Double> simulate(List<MobileNode> nodeList){
		List<Double> avgHopCountList= new ArrayList<Double>();
		List<SDSession> sdSessionList = generateSDSessionList();
		
		int totalSamplingCount = (int) (totalSimulationTime / samplingTime);
		
		// create 2D visualizer
	//	Visualizer2D visualizer = new Visualizer2D(this.dimensionLength);
		
		List<List<Double>> trajectX = new ArrayList<List<Double>>();
		List<List<Double>> trajectY = new ArrayList<List<Double>>();

		for(int nodeIndex=0; nodeIndex<nodeList.size();nodeIndex++ ){
			trajectX.add(new ArrayList<Double>());
			trajectY.add(new ArrayList<Double>());
		}
		
		for(int i=0; i<=totalSamplingCount;i++){
			currentSimulationTime = i*samplingTime;
		//	visualizer.clearScreen();
		//	List<List<Integer>> pathSDList=new ArrayList<List<Integer>>();
			
			// generate adjacency matrix for the specific sampling
	/*		boolean[][] adjacencyMatrix = generateAdjacencyMatrix(nodeList);
			for(int j=0; j<numberOfPairs; j++) {
				SDSession tmpSession = sdSessionList.get(j);
				if(tmpSession.getStartTime() <= currentSimulationTime){ // if the session started
					BreadthFirstSearch bfs = new BreadthFirstSearch(adjacencyMatrix,tmpSession.sourceVertex, tmpSession.destinationVertex);
					pathSDList.add(bfs.getPathSD());
					int minHopCount = bfs.getMinHopCount();
					if(minHopCount!=0){ // there exist a path
						tmpSession.setTotalSecondsStaticPathExists(tmpSession.getTotalSecondsStaticPathExists()+samplingTime);
						tmpSession.setSumOfTimeWithHopCounts(tmpSession.getSumOfTimeWithHopCounts()+samplingTime*minHopCount);
					}
				}
			} */
			// move nodes
			
			
			for(int nodeIndex=0; nodeIndex<nodeList.size();nodeIndex++ ){
				trajectX.get(nodeIndex).add(nodeList.get(nodeIndex).currentX);
				trajectY.get(nodeIndex).add(nodeList.get(nodeIndex).currentY);
				nodeList.get(nodeIndex).move(samplingTime, dimensionLength);
			}
			// do the same(iterate) for every sampling
			
			// draw the current structure
		/*	while(true){
				System.out.println("waiting");
				if(true){
					visualizer.draw(adjacencyMatrix, sdSessionList, nodeList, pathSDList);
					visualizer.setCase1(false);
					break;
				}
			}
			*/
		}
		String curDir =  System.getProperty("user.dir");
		writeFlightLengths(nodeList,curDir);
		writeFNumberOfWaitingPoints(nodeList,curDir);
	//	writeTrajectories(trajectX, trajectY, curDir);
		// compute averageHopCounts  
		// for each sdSession (sdSessionSumOfTimeHopCount/totalSecondsStaticPathExists) 
		for(int t=0; t<sdSessionList.size(); t++){
			SDSession tmpSession=sdSessionList.get(t);
			double tmpAverageHopCount = tmpSession.getSumOfTimeWithHopCounts()/tmpSession.getTotalSecondsStaticPathExists();
			avgHopCountList.add(tmpAverageHopCount);
		}
		return avgHopCountList;
		  		
	}
	
	private void writeFNumberOfWaitingPoints(List<MobileNode> nodeList,
			String curDir) {
	
			// write number of entries
		//	out.write(trajectX.size() +"\n");
			int totalWaitingPoints=0;
			for(int i=0; i<nodeList.size(); i++){
					int numberOfWaitingPoints=  nodeList.get(i).getNumberOfWaitPoints();
					totalWaitingPoints+= numberOfWaitingPoints;
			}
			
			double averageNumberOfWaitingPointsPerHour = (double)totalWaitingPoints/ (double)nodeList.size();
			averageNumberOfWaitingPointsPerHour = averageNumberOfWaitingPointsPerHour/(totalSimulationTime /3600);

			System.out.println("Average Waiting Points Per Hour = " + averageNumberOfWaitingPointsPerHour);
	
		
	}

	private void writeFlightLengths(List<MobileNode> nodeList, String curDir) {
		FileWriter fstream;
		BufferedWriter out;
		try {
			fstream = new FileWriter(curDir + "\\Output\\" +"RandomWaypointFlightLengths.txt");
		
			 out = new BufferedWriter(fstream);
			// write number of entries
		//	out.write(trajectX.size() +"\n");
			for(int i=0; i<nodeList.size(); i++){
				List<Double> flightLengthList = nodeList.get(i).getFlightLengthList();
				for(int j=0; j<flightLengthList.size();j++){
					out.write(flightLengthList.get(j) + "\n" );
				}
				
			}
			out.close();
			fstream.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	private void writeTrajectories(List<List<Double>> trajectX, List<List<Double>> trajectY, String curDir) {
			FileWriter fstream;
			BufferedWriter out;
			try {
				fstream = new FileWriter(curDir + "\\Output\\" +"RandomWaypointTrajectories.txt");
			
				 out = new BufferedWriter(fstream);
				// write number of entries
			//	out.write(trajectX.size() +"\n");
				for(int i=0; i<trajectX.size(); i++){
					for(int j=0;j<totalSimulationTime/samplingTime;j++){
						out.write(j+" "+ trajectX.get(i).get(j) + " " + trajectY.get(i).get(j) + "\n" );
					}
				}
				out.close();
				fstream.close();
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}
	// generate adj. matrix using euclidean distances between nodes and transmission range
	private boolean[][] generateAdjacencyMatrix(List<MobileNode> nodeList){
		boolean[][] adjMatrix= new boolean[numberOfNodes][numberOfNodes];
		
		for(int i=0; i<numberOfNodes; i++){
			for(int j=0;j<numberOfNodes;j++){
				if(i==j){
					adjMatrix[i][j]=false; // no self loop
				}
				else if(adjMatrix[i][j]==true){
									// nondirectional
				}
				else{
					double euclideanDistance = Math.pow(nodeList.get(i).getCurrentX()-nodeList.get(j).getCurrentX(), 2);
					euclideanDistance += Math.pow(nodeList.get(i).getCurrentY()-nodeList.get(j).getCurrentY(), 2);
					euclideanDistance = Math.sqrt(euclideanDistance);
					if(euclideanDistance<=transmissionRange){ 
						// there exist and edge between vertices i and j
						adjMatrix[i][j]=true; 
						adjMatrix[j][i]=true;
					}
				}
			}
		}
		
		return adjMatrix;
	}
	// generate different source and destination vertices randomly
	private List<SDSession> generateSDSessionList(){
		List<SDSession> returnList = new ArrayList<SDSession>();
		Random r= new Random();
		for(int i=0;i<numberOfPairs;i++){
			int startTime = i+1;	
			int sourceLabel = r.nextInt(numberOfNodes-1)+1; // uniformly select a vertex 
			int destLabel = r.nextInt(numberOfNodes-1)+1; // uniformly select a vertex 	
			if(sourceLabel==destLabel){ // check if two nodes are the same
				i--;
				continue;
			}
		
			SDSession tmpSDSession = new SDSession(startTime, sourceLabel, destLabel);
			returnList.add(tmpSDSession);
		}	
		return returnList;
	}
	//constructor
	public Simulator(int numberOfNodes, double nodeVelocity,
			double samplingTime, double transmissionRange,
			double simulationTime, double dimensionLength, double numberOfPairs) {
		super();
		this.numberOfNodes = 200;
		this.nodeVelocity = 1;
		this.samplingTime = 1;
		this.transmissionRange = transmissionRange;
		this.totalSimulationTime = 36000;
		this.dimensionLength = 2000;
		this.numberOfPairs = numberOfPairs;
	}
	
	// generate and initialize nodes of the system
	private List<MobileNode> initializeSystem(){
		this.currentSimulationTime = 0;
		List<MobileNode> returnList = new ArrayList<MobileNode>();
		Random r= new Random();
		for(int i=0; i<numberOfNodes; i++){
			MobileNode tmpNode = new MobileNode(nodeVelocity);
			double tmpCurrentX = r.nextDouble() * dimensionLength;
			tmpNode.setCurrentX(tmpCurrentX);
			double tmpCurrentY = r.nextDouble() * dimensionLength;
			tmpNode.setCurrentY(tmpCurrentY);	
			double tmpTargetX = r.nextDouble() * dimensionLength;
			tmpNode.setTargetX(tmpTargetX);
			double tmpTargetY = r.nextDouble() * dimensionLength;
			tmpNode.setTargetY(tmpTargetY);
			returnList.add(tmpNode);
		}
		return returnList;
	}
	
}
