package aima.core.environment.tsp;

import java.util.List;

import aima.core.util.datastructure.PriorityQueue;

/**
 * @author Paula Díaz Puertas
 */
public class SumMinimumArcsHeuristicFunction extends AbstractTSPHeuristicFunction {

	@Override
	public double h(Object state) {
		
		//We get all the routes than can be taken in this state
		List<City> toVisit = getCitiesForRoutes(state);
		
		PriorityQueue<Integer> arcs = new PriorityQueue<>();
		
		for(City from : toVisit) {
			
			for(City to : toVisit) {
				
				Integer cost = from.getCost(to);
				
				if(cost != null)
					arcs.add(cost);
				
			}
		}
		
		double minArcSum = 0;
		
		for(int i = 1; i < toVisit.size(); i++) {
			if(!arcs.isEmpty())
				minArcSum += arcs.poll();
		}
		
		return minArcSum;
	}

}
