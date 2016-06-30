package aima.test.core.unit.environment.tsp;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import aima.core.environment.tsp.City;
import aima.core.environment.tsp.HungarianAlgorithmHeuristicFunction;
import aima.core.environment.tsp.TravelingSalesmanState;

/**
 * @author Paula Díaz Puertas
 */
public class HungarianAlgorithmHeuristicFunctionTest {

	@Test
	public void testH() {
		
		Set<City> cities = new HashSet<>();
		
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");
		
		a.addCost(b, 54D);
		a.addCost(c, 90D);
		b.addCost(c, 86D);
		b.addCost(a, 20D);
		c.addCost(b, 80D);
		c.addCost(a, 98D);
		
		cities.add(a);
		cities.add(b);
		cities.add(c);
		
		TravelingSalesmanState state = new TravelingSalesmanState(cities, a);
		
		double delta = 0.1;
		
		assertEquals(20, b.getCost(a), delta);
		assertEquals(80, c.getCost(b), delta);
		assertEquals(98, c.getCost(a), delta);
		
		assertEquals(190, new HungarianAlgorithmHeuristicFunction().h(state), delta);
		
	}

}
