package aima.core.environment.tsp;

import java.util.List;

public class SumMinimumArcEachCityHeuristicFunction extends AbstractTSPHeuristicFunction {

	@Override
	public double h(Object state) {
		
		//We get all the routes than can be taken in this state
		List<City> toVisit = getCitiesForRoutes(state);
		
		double minArcSum = 0;
		
		for(City from : toVisit) {
			
			double minArc = Double.MAX_VALUE;
			
			for(City to : toVisit) {
				
				Integer cost = from.getCost(to);
				
				if(cost != null && cost < minArc)
					minArc = cost;
				
			}
			
			minArcSum += minArc;
		}
		
		return minArcSum;
	}

}
