package com.divyendu.travellingsalesmanproblem.datatypes;

import java.util.Scanner;

import com.divyendu.travellingsalesmanproblem.constants.Constants;

/**
 * <p>This class is used to model an undirected graph 
 * (although it can model a directed graph, need to change the way cost matrix is generated)
 * Graph is represented with a 2D matrix - cost matrix and number of vertices in the graph
 * </p>
 */
public class UndirectedGraph {
	
	private int numberOfVertices;
	private int[][] costMatrix;
	
	/**
	 * Generates the cost matrix in a smart manner (specific to an undirected graph)
	 */
	public void generateCostMatrixViaInput(int vertices) {
		numberOfVertices = vertices;
		int lastVertex = vertices - 1;
		costMatrix = new int[vertices][vertices];
		initializeCostMatrix();
		Scanner in = new Scanner(System.in);
		for(int i=0; i<lastVertex; i++) {
			char rootVertex = (char)(Constants.CAPITAL_A + i);
			for(int j = i+1 ; j< vertices; j++) {
				char destVertex = (char)(Constants.CAPITAL_A + j);
				System.out.println("Enter the distance from "+rootVertex+" to "+destVertex);
				int distance = in.nextInt();
				costMatrix[i][j] = costMatrix[j][i] = distance; 
			}	
		}
	}
	
	private void initializeCostMatrix() {
		/*Initializes distances from a vertex to itself to INF, since no self loops*/
		for(int i=0; i<numberOfVertices; i++) {
			costMatrix[i][i] = Constants.INFINITY;
		}
	}
	
	/**
	 * Displays the cost matrix
	 */
	public void displayCostMatrix() {
		for(int i = 0; i<numberOfVertices; i++) {
			char rootVertex = (char)(Constants.CAPITAL_A + i);
			for(int j = 0 ;j<numberOfVertices; j++) {
				char destVertex = (char)(Constants.CAPITAL_A + j);
				System.out.println("Distance from "+rootVertex+" to "+destVertex+" : "+costMatrix[i][j]);
			}
		}
	}
	
	public int[][] getCostMatrix() {
		return costMatrix;
	}

	public int getNumberOfVertices() {
		return numberOfVertices;
	}
	
	
}
