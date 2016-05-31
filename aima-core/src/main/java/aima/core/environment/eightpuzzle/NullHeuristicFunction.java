package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

/**
 * @author Ravi Mohan
 * 
 */
public class NullHeuristicFunction implements HeuristicFunction {

	public double h(Object state) {
		return (double)0;
	}
}