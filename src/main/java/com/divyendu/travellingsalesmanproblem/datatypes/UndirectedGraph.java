package com.divyendu.travellingsalesmanproblem.datatypes;

import java.util.Scanner;

import com.divyendu.travellingsalesmanproblem.constants.Constants;

public class UndirectedGraph {
	
	private int numberOfVertices;
	private int[][] costMatrix;
	
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
		in.close();
	}
	
	public void displayCostMatrix() {
		for(int i = 0; i<numberOfVertices; i++) {
			char rootVertex = (char)(Constants.CAPITAL_A + i);
			for(int j = 0 ;j<numberOfVertices; j++) {
				char destVertex = (char)(Constants.CAPITAL_A + j);
				System.out.println("Distance from "+rootVertex+" to "+destVertex+" : "+costMatrix[i][j]);
			}
		}
	}
	
	private void initializeCostMatrix() {
		//Initializes distances from a vertex to itself to INF
		for(int i=0; i<numberOfVertices; i++) {
			costMatrix[i][i] = Constants.INFINITY;
		}
	}
}
