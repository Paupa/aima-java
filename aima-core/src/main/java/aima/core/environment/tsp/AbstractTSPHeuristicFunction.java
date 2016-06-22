package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.framework.HeuristicFunction;


/**
 * This class helps TSP heuristic classes to get a list of which cities there are to visit
 * 
 * @author Paula Díaz Puertas
 *
 */
public abstract class AbstractTSPHeuristicFunction implements HeuristicFunction{

	/**
	 * Gets a list with the cities needed to see the routes there can be taken
	 * 
	 * @param state State that'll be used to get the cities
	 * @return A list with the cities needed for the routes.
	 * 		It's: last city visited + all the cities not visited + starter city
	 */
	public List<City> getCitiesForRoutes(Object state) {
		return getCitiesForRoutes((TravelingSalesmanState) state);
	}
	
	/**
	 * Gets a list with the cities needed to see the routes there can be taken
	 * 
	 * @param state State that'll be used to get the cities
	 * @return A list with the cities needed for the routes.
	 * 		It's: last city visited + all the cities not visited + starter city
	 */
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
