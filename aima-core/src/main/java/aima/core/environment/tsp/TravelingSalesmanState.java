package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelingSalesmanState {
	
	private boolean needToComeBack = true;
	
	//Guardar ciudades visitadas
	private List<City> visited = new ArrayList<>();
	
	//Guardar ciudades por visitar
	private List<City> notVisited = new ArrayList<>();
	
	public TravelingSalesmanState(List<City> cities) {
		notVisited = cities;
	}
	
	public TravelingSalesmanState(List<City> cities, City starter) {
		this(cities);
		visit(starter);
	}
	
	public TravelingSalesmanState(TravelingSalesmanState state) {
		
		for(City city : state.getVisited())
			visited.add(city);
		
		for(City city : state.getNotVisited())
			notVisited.add(city);
		
		needToComeBack = state.getNeedToComeBack();
	}

	public boolean areAllCitiesVisited() {
		return notVisited.isEmpty();
	}
	
	public List<City> getNotVisited() {
		return Collections.unmodifiableList(notVisited);
	}
	
	public List<City> getVisited() {
		return Collections.unmodifiableList(visited);
	}
	
	public City getLastVisited() {
		if(visited.isEmpty())
			return null;
		
		return visited.get(visited.size() - 1);
	}

	public void visit(City city) {
		notVisited.remove(city);
		visited.add(city);
		
		
		if(notVisited.isEmpty() && needToComeBack) {
			notVisited.add(visited.get(0));
			needToComeBack = false;
		}
		
	}
	
	protected boolean getNeedToComeBack() {
		return needToComeBack;
	}

	@Override
	public String toString() {
		return "Visited\n" + visited + "\nNot visited\n" + notVisited;
	}
}
