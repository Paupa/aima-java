package aima.core.environment.tsp;

import aima.core.agent.impl.DynamicAction;

public class TravelingSalesmanAction extends DynamicAction {
	
	public static final String VISIT = "visit";
	public static final String ATTRIBUTE_CITY = "city";

	public TravelingSalesmanAction(City city) {
		super(VISIT);
		setAttribute(ATTRIBUTE_CITY, city);
	}
	
	public City getCity() {
		return (City) getAttribute(ATTRIBUTE_CITY);
	}

}
