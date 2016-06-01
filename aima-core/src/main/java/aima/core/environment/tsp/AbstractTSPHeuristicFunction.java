package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.framework.HeuristicFunction;

public abstract class AbstractTSPHeuristicFunction implements HeuristicFunction{

	public List<City> getCitiesForRoutes(Object state) {
		return getCitiesForRoutes((TravelingSalesmanState) state);
	}
	
	public List<City> getCitiesForRoutes(TravelingSalesmanState state) {
		List<City> citiesForRoutes = new ArrayList<>();
		
		if(!state.getVisited().isEmpty())
			citiesForRoutes.add(state.getLastVisited());
		
		for(City city : state.getNotVisited())
			citiesForRoutes.add(city);
		
		if(state.getNeedToComeBack() && !state.getVisited().isEmpty())
			citiesForRoutes.add(state.getVisited().get(0));
		
		return citiesForRoutes;
	}
}
