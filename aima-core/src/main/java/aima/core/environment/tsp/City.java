package aima.core.environment.tsp;

import java.util.HashMap;
import java.util.Map;

public class City {
	
	private String id;
	
	private Map<City, Integer> canVisit = new HashMap<>();
	
	public City(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	public void addCost(City city, Integer cost) {
		canVisit.put(city, cost);
	}
	
	public void addSymmetricCost(City city, Integer cost) {
		addCost(city, cost);
		city.addCost(this, cost);
	}
	
	public Integer getCost(City city) {
		return canVisit.get(city);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		City other = (City) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City " + id;
	}
	
}
