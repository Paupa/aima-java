package aima.core.environment.tsp;

import java.util.List;

public class MinimumCostArcHeuristicFunction extends AbstractTSPHeuristicFunction {

	@Override
	public double h(Object state) {
		
		//We get all the routes than can be taken in this state
		List<City> toVisit = getCitiesForRoutes(state);
		
		//We find the route with the minimum cost and the routes
		double minCost = Double.MAX_VALUE;
		//We save the number of routes that can be taken
		int routes = 0;
		
		for(City from : toVisit) {
			
			for(City to : toVisit) {
				
				Integer cost = from.getCost(to);
				
				if(cost != null) {
					routes++;
					
					if(cost < minCost)
						minCost = cost;
				}
			}
		}
		
		return minCost * routes;
	}

}
