package com.divyendu.travellingsalesmanproblem;

import com.divyendu.travellingsalesmanproblem.datatypes.UndirectedGraph;

public class ApplicationRunner 
{
    public static void main( String[] args )
    {
        UndirectedGraph tspGraph = new UndirectedGraph();
        tspGraph.generateCostMatrixViaInput(4);
        tspGraph.displayCostMatrix();
    }
}
