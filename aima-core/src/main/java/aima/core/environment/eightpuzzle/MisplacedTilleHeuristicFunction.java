package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

/**
 * @author Ravi Mohan
 * 
 */
public class MisplacedTilleHeuristicFunction implements HeuristicFunction {
	
	private EightPuzzleGoalTest goal;

	public MisplacedTilleHeuristicFunction(EightPuzzleGoalTest goal) {
		this.goal = goal;
	}

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		return getNumberOfMisplacedTiles(board);
	}

	private int getNumberOfMisplacedTiles(EightPuzzleBoard board) {
		int numberOfMisplacedTiles = 0;
		
		for (int i = 1; i < 9; i++)
		    if (!(board.getLocationOf(i).equals(goal.getLocationOf(i)))) {
			    numberOfMisplacedTiles++;
		    }
		return numberOfMisplacedTiles;
	}
}