package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.List;

public class SpanningTreeHeuristicFunction extends AbstractTSPHeuristicFunction {

	@Override
	public double h(Object state) {
		
		List<City> toVisit = this.getCitiesForRoutes(state);
		
		if(toVisit.isEmpty())
			return 0;
		
		List<City> alreadyVisited = new ArrayList<>();
		alreadyVisited.add(toVisit.get(0));
		toVisit.remove(0);
		
		double totalCost = 0;
		
		while(!toVisit.isEmpty()) {
			double minimumArc = Double.MAX_VALUE;
			City minimumCity = null;
			
			for(int i = 0; i < alreadyVisited.size(); i++) {
				City alreadyConected = alreadyVisited.get(i);
				
				for(int j = 0; j < toVisit.size(); j++) {
					City toConect = toVisit.get(j);
					
					Integer cost = alreadyConected.getCost(toConect);
					if(cost != null && cost < minimumArc) {
						minimumArc = cost;
						minimumCity = toConect;
					}
				}
			}
			
			if(minimumCity == null)
				return minimumArc;
			
			alreadyVisited.add(minimumCity);
			toVisit.remove(minimumCity);
			
			totalCost += minimumArc;
		}
		
		return totalCost;
	}

}
