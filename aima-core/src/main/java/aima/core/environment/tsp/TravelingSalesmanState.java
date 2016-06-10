package aima.core.environment.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravelingSalesmanState {
	
	private boolean needToComeBack = true;
	
	private City starterCity = null;
	
	private City lastVisited = null;
	
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
		
		starterCity = state.getStarterCity();
		lastVisited = state.getLastVisited();
		
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
	
	public City getStarterCity() {
		return starterCity;
	}
	
	public City getLastVisited() {
		
		return lastVisited;
	}

	public void visit(City city) {
		notVisited.remove(city);
		lastVisited = city;
		
		if(starterCity == null)
			starterCity = city;
		
		if(notVisited.isEmpty() && needToComeBack) {
			notVisited.add(starterCity);
			needToComeBack = false;
		}
		
	}
	
	protected boolean getNeedToComeBack() {
		return needToComeBack;
	}

	@Override
	public String toString() {
		return "Starter city\n" + starterCity + "\nLast visited\n" + lastVisited + "\nNot visited\n" + notVisited;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lastVisited == null) ? 0 : lastVisited.hashCode());
		result = prime * result + (needToComeBack ? 1231 : 1237);
		result = prime * result + ((notVisited == null) ? 0 : notVisited.hashCode());
		result = prime * result + ((starterCity == null) ? 0 : starterCity.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TravelingSalesmanState other = (TravelingSalesmanState) obj;
		if (lastVisited == null) {
			if (other.lastVisited != null)
				return false;
		} else if (!lastVisited.equals(other.lastVisited))
			return false;
		if (needToComeBack != other.needToComeBack)
			return false;
		if (notVisited == null) {
			if (other.notVisited != null)
				return false;
		} else if (!notVisited.equals(other.notVisited))
			return false;
		if (starterCity == null) {
			if (other.starterCity != null)
				return false;
		} else if (!starterCity.equals(other.starterCity))
			return false;
		return true;
	}
}
