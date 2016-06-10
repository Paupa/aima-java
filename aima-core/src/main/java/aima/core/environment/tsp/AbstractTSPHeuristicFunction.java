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
		
		City lastVisited = state.getLastVisited();
		
		if(lastVisited != null)
			citiesForRoutes.add(lastVisited);
		
		for(City city : state.getNotVisited())
			citiesForRoutes.add(city);
		
		City starterCity = state.getStarterCity();
		if(state.getNeedToComeBack() && starterCity != null)
			citiesForRoutes.add(starterCity);
		
		return citiesForRoutes;
	}
}
