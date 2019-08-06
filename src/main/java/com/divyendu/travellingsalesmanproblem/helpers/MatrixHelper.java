package com.divyendu.travellingsalesmanproblem.helpers;

import com.divyendu.travellingsalesmanproblem.constants.Constants;

/**
 * <p>This class has helper methods to perform various operations on matrices. </p>
 *
 */
public class MatrixHelper {
	
	/**
	 * Reduce a given matrix - row reduction followed by column reduction
	 */
	public static int reduceMatrix(int[][] aMatrix, int vertices) {
		int reductionCost = 0;
		//row reduction
		int[] rowMin = new int[vertices];
		for(int i=0; i<vertices; i++) {
			int minimum = Constants.INFINITY;
			for(int j=0; j<vertices; j++) {
				if(aMatrix[i][j] < minimum) {
					minimum = aMatrix[i][j];
				}
			}
			rowMin[i] = minimum;
		}
		
		for(int i=0; i<vertices; i++) {
			for(int j=0; j<vertices; j++) {
				if(aMatrix[i][j] != Constants.INFINITY) {
					aMatrix[i][j] -= rowMin[i];
				}
			}
		}
		
		//column reduction
		int[] columnMin = new int[vertices];
		for(int j=0; j<vertices; j++) {
			int minimum = Constants.INFINITY;
			for(int i=0; i<vertices; i++) {
				if(aMatrix[i][j] < minimum) {
					minimum = aMatrix[i][j];
				}
			}
			columnMin[j] = minimum;
		}
		
		for(int j=0; j<vertices; j++) {
			for(int i=0; i<vertices; i++) {
				if(aMatrix[i][j] != Constants.INFINITY) {
					aMatrix[i][j] -= columnMin[j];
				}
			}
		}
		
		reductionCost = reductionCost(rowMin, columnMin, vertices);
		return reductionCost;
	}
	
	/**
	 * Finds the reduction cost of a matrix which is a sum of the minimum values of each row and each column 
	 * 
	 * @param rowMin - array of minimum values of each of the rows of the matrix
	 * @param columnMin - array of minimum values of each of the columns of the matrix
	 * @param vertices - number of vertices (number of rows/columns in the matrix)
	 * @return reductionCost - the cost to reduce the matrix
	 */
	private static int reductionCost(int[] rowMin, int[] columnMin, int vertices) {
		int reductionCost = 0;
		for(int i=0; i<vertices; i++) {
			if(rowMin[i] != Constants.INFINITY) {
				reductionCost += rowMin[i];
			}
			if(columnMin[i] != Constants.INFINITY) {
				reductionCost += columnMin[i];
			}
		}
		return reductionCost;
	}
	
	/**
	 * Displays a matrix
	 */
	public static void displayMatrix(int[][] aMatrix, int vertices) {
		for(int i=0; i<vertices; i++) {
			for(int j=0; j<vertices; j++) {
				System.out.print(aMatrix[i][j] +"\t");
			}
			System.out.print("\n");
		}
	}
	
	/**
	 * Create a deep copy of a matrix. In java arrays are passed by reference
	 * @param aMatrix - The matrix whose deep copy needs to be created
	 * @param vertices - number of vertices
	 * @return copyMatrix - the deep copy of the input matrix
	 */
	public static int[][] createDeepCopyMatrix(int[][] aMatrix, int vertices) {
		int[][] copyMatrix = new int[vertices][vertices];
		for(int i=0; i<vertices; i++) {
			for(int j=0; j<vertices; j++) {
					copyMatrix[i][j] = aMatrix[i][j];
				}
			}
		return copyMatrix;
	}
}
