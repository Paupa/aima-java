package aima.core.environment.tsp;

import java.util.List;

public class SumArcsHeuristicFunction extends AbstractTSPHeuristicFunction {

	@Override
	public double h(Object state) {
		
		List<City> toVisit = this.getCitiesForRoutes(state);
		
		double sumArcs = 0;
		
		int routes = 0;
		
		for(City from : toVisit) {
			
			for(City to : toVisit) {
				
				Integer cost = from.getCost(to);
				
				if(cost != null) {
					sumArcs += cost;
					routes++;
				}
			}
		}
		
		return sumArcs / routes * toVisit.size();
	}

}
