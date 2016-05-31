package aima.gui.demo.search;

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
import aima.core.search.framework.qsearch.RectifyExpandedGraphSearch;
import aima.core.search.framework.qsearch.ReexpandingGraphSearch;
import aima.core.search.informed.AStarSearch;

public class AStarSearchNonConsistentHeuristicDemo {

	// Este es el estado inicial
	static EightPuzzleBoard initialState = new EightPuzzleBoard(new int[]
	 {2, 1, 3, 8, 0, 4, 7, 6, 5 } // Coste 24
	//{ 1, 4, 2, 7, 5, 8, 3, 0, 6 } // Coste 9
	//
	// Otras instancias
	// { 4, 5, 7, 6, 0, 3, 8, 2, 1} // Coste 20
	// { 8, 3, 7, 4, 0, 6, 5, 2, 1} // Coste 24
	// { 6, 5, 3, 4, 0, 2, 7, 8, 1} // Coste 20
	// { 3, 4, 7, 0, 8, 6, 5, 2, 1 } // Coste 23
	);

	static EightPuzzleGoalTest goalState = new EightPuzzleGoalTest();

	public static void main(String[] args) {

		// Quiero probar diferencias de resultado usando GraphSearch y
		// ReexpandingGraphSearch
		// Con heuristicos monotonos el resultado deberia ser EL MISMO
		// Con heuristicos no monotonos el ReexpandingGraphSearch deberia
		// encontrar soluciones iguales o mejores

		System.out.println("- ISSUE 75 TESTING -\n\n");

		// Diferencias con el heuristico Null
		eightPuzzleAStarForConsistentNullDemo();
		eightPuzzleAStarRectifyExpandedNullDemo();
		eightPuzzleAStarReexpandingNullDemo();

		// Diferencias con el heuristico Misplaced
		eightPuzzleAStarForConsistentMisplacedDemo();
		eightPuzzleAStarRectifyExpandedMisplacedDemo();
		eightPuzzleAStarReexpandingMisplacedDemo();

		// Diferencias con el heuristico Manhattan
		eightPuzzleAStarForConsistentManhattanDemo();
		eightPuzzleAStarRectifyExpandedManhattanDemo();
		eightPuzzleAStarReexpandingManhattanDemo();

		// Diferencias con el heuristico NoConsistent
		eightPuzzleAStarForConsistentNoConsistentDemo();
		eightPuzzleAStarRectifyExpandedNoConsistentDemo();
		eightPuzzleAStarReexpandingNoConsistentDemo();

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

	private static void AStarRectifyExpandedSearch(String title, HeuristicFunction heuristic) {
		AStarSearch("[RECTIFY EXPANDED] " + title, new RectifyExpandedGraphSearch(), heuristic);
	}

	private static void AStarReexpandingSearch(String title, HeuristicFunction heuristic) {
		AStarSearch("[REEXPANDING] " + title, new ReexpandingGraphSearch(), heuristic);
	}

	private static void AStarConsistentSearch(String title, HeuristicFunction heuristic) {
		AStarSearch("[CONSISTENT] " + title, new GraphSearch(), heuristic);
	}

	// --------------------------------------------------------------------------------------------
	// -- A* Search AUTENTICO con los 4 heurísticos conocidos, h0, h1, h2, h3 --
	// -
	// - es la de Russell y Norving modificada para que rectifique nodos ya expandidos
	// --------------------------------------------------------------------------------------------

	private static void eightPuzzleAStarRectifyExpandedNullDemo() {
		AStarRectifyExpandedSearch("Null heuristic", new NullHeuristicFunction());
	}

	private static void eightPuzzleAStarRectifyExpandedMisplacedDemo() {
		AStarRectifyExpandedSearch("Misplaced heuristic", new MisplacedTilleHeuristicFunction());
	}

	private static void eightPuzzleAStarRectifyExpandedManhattanDemo() {
		AStarRectifyExpandedSearch("Manhattan heuristic", new ManhattanHeuristicFunction());
	}

	private static void eightPuzzleAStarRectifyExpandedNoConsistentDemo() {
		AStarRectifyExpandedSearch("Non-consistent heuristic", new NoConsistentHeuristicFunction());
	}

	// --------------------------------------------------------------------------------------------
	// -- A* Search AUTENTICO con los 4 heurísticos conocidos, h0, h1, h2, h3 --
	// -
	// - es la de Russell y Norving modificada para que reinserte nodos ya expandidos en ABIERTA -
	// --------------------------------------------------------------------------------------------

	private static void eightPuzzleAStarReexpandingNullDemo() {
		AStarReexpandingSearch("Null heuristic", new NullHeuristicFunction());
	}

	private static void eightPuzzleAStarReexpandingMisplacedDemo() {
		AStarReexpandingSearch("Misplaced heuristic", new MisplacedTilleHeuristicFunction());
	}

	private static void eightPuzzleAStarReexpandingManhattanDemo() {
		AStarReexpandingSearch("Manhattan heuristic", new ManhattanHeuristicFunction());
	}

	private static void eightPuzzleAStarReexpandingNoConsistentDemo() {
		AStarReexpandingSearch("Non-consistent heuristic", new NoConsistentHeuristicFunction());
	}

	// ---------------------------------------------------------
	// -- A* Search, version de Russell y Norvig
	// que es admisible solo para heuristicos consistentes --
	// ---------------------------------------------------------

	private static void eightPuzzleAStarForConsistentNullDemo() {
		AStarConsistentSearch("Null heuristic", new NullHeuristicFunction());
	}

	private static void eightPuzzleAStarForConsistentMisplacedDemo() {
		AStarConsistentSearch("Misplaced heuristic", new MisplacedTilleHeuristicFunction());
	}

	private static void eightPuzzleAStarForConsistentManhattanDemo() {
		AStarConsistentSearch("Manhattan heuristic", new ManhattanHeuristicFunction());
	}

	private static void eightPuzzleAStarForConsistentNoConsistentDemo() {
		AStarConsistentSearch("Non-Consistent heuristic", new NoConsistentHeuristicFunction());
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
