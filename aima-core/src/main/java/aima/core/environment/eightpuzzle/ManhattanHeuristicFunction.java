package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class ManhattanHeuristicFunction implements HeuristicFunction {
	
	private EightPuzzleGoalTest goal;

	public ManhattanHeuristicFunction(EightPuzzleGoalTest goal) {
		this.goal = goal;
	}

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		int retVal = 0;
		for (int i = 1; i <= 8; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation locGS = goal.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(loc, locGS);
		}
		return retVal;

	}
	
	public int evaluateManhattanDistanceOf(XYLocation loc, XYLocation locGS) {
		int retVal = 0;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int xposGS = locGS.getXCoOrdinate();
		int yposGS = locGS.getYCoOrdinate();
		
		retVal = Math.abs(xpos - xposGS) + Math.abs(ypos - yposGS);
		
		return retVal;
	}
}