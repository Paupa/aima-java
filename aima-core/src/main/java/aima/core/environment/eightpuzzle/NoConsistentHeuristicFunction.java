package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * Version modificada de ManhattanHeuristicFunction.java
 * 
 */
public class NoConsistentHeuristicFunction implements HeuristicFunction {

	private EightPuzzleGoalTest goal;
	// Se calcula la estimacion con respecto a goalState
	
	public NoConsistentHeuristicFunction(EightPuzzleGoalTest goal) {
		this.goal = goal;
	}
	
	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation locGS = goal.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(loc, locGS);
		}
		return retVal;

	}
	
	// Con respecto a goalState
	public int evaluateManhattanDistanceOf(XYLocation loc, XYLocation locGS) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int xposGS = locGS.getXCoOrdinate();
		int yposGS = locGS.getYCoOrdinate();
		
		retVal = Math.abs(xpos - xposGS) + Math.abs(ypos - yposGS);
		
		return (retVal == 2 ? retVal : 0);  // !!solo las distancias iguales a 2 cuentan
	}
}