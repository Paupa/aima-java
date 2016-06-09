package aima.gui.demo.search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction;
import aima.core.environment.eightpuzzle.NoConsistentHeuristicFunction;
import aima.core.environment.eightpuzzle.NullHeuristicFunction;
import aima.core.search.framework.HeuristicFunction;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.framework.qsearch.QueueSearch;
import aima.core.search.framework.qsearch.GraphSearchRectifyExpanded;
import aima.core.search.framework.qsearch.GraphSearchReinsertExpanded;
import aima.core.search.informed.AStarSearch;
import aima.gui.demo.search.util.TitledPart;

public class AStarSearchNonConsistentHeuristicDemo {

	// Este es el estado inicial
	static EightPuzzleBoard initialState = new EightPuzzleBoard(new int[]
	// coste 25
	// {6, 7, 4, 0, 5, 1, 3, 2, 8}
	// {6, 0, 7, 5, 4, 1, 3, 8, 2}
	// {3, 4, 8, 5, 7, 1, 6, 0, 2}
	// {4, 5, 3, 7, 6, 2, 8, 0, 1}
	// {2, 7, 8, 5, 4, 0, 3, 1, 6}
	// coste 30
	// { 5, 6, 7, 2, 8, 4, 0, 3, 1 }
	// {5, 6, 7, 4, 0, 8, 3, 2, 1}
	// {5, 4, 7, 6, 0, 3, 8, 2, 1}
	// {3, 8, 7, 4, 0, 6, 5, 2, 1}
	// {5, 6, 3, 4, 0, 2, 7, 8, 1}
	// Instancias que no se resuelven con h3 y la version de A* que no rectifica
	// { 6, 3, 2, 5, 7, 8, 0, 4, 1 } // 24
	 { 6, 3, 2, 5, 8, 1, 4, 0, 7 } // 23
	// { 3, 5, 6, 4, 2, 7, 0, 8, 1 } // 24
	// { 7, 0, 2, 3, 6, 1, 5, 8, 4 } // 21
	);

	static EightPuzzleGoalTest goalState = new EightPuzzleGoalTest();

	public static void main(String[] args) {

		// Quiero probar diferencias de resultado usando GraphSearch y
		// ReexpandingGraphSearch
		// Con heuristicos monotonos el resultado deberia ser EL MISMO
		// Con heuristicos no monotonos el ReexpandingGraphSearch deberia
		// encontrar soluciones iguales o mejores

		System.out.println("- ISSUE 75 TESTING -\n\n");

		List<TitledPart<HeuristicFunction>> heuristics = new ArrayList<>();
		heuristics.add(new TitledPart<HeuristicFunction>("Null heuristic", new NullHeuristicFunction()));
		heuristics.add(new TitledPart<HeuristicFunction>("Misplaced heuristic",
				new MisplacedTilleHeuristicFunction(goalState)));
		heuristics.add(
				new TitledPart<HeuristicFunction>("Manhattan heuristic", new ManhattanHeuristicFunction(goalState)));
		heuristics.add(new TitledPart<HeuristicFunction>("Non-consistent heuristic",
				new NoConsistentHeuristicFunction(goalState)));

		List<TitledPart<QueueSearch>> searchs = new ArrayList<>();
		searchs.add(new TitledPart<QueueSearch>("[CONSISTENT]", new GraphSearch()));
		searchs.add(new TitledPart<QueueSearch>("[RECTIFY EXPANDED]", new GraphSearchRectifyExpanded()));
		searchs.add(new TitledPart<QueueSearch>("[REINSERT EXPANDED]", new GraphSearchReinsertExpanded()));

		for (TitledPart<HeuristicFunction> heuristic : heuristics) {

			for (TitledPart<QueueSearch> search : searchs) {

				AStarSearch(search.getTitle() + " " + heuristic.getTitle(), search.getPart(), heuristic.getPart());
			}
		}
	}

	private static void AStarSearch(String title, QueueSearch qSearch, HeuristicFunction heuristic) {

		System.out.println(title + "\n");
		System.out.println("Initial State:\n" + initialState.toString() + "\n");
		try {
			Problem problem = new Problem(initialState, EightPuzzleFunctionFactory.getActionsFunction(),
					EightPuzzleFunctionFactory.getResultFunction(), goalState);
			Search search = new AStarSearch(qSearch, heuristic);
			SearchAgent agent = new SearchAgent(problem, search);
			System.out.println("Goal State:\n" + goalState.toString() + "\n");
			// printActions(agent.getActions());
			// System.out.println();
			printInstrumentation(agent.getInstrumentation());

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
