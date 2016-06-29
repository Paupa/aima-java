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
		
		a.addCost(b, 54);
		a.addCost(c, 90);
		b.addCost(c, 86);
		b.addCost(a, 20);
		c.addCost(b, 80);
		c.addCost(a, 98);
		
		cities.add(a);
		cities.add(b);
		cities.add(c);
		
		TravelingSalesmanState state = new TravelingSalesmanState(cities, a);
		
		assertEquals((int) 20, (int) b.getCost(a));
		assertEquals((int) 80, (int) c.getCost(b));
		assertEquals((int) 98, (int) c.getCost(a));
		
		assertEquals(190, new HungarianAlgorithmHeuristicFunction().h(state), 0.1);
		
	}

}
