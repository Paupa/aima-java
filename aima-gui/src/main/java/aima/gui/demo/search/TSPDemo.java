package aima.gui.demo.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.NullHeuristicFunction;
import aima.core.environment.tsp.City;
import aima.core.environment.tsp.TravelingSalesmanFunctionFactory;
import aima.core.environment.tsp.TravelingSalesmanGoalState;
import aima.core.environment.tsp.TravelingSalesmanState;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.QueueSearch;
import aima.core.search.framework.qsearch.RectifyExpandedGraphSearch;
import aima.core.search.informed.AStarSearch;

public class TSPDemo {

	public static void main(String[] args) {
		
		System.out.println("- TSP IMPLEMENTATION TESTING -\n\n");
		
		TSP1AStarForConsistentNullDemo();
		TSP1AStarReexpandingNullDemo();
		
		TSP2AStarForConsistentNullDemo();
		TSP2AStarReexpandingNullDemo();
		
		TSP3AStarForConsistentNullDemo();
		TSP3AStarReexpandingNullDemo();

	}

	private static TravelingSalesmanState instantiateProblem1() {
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");
		City d = new City("D");
		City e = new City("E");
		City f = new City("F");

		a.addSymmetricCost(f, 92);
		a.addSymmetricCost(e, 113);
		a.addSymmetricCost(d, 15);
		a.addSymmetricCost(c, 12);

		b.addSymmetricCost(f, 9);
		b.addSymmetricCost(e, 25);
		b.addSymmetricCost(d, 32);
		b.addSymmetricCost(c, 7);

		d.addSymmetricCost(f, 39);
		d.addSymmetricCost(e, 180);

		e.addSymmetricCost(f, 17);

		List<City> cities = new ArrayList<>();
		cities.add(a);
		cities.add(b);
		cities.add(c);
		cities.add(d);
		cities.add(e);
		cities.add(f);

		return new TravelingSalesmanState(cities, c);
	}
	
	private static TravelingSalesmanState instantiateProblem2() {
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");
		City d = new City("D");
		City e = new City("E");
		City f = new City("F");

		a.addSymmetricCost(b, 1);
		a.addSymmetricCost(c, 3);
		a.addSymmetricCost(d, 7);
		a.addSymmetricCost(e, 8);
		a.addSymmetricCost(f, 6);

		b.addSymmetricCost(c, 3);
		b.addSymmetricCost(d, 7);
		b.addSymmetricCost(e, 8);
		b.addSymmetricCost(f, 6);
		
		c.addSymmetricCost(d, 5);
		c.addSymmetricCost(e, 6);
		c.addSymmetricCost(f, 8);

		d.addSymmetricCost(e, 2);
		d.addSymmetricCost(f, 5);

		e.addSymmetricCost(f, 4);

		List<City> cities = new ArrayList<>();
		cities.add(a);
		cities.add(b);
		cities.add(c);
		cities.add(d);
		cities.add(e);
		cities.add(f);

		return new TravelingSalesmanState(cities, c);
	}
	
	private static TravelingSalesmanState instantiateProblem3() {
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");
		City d = new City("D");
		City e = new City("E");
		City f = new City("F");

		a.addSymmetricCost(b, 21);
		a.addSymmetricCost(c, 12);
		a.addSymmetricCost(d, 15);
		a.addSymmetricCost(e, 113);
		a.addSymmetricCost(f, 92);

		b.addSymmetricCost(c, 7);
		b.addSymmetricCost(d, 32);
		b.addSymmetricCost(e, 25);
		b.addSymmetricCost(f, 9);
		
		c.addSymmetricCost(d, 5);
		c.addSymmetricCost(e, 18);
		c.addSymmetricCost(f, 20);

		d.addSymmetricCost(e, 180);
		d.addSymmetricCost(f, 39);

		e.addSymmetricCost(f, 17);

		List<City> cities = new ArrayList<>();
		cities.add(a);
		cities.add(b);
		cities.add(c);
		cities.add(d);
		cities.add(e);
		cities.add(f);

		return new TravelingSalesmanState(cities, c);
	}

	private static void AStarSearch(String title, TravelingSalesmanState initialState,
			QueueSearch qSearch, HeuristicFunction heuristic) {

		System.out.println(title + "\n");
		System.out.println("Initial State:\n" + initialState.toString() + "\n");

		try {
			Problem problem = new Problem(initialState, TravelingSalesmanFunctionFactory.getActionsFunction(),
					TravelingSalesmanFunctionFactory.getResultFunction(), new TravelingSalesmanGoalState(), 
					TravelingSalesmanFunctionFactory.getStepCostFunction());
			Search search = new AStarSearch(qSearch, heuristic);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			System.out.println();
			printInstrumentation(agent.getInstrumentation());

			System.out.println("\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void TSP1AStarReexpandingNullDemo() {
		AStarSearch("Problem 1 Null heuristic [Reexpanding]", instantiateProblem1(), 
				new RectifyExpandedGraphSearch(), new NullHeuristicFunction());
	}
	
	private static void TSP1AStarForConsistentNullDemo() {
		AStarSearch("Problem 1 Null heuristic [CONSISTENT]", instantiateProblem1(),
				new GraphSearch(), new NullHeuristicFunction());
	}
	
	private static void TSP2AStarReexpandingNullDemo() {
		AStarSearch("Problem 2 Null heuristic [Reexpanding]", instantiateProblem2(), 
				new RectifyExpandedGraphSearch(), new NullHeuristicFunction());
	}
	
	private static void TSP2AStarForConsistentNullDemo() {
		AStarSearch("Problem 2 Null heuristic [CONSISTENT]", instantiateProblem2(),
				new GraphSearch(), new NullHeuristicFunction());
	}
	
	private static void TSP3AStarReexpandingNullDemo() {
		AStarSearch("Problem 3 Null heuristic [Reexpanding]", instantiateProblem3(), 
				new RectifyExpandedGraphSearch(), new NullHeuristicFunction());
	}
	
	private static void TSP3AStarForConsistentNullDemo() {
		AStarSearch("Problem 3 Null heuristic [CONSISTENT]", instantiateProblem3(),
				new GraphSearch(), new NullHeuristicFunction());
	}

	// -- Métodos auxiliares para mostrar resultados --

	private static void printInstrumentation(Properties properties) {
		
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}
	}

	private static void printActions(List<Action> actions) {
		
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}
