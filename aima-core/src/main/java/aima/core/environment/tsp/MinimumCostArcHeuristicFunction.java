package aima.core.environment.tsp;

import java.util.List;

/**
 * @author Paula Díaz Puertas
 */
public class MinimumCostArcHeuristicFunction extends AbstractTSPHeuristicFunction {

	@Override
	public double h(Object state) {
		
		//We get all the routes than can be taken in this state
		List<City> toVisit = getCitiesForRoutes(state);
		
		//We find the route with the minimum cost and the routes
		double minCost = Double.MAX_VALUE;
		
		for(City from : toVisit) {
			
			for(City to : toVisit) {
				
				Double cost = from.getCost(to);
				
				if(cost != null && cost < minCost)
						minCost = cost;
			}
		}
		
		return minCost * (toVisit.size() - 1);
	}

}
