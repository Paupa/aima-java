package aima.core.environment.eightpuzzle;

import aima.core.search.framework.GoalTest;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Ravi Mohan
 * 
 */
public class EightPuzzleGoalTest implements GoalTest {
	
	private EightPuzzleBoard goal;
	
	public EightPuzzleGoalTest (){
		this(new int[] { 1, 2, 3, 8, 0, 4, 7, 6, 5 });
	}
	
	public EightPuzzleGoalTest (int [] aVector){
		goal = new EightPuzzleBoard(aVector);
	}

	public boolean isGoalState(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		return board.equals(goal);
	}

	@Override
	public String toString() {
		return goal.toString();
	}

	public XYLocation getLocationOf(int val) {
		return goal.getLocationOf(val);
	}
}