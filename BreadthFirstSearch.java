import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Gurkan Solmaz 
 * Implementation of the Breadth First Search (BFS) algorithm
 *
 */
public class BreadthFirstSearch {
	
	boolean[][] adjacencyMatrix;
	int numberOfVertices;
	int sourceLabel;
	int destinationLabel;
	int minHopCount;
	List<Integer> pathSD;
	
	// constructor
	public BreadthFirstSearch(boolean[][] adjacencyMatrix, int sourceLabel,
			int destinationLabel) {
		this.adjacencyMatrix = adjacencyMatrix;
		this.sourceLabel = sourceLabel;
		this.destinationLabel = destinationLabel;
		numberOfVertices =adjacencyMatrix.length;
		minHopCount = breadthFirst();
	}
	
	private int breadthFirst(){
		List<Integer> nodesExplored = new ArrayList<Integer>();
		nodesExplored.add(sourceLabel);
		Queue<Integer> fifoQueue = new LinkedList<Integer>();
		fifoQueue.add(sourceLabel);
		
		List<Integer> pathDS = new ArrayList<Integer>();
		int parent[] = new int[numberOfVertices];
		for(int i=0; i<numberOfVertices;i++){
			parent[i] = -1;
		}
		//HashMap<Integer,Integer> parentList = new HashMap<Integer, Integer>(); // tree edge list

		while(fifoQueue.size()>0){
			int u = fifoQueue.poll();
			for(int v=0;v<numberOfVertices; v++){ 
				if(adjacencyMatrix[u][v]){		// for every neighbour of vertex u
				
					boolean explored=false;
					for(int tmpNode:nodesExplored){
						if(tmpNode==v){
							explored=true;
						}
					}
					if(!explored){ // if v is not explored yet
						nodesExplored.add(v);
						fifoQueue.add(v);
						//parentList.put(u, v);
						parent[v]=u;
					}
				
				}
			}	
		}
		
		if(nodesExplored.size() == numberOfVertices ){
			pathDS.add(destinationLabel) ;
			int tmpNode = destinationLabel;
			
			while(parent[tmpNode]!= -1){
				pathDS.add(parent[tmpNode]);
				tmpNode = parent[tmpNode];
			}
			
			pathSD = new ArrayList<Integer>();
			// reverse 
			for(int k=pathDS.size()-1; k>=0;k--){
				pathSD.add(pathDS.get(k));
			}
			return pathSD.size()-1; // return the size of the math, that is minimum hop count
		}
		else return 0;	// no path between the source and destination
	}
	
	
	// getters and setters
	public int getMinHopCount() {
		return minHopCount;
	}


	public void setMinHopCount(int minHopCount) {
		this.minHopCount = minHopCount;
	}

	public List<Integer> getPathSD() {
		return pathSD;
	}

	public void setPathSD(List<Integer> pathSD) {
		this.pathSD = pathSD;
	}
	

}
