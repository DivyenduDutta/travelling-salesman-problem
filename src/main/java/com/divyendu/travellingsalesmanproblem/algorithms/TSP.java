package com.divyendu.travellingsalesmanproblem.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.divyendu.travellingsalesmanproblem.constants.Constants;
import com.divyendu.travellingsalesmanproblem.datatypes.StateSpaceTreeNode;
import com.divyendu.travellingsalesmanproblem.datatypes.UndirectedGraph;
import com.divyendu.travellingsalesmanproblem.helpers.MatrixHelper;

/**
 * This class models the Travelling salesman problem algorithm via the Branch and Bound - level order
 *
 */
public class TSP {
	//private int rootVertex; //use state space tree node object here
	private StateSpaceTreeNode rootVertex;
	private UndirectedGraph tspGraph;
	private boolean[] visited;
	private StringBuffer tour;
	
	/**
	 * Main entry into the TSP algorithm logic
	 */
	public void getOptimalTour() {
		initTSP();
		List<StateSpaceTreeNode> processedStateSpaceNodes = new ArrayList<>();
		processVertex();	//process just the root vertex
		int vertices = tspGraph.getNumberOfVertices();
		while(!areAllVerticesVisited()) {	//loop until all vertices have been visited
			for(int i=0; i<tspGraph.getNumberOfVertices(); i++) {
				//core logic here
				if(!(isRootVertex(i) || isGivenVertexVisited(i))) { // check if vertex has not been visited and isnt the root vertex
					StateSpaceTreeNode curNode = new StateSpaceTreeNode();
					curNode.setVertex(i);
					processOtherVertex(curNode, vertices); //process any other vertex
					processedStateSpaceNodes.add(curNode);
				}
			}
			chooseRootVertex(processedStateSpaceNodes);
			processedStateSpaceNodes.clear();
		}
		tour.append(tour.charAt(0)); //complete the tour
		System.out.println("Optimal Tour is "+tour+" with cost "+rootVertex.getBoundingValue());
	}
	
	/**
	 * Processes just the root vertex, involves reducing the cost matrix and getting the reduction cost
	 */
	private void processVertex() {
		int[][] rootVertexCostMatCopy = MatrixHelper.createDeepCopyMatrix(tspGraph.getCostMatrix(), tspGraph.getNumberOfVertices());
		int boundingValue = MatrixHelper.reduceMatrix(rootVertexCostMatCopy, tspGraph.getNumberOfVertices());
		rootVertex.setBoundingValue(boundingValue);
		rootVertex.setCostMatrix(rootVertexCostMatCopy);
	}
	
	/**
	 * Processes every other vertex, involves reducing the cost matrix of its root vertex
	 * The cost of the vertex = cost of the root vertex + distance from the root to currently being processed vertex + reduction cost
	 */
	private void processOtherVertex(StateSpaceTreeNode curNode, int vertices) {
		int[][] copyRootCostMatrix = MatrixHelper.createDeepCopyMatrix(rootVertex.getCostMatrix(), vertices);
		processCostMatrixForCurrentNode(copyRootCostMatrix, rootVertex.getVertex(), curNode.getVertex(), vertices);
		int boundingValue = MatrixHelper.reduceMatrix(copyRootCostMatrix, vertices) + rootVertex.getCostMatrix()[rootVertex.getVertex()][curNode.getVertex()] + rootVertex.getBoundingValue();
		curNode.setBoundingValue(boundingValue);
		curNode.setCostMatrix(copyRootCostMatrix);
		
	}
	
	/**
	 * Chooses the root vertex based on the lowest bounding value
	 */
	private void chooseRootVertex(List<StateSpaceTreeNode> processedStateSpaceNodes) {
		StateSpaceTreeNode minNode = processedStateSpaceNodes.get(0);
		for(StateSpaceTreeNode node : processedStateSpaceNodes) {
			if(node.getBoundingValue() < minNode.getBoundingValue()) {
				minNode = node;
			}
		}
		rootVertex = minNode;
		visited[rootVertex.getVertex()] = true;
		tour.append((char)(rootVertex.getVertex()+Constants.CAPITAL_A));
	}
	
	/**
	 * Processes the cost matrix to ensure that for an edge already part of the tour has all outgoing from a vertex and 
	 * incoming to vertex of the edge is made INFINITY
	 */
	private void processCostMatrixForCurrentNode(int[][] aMatrix, int out, int in, int vertices) {
		//Make all outgoing edges from i to INF and all incoming edges to j INF 
		for(int i=0; i<vertices; i++) {
			for(int j=0; j<vertices; j++) {
				aMatrix[out][j] = Constants.INFINITY;
				aMatrix[j][in] = Constants.INFINITY;
			}
		}
		
		//Make all edges from previously vertices to current vertex INF
		for(int i = 0; i<vertices; i++) {
			if(i != in && visited[i] && aMatrix[i][in] != Constants.INFINITY) {
				aMatrix[i][in] = Constants.INFINITY;
			}
		}
	}
	
	/**
	 * Checks if all vertices have been visited
	 */
	private boolean areAllVerticesVisited() {
		boolean isAllVerticesVisited = true;
		for(int i=0; i<tspGraph.getNumberOfVertices(); i++) {
			isAllVerticesVisited &= isGivenVertexVisited(i);
		}
		return isAllVerticesVisited;
	}
	
	/**
	 * Checks if a given vertex has been visited
	 */
	private boolean isGivenVertexVisited(int vertex) {
		boolean isVertexVisited = false;
		isVertexVisited = (visited[vertex])?true:false;
		return isVertexVisited;
	}
	
	/**
	 * Checks if the input vertex is the root vertex
	 */
	private boolean isRootVertex(int vertex) {
		boolean isVertexRoot = false;
		isVertexRoot = (vertex == rootVertex.getVertex())?true:false;
		return isVertexRoot;
	}
	
	
	/**
	 * Initilizes the TSP graph, info about which vertices haven been visited (which is none initially), the root vertex
	 */
	private void initTSP() {
		initTSPGraph();
		initVerticesVisitedArray();
		initRootVertex();
	}
	
	/**
	 * Initializes the root vertex based on input taken from the user
	 */
	private void initRootVertex() {
		System.out.println("Enter the vertex from which to start tour - "+Constants.CAPITAL_A +" to "
				+(char)(Constants.CAPITAL_A+tspGraph.getNumberOfVertices()-1));
		Scanner scanner = new Scanner(System.in);
		String input = scanner.nextLine();
		char rootVertexChar = input.charAt(0);
		setRootVertex(rootVertexChar);
	}
	
	/**
	 * Sets the root vertex as an integer based on user input
	 */
	private void setRootVertex(char rootVertexChar) {
		rootVertex = new StateSpaceTreeNode();
		if(validateRootVertexValue(rootVertexChar)) {
			rootVertex.setVertex((int)(rootVertexChar-Constants.CAPITAL_A));
		}else {
			System.out.println("Invalid root vertex recieved...setting root vertex to A");
			rootVertex.setVertex(0);
		}
		visited[rootVertex.getVertex()] = true;
		tour.append((char)(rootVertex.getVertex()+Constants.CAPITAL_A));
	}
	
	/**
	 * Validates whether the user entered vertex is valid - is within the bounds of the number of vertices of the graph
	 */
	private boolean validateRootVertexValue(char rootVertexChar) {
		boolean isValid = false;
		int rootVertexInt = rootVertexChar;
		isValid = (rootVertexInt >= Constants.CAPITAL_A && rootVertexInt <= (Constants.CAPITAL_A+tspGraph.getNumberOfVertices()-1))?true:false;
		return isValid;
	}
	
	/**
	 * Initializes the TSP's undirected graph. This invokes the method of the undirected graph to create the cost matrix
	 */
	private void initTSPGraph() {
		System.out.println("Enter the number of vertices (nodes)");
		Scanner in = new Scanner(System.in);
		int numberOfVertices = in.nextInt();
		tspGraph = new UndirectedGraph();
		tspGraph.generateCostMatrixViaInput(numberOfVertices);
	}
	
	private void initVerticesVisitedArray() {
		visited = new boolean[tspGraph.getNumberOfVertices()];
		for(int i=0; i<tspGraph.getNumberOfVertices(); i++) {
			visited[i] = false;
		}
		tour = new StringBuffer();
	}
	
}
