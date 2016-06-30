package aima.core.environment.tsp;

import aima.core.search.framework.GoalTest;

/**
 * @author Paula Díaz Puertas
 */
public class TravelingSalesmanGoalTest implements GoalTest {

	@Override
	public boolean isGoalState(Object state) {
		return ((TravelingSalesmanState) state).areAllCitiesVisited();
	}

}
