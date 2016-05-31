package aima.core.environment.tsp;

import aima.core.search.framework.GoalTest;

public class TravelingSalesmanGoalState implements GoalTest {

	@Override
	public boolean isGoalState(Object state) {
		return ((TravelingSalesmanState) state).areAllCitiesVisited();
	}

}
