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
import aima.core.search.local.AbstractGeneticAlgorithm;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.Individual;
import aima.gui.demo.search.util.AbstractTSPGeneticAlgorithmInstantiator;
import aima.gui.demo.search.util.TSPGeneticAlgorithmInstantiator;
import aima.gui.demo.search.util.TSPSingleGeneticAlgorithmInstantiator;
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
		// heuristics.add(new TitledPart<HeuristicFunction>("Minimum cost arcs
		// heuristic", new MinimumCostArcHeuristicFunction()));
		// heuristics.add(new TitledPart<HeuristicFunction>("Sum minimum arc for
		// each city heuristic", new SumMinimumArcEachCityHeuristicFunction()));
		// heuristics.add(new TitledPart<HeuristicFunction>("Sum all remaining
		// arcs heuristic", new SumArcsHeuristicFunction()));
		//heuristics.add(new TitledPart<HeuristicFunction>("Spanning tree heuristic", new SpanningTreeHeuristicFunction()));
		// heuristics.add(new TitledPart<HeuristicFunction>("Hungarian algorithm
		// heuristic", new HungarianAlgorithmHeuristicFunction()));

		List<TitledPart<QueueSearch>> searchs = new ArrayList<>();
		// searchs.add(new TitledPart<QueueSearch>("[CONSISTENT]", new
		// GraphSearch()));
		searchs.add(new TitledPart<QueueSearch>("[RECTIFY EXPANDED]", new GraphSearchRectifyExpanded()));
		// searchs.add(new TitledPart<QueueSearch>("[REINSERT EXPANDED]", new
		// GraphSearchReinsertExpanded()));

		List<TitledPart<AbstractTSPGeneticAlgorithmInstantiator>> instantiators = new ArrayList<>();
		instantiators.add(new TitledPart<AbstractTSPGeneticAlgorithmInstantiator>("[GENETIC ALGORITHM]",
				new TSPGeneticAlgorithmInstantiator()));
		//instantiators.add(new TitledPart<AbstractTSPGeneticAlgorithmInstantiator>("[SINGLE GENETIC ALGORITHM]",
			//	new TSPSingleGeneticAlgorithmInstantiator()));

		for (TitledPart<TravelingSalesmanState> problem : problems) {

			for (TitledPart<HeuristicFunction> heuristic : heuristics) {

				for (TitledPart<QueueSearch> search : searchs) {

					AStarSearch(search.getTitle() + " " + problem.getTitle() + " " + heuristic.getTitle(),
							problem.getPart(), search.getPart(), heuristic.getPart());
				}
			}

			for (TitledPart<AbstractTSPGeneticAlgorithmInstantiator> instantiator : instantiators)
				geneticAlgorithmSearch(instantiator.getTitle() + " " + problem.getTitle(), problem.getPart(),
						instantiator.getPart());
		}

	}

	private static TravelingSalesmanState instantiateProblem1() {
		City a = new City("A");
		City b = new City("B");
		City c = new City("C");
		City d = new City("D");
		City e = new City("E");
		City f = new City("F");

		a.addSymmetricCost(f, 92D);
		a.addSymmetricCost(e, 113D);
		a.addSymmetricCost(d, 15D);
		a.addSymmetricCost(c, 12D);

		b.addSymmetricCost(f, 9D);
		b.addSymmetricCost(e, 25D);
		b.addSymmetricCost(d, 32D);
		b.addSymmetricCost(c, 7D);

		d.addSymmetricCost(f, 39D);
		d.addSymmetricCost(e, 180D);

		e.addSymmetricCost(f, 17D);

		Set<City> cities = new HashSet<>();
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

		a.addSymmetricCost(b, 1D);
		a.addSymmetricCost(c, 3D);
		a.addSymmetricCost(d, 7D);
		a.addSymmetricCost(e, 8D);
		a.addSymmetricCost(f, 6D);

		b.addSymmetricCost(c, 3D);
		b.addSymmetricCost(d, 7D);
		b.addSymmetricCost(e, 8D);
		b.addSymmetricCost(f, 6D);

		c.addSymmetricCost(d, 5D);
		c.addSymmetricCost(e, 6D);
		c.addSymmetricCost(f, 8D);

		d.addSymmetricCost(e, 2D);
		d.addSymmetricCost(f, 5D);

		e.addSymmetricCost(f, 4D);

		Set<City> cities = new HashSet<>();
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

		a.addSymmetricCost(b, 21D);
		a.addSymmetricCost(c, 12D);
		a.addSymmetricCost(d, 15D);
		a.addSymmetricCost(e, 113D);
		a.addSymmetricCost(f, 92D);

		b.addSymmetricCost(c, 7D);
		b.addSymmetricCost(d, 32D);
		b.addSymmetricCost(e, 25D);
		b.addSymmetricCost(f, 9D);

		c.addSymmetricCost(d, 5D);
		c.addSymmetricCost(e, 18D);
		c.addSymmetricCost(f, 20D);

		d.addSymmetricCost(e, 180D);
		d.addSymmetricCost(f, 39D);

		e.addSymmetricCost(f, 17D);

		Set<City> cities = new HashSet<>();
		cities.add(a);
		cities.add(b);
		cities.add(c);
		cities.add(d);
		cities.add(e);
		cities.add(f);

		return new TravelingSalesmanState(cities, c);
	}

	private static void AStarSearch(String title, TravelingSalesmanState initialState, QueueSearch qSearch,
			HeuristicFunction heuristic) {

		System.out.println(title + "\n");
		System.out.println("Initial State:\n" + initialState.toString() + "\n");

		try {
			Problem problem = new Problem(initialState, TravelingSalesmanFunctionFactory.getActionsFunction(),
					TravelingSalesmanFunctionFactory.getResultFunction(), new TravelingSalesmanGoalTest(),
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

	private static void geneticAlgorithmSearch(String title, TravelingSalesmanState problem,
			AbstractTSPGeneticAlgorithmInstantiator instantiator) {
		System.out.println(title + "\n");
		try {
			FitnessFunction<City> fitnessFunction = TSPGeneticAlgorithmUtil.getFitnessFunction();
			GoalTest goalTest = TSPGeneticAlgorithmUtil.getGoalTest();

			List<City> cities = instantiator.getAllCities(problem);

			// Generate an initial population
			Set<Individual<City>> population = new HashSet<>();
			for (int i = 0; i < 50; i++) {
				population.add(TSPGeneticAlgorithmUtil.generateRandomIndividual(cities));
			}

			AbstractGeneticAlgorithm<City> ga = instantiator.instantianteGeneticAlgorithm(problem);

			Individual<City> firstBest = ga.retrieveBestIndividual(population, fitnessFunction);

			// Run for a set amount of time
			Individual<City> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest);

			String originalStringRepresentation = getStringRepresentation(firstBest);
			String stringRepresentation = getStringRepresentation(bestIndividual);

			String stringMaxTime = "";

			/*
			 * if(maxTimeMilliseconds > 0) stringMaxTime = "Max Time (" +
			 * maxTimeMilliseconds + ") ";
			 */

			double originalCost = 1 / fitnessFunction.getValue(firstBest);
			double fitness = fitnessFunction.getValue(bestIndividual);

			System.out.println("Best Individual from initial population (cost = " + originalCost + ")\n"
					+ originalStringRepresentation + "\n");

			System.out.println(stringMaxTime + "Best Individual\n" + stringRepresentation);
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

	private static String getStringRepresentation(Individual<City> cromosome) {
		List<City> representation = cromosome.getRepresentation();

		String stringRepresentation = representation.get(0).toString();

		for (int i = 1; i < representation.size(); i++)
			stringRepresentation += " -> " + representation.get(i);

		return stringRepresentation;
	}
}
