package com.divyendu.travellingsalesmanproblem;

import com.divyendu.travellingsalesmanproblem.algorithms.TSP;

/**
 * <p><b>Program entry point</b></p>
 *
 */
public class ApplicationRunner 
{
    public static void main( String[] args )
    {
    	/* Create the TSP instance and invoke method to get the optimal tour */ 
    	TSP tspInstance = new TSP();
    	tspInstance.getOptimalTour();
    }
}
