package aima.core.environment.tsp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Paula Díaz Puertas
 */
public class City {
	
	private String id;
	
	private Map<City, Integer> canVisit = new HashMap<>();
	
	public City(String id) {
		
		assert(id != null);
		
		this.id = id;
	}

	public String getId() {
		return id;
	}
	
	/**
	 * Saves the cost passed, so going from this city to the city passed 
	 * costs what is passed.
	 * 
	 * @param city The city we want to be able to travel from this.
	 * @param cost Cost of going from this city to the city passed.
	 */
	public void addCost(City city, Integer cost) {
		canVisit.put(city, cost);
	}
	
	/**
	 * Saves the cost as a symmetric one. This means that going from this city 
	 * to the city passed costs the cost, and so does going from the city passed
	 * to this city.
	 * 
	 * @param city The city we want to be able to go and come
	 * @param cost The cost of traveling to and from the city
	 */
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
