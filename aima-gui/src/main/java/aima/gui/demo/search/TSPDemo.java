package aima.gui.demo.search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.NullHeuristicFunction;
import aima.core.environment.tsp.*;
import aima.core.search.framework.GoalTest;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.qsearch.*;
import aima.core.search.informed.AStarSearch;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;
import aima.gui.demo.search.util.TitledPart;

public class TSPDemo {

	public static void main(String[] args) {
		
		System.out.println("- TSP IMPLEMENTATION TESTING -\n\n");
		
		List<TitledPart<TravelingSalesmanState>> problems = new ArrayList<>();
		problems.add(new TitledPart<TravelingSalesmanState>("Problem 1", instantiateProblem1()));
		problems.add(new TitledPart<TravelingSalesmanState>("Problem 2", instantiateProblem2()));
		problems.add(new TitledPart<TravelingSalesmanState>("Problem 3", instantiateProblem3()));
		
		List<TitledPart<HeuristicFunction>> heuristics = new ArrayList<>();
		heuristics.add(new TitledPart<HeuristicFunction>("Null heuristic", new NullHeuristicFunction()));
		//heuristics.add(new TitledPart<HeuristicFunction>("Minimum cost arcs heuristic", new MinimumCostArcHeuristicFunction()));
		//heuristics.add(new TitledPart<HeuristicFunction>("Sum minimum arc for each city heuristic", new SumMinimumArcEachCityHeuristicFunction()));
		//heuristics.add(new TitledPart<HeuristicFunction>("Sum all remaining arcs heuristic", new SumArcsHeuristicFunction()));
		heuristics.add(new TitledPart<HeuristicFunction>("Spanning tree heuristic", new SpanningTreeHeuristicFunction()));
		heuristics.add(new TitledPart<HeuristicFunction>("Hungarian algorithm heuristic", new HungarianAlgorithmHeuristicFunction()));
		
		List<TitledPart<QueueSearch>> searchs = new ArrayList<>();
		searchs.add(new TitledPart<QueueSearch>("[CONSISTENT]", new GraphSearch()));
		//searchs.add(new TitledPart<QueueSearch>("[RECTIFY EXPANDED]", new GraphSearchRectifyExpanded()));
		//searchs.add(new TitledPart<QueueSearch>("[REINSERT EXPANDED]", new GraphSearchReinsertExpanded()));
		
		for(TitledPart<TravelingSalesmanState> problem : problems) {
			
			for(TitledPart<HeuristicFunction> heuristic : heuristics) {
				
				for(TitledPart<QueueSearch> search : searchs) {
					
					AStarSearch(search.getTitle() + " " + problem.getTitle() + " " + heuristic.getTitle(), 
							problem.getPart(), search.getPart(), heuristic.getPart());
				}
			}
			
			geneticAlgorithmSearch(problem.getTitle(), problem.getPart(), 0);
		}

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
	
	public static void geneticAlgorithmSearch(String title, TravelingSalesmanState problem, long maxTimeMilliseconds) {
		System.out.println("[GENETIC ALGORITHM] " + title + "\n");
		try {
			FitnessFunction<City> fitnessFunction = TSPGeneticAlgorithmUtil.getFitnessFunction();
			GoalTest goalTest = TSPGeneticAlgorithmUtil.getGoalTest();
			
			List<City> cities = new ArrayList<>();
			
			if(problem.getStarterCity() != null)
				cities.add(problem.getStarterCity());
			
			cities.addAll(problem.getNotVisited());
			
			// Generate an initial population
			Set<Individual<City>> population = new HashSet<>();
			for (int i = 0; i < 50; i++) {
				population.add(TSPGeneticAlgorithmUtil.generateRandomIndividual(cities));
			}
			
			Set<City> finiteAlphabet = new HashSet<>();
			
			finiteAlphabet.addAll(cities);

			GeneticAlgorithm<City> ga = new GeneticAlgorithm<>(cities.size(),
					finiteAlphabet, 0.15);

			// Run for a set amount of time
			Individual<City> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, maxTimeMilliseconds);
			
			List<City> representation = bestIndividual.getRepresentation();
			
			String stringRepresentation = representation.get(0).toString();
			
			for(int i = 1; i < representation.size(); i++)
				stringRepresentation += " -> " + representation.get(i);
			
			String stringMaxTime = "";
			
			if(maxTimeMilliseconds > 0)
				stringMaxTime = "Max Time (" + maxTimeMilliseconds + ") ";
			
			double fitness = fitnessFunction.getValue(bestIndividual);

			System.out.println(stringMaxTime + "Best Individual=\n"
					+ stringRepresentation);
			System.out.println("Fitness         = " + fitness);
			System.out.println("Cost         	= " + 1 / fitness);
			System.out.println("Is Goal         = " + goalTest.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");
			
			System.out.println("\n");

		} catch (Exception e) {
			e.printStackTrace();
		}
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
