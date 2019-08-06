package com.divyendu.travellingsalesmanproblem.datatypes;

/**
 * <p>This class models a state space tree node.
 * This is the node we would see in the state space tree when visualizing solving TSP via Branch and Bound - level order
 * </p>
 * 
 * It's represented via the vertex, its cost matrix (reduced form) and the bounding value 
 * Cost matrix is derived after reducing and processing its root vertex's cost matrix
 * Bounding value is used to decide whether that node can be further progressed on or not
 * 
 */
public class StateSpaceTreeNode {
	
	private int vertex;
	private int[][] costMatrix;
	private int boundingValue;
	
	public int getVertex() {
		return vertex;
	}
	public void setVertex(int vertex) {
		this.vertex = vertex;
	}
	public int[][] getCostMatrix() {
		return costMatrix;
	}
	public void setCostMatrix(int[][] costMatrix) {
		this.costMatrix = costMatrix;
	}
	public int getBoundingValue() {
		return boundingValue;
	}
	public void setBoundingValue(int boundingValue) {
		this.boundingValue = boundingValue;
	}
	
	

}
