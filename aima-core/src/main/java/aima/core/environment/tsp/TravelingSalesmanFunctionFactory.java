package aima.core.environment.tsp;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.StepCostFunction;

public class TravelingSalesmanFunctionFactory {
	
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;
	private static StepCostFunction _stepCostFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new TSPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new TSPResultFunction();
		}
		return _resultFunction;
	}
	
	public static StepCostFunction getStepCostFunction() {
		if (null == _stepCostFunction) {
			_stepCostFunction = new TSPStepCostFunction();
		}
		return _stepCostFunction;
	}

	private static class TSPActionsFunction implements ActionsFunction {
		
		public Set<Action> actions(Object state) {

			Set<Action> actions = new LinkedHashSet<Action>();
			
			TravelingSalesmanState tspState = (TravelingSalesmanState) state;
			
			City from = tspState.getLastVisited();
			
			for(City notVisited : ((TravelingSalesmanState) state).getNotVisited())
				if(from == null || from.getCost(notVisited) != null)
					actions.add(new TravelingSalesmanAction(notVisited));

			return actions;
		}
	}

	private static class TSPResultFunction implements ResultFunction {
		
		public Object result(Object s, Action a) {
			
			TravelingSalesmanState state = (TravelingSalesmanState) s;
			City toVisit = ((TravelingSalesmanAction) a).getCity();
			
			if(state.getNotVisited().contains(toVisit)) {
				TravelingSalesmanState newState = new TravelingSalesmanState(state);
				newState.visit(toVisit);
				return newState;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
	
	private static class TSPStepCostFunction implements StepCostFunction {

		@Override
		public double c(Object s, Action a, Object sDelta) {

			TravelingSalesmanState state = (TravelingSalesmanState) s;
			
			City from = state.getLastVisited();
			
			if(from == null)
				return 0;
			
			return from.getCost(((TravelingSalesmanAction) a).getCity());
			
		}

	}

}
