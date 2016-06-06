package aima.core.search.framework.qsearch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aima.core.agent.Action;
import aima.core.search.framework.Node;
import aima.core.search.framework.Problem;
import aima.core.util.datastructure.Queue;

public class GraphSearchReinsertExpanded extends QueueSearch {
	
	public static final String METRIC_NODES_REINSERTED = "nodesReinserted";
	
	private Map<Object, Node> explored = new HashMap<>();
	
	/**
	 * Clears the map of opened nodes and calls the search implementation of
	 * <code>QueueSearch</code>
	 */
	@Override
	public List<Action> search(Problem problem, Queue<Node> frontier) {
		// Initialize the opened map to be empty
		explored.clear();
		return super.search(problem, frontier);
	}
	
	@Override
	protected void insertIntoFrontier(Node node) {
		
		// If this state has already been explored...
		if(explored.containsKey(node.getState())) {
			Node exploredNode = explored.get(node.getState());
			
			// If a better path has been found, we reinsert the node in the frontier
			if(node.getPathCost() < exploredNode.getPathCost()) {
				frontier.remove(exploredNode);
				frontier.add(node);
				explored.replace(node.getState(), node);
				metrics.incrementInt(METRIC_NODES_REINSERTED);
			}
		}
		
		else {
			// If the node hasn't been opened before it's put in the opened map and inserted in the frontier
			
			frontier.insert(node);
			updateMetrics(frontier.size());
			explored.put(node.getState(), node);
		}
		
	}

	@Override
	protected Node popNodeFromFrontier() {
		Node result = frontier.pop();
		updateMetrics(frontier.size());
		return result;
	}

	@Override
	protected boolean isFrontierEmpty() {
		return frontier.isEmpty();
	}
	
	@Override
	public void clearInstrumentation() {
		super.clearInstrumentation();
		metrics.set(METRIC_NODES_REINSERTED, 0);
	}

}
