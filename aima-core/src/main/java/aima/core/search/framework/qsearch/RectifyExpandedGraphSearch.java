package aima.core.search.framework.qsearch;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import aima.core.agent.Action;
import aima.core.search.framework.Node;
import aima.core.search.framework.Problem;
import aima.core.search.framework.RectifiableNode;
import aima.core.util.datastructure.Queue;

/**
 * This type of search only works with <code>RectifiableNode</code>.
 * 
 * @author Paula Díaz Puertas
 *
 */
public class RectifyExpandedGraphSearch extends QueueSearch {
	
	public static final String METRIC_NODES_RECTIFIED = "nodesRectified";
	
	// Saves all the nodes that have been put in the frontier. The key is their state.
	private Map<Object, RectifiableNode> explored = new HashMap<>();
	
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
	
	/**
	 * Inserts the node at the tail of the frontier if the corresponding state
	 * was not yet explored.
	 */
	protected void insertIntoFrontier(Node node) {
		
		// If the node has already been opened...
		if(explored.containsKey(node.getState())) {
			RectifiableNode alreadyOpenedNode = explored.get(node.getState());
			
			// If the new path is better than the path we had before...
			if(node.getPathCost() < alreadyOpenedNode.getPathCost()) {
				// We modify the data of the node we have saved with the new path
				alreadyOpenedNode.rectify(node);
				metrics.incrementInt(METRIC_NODES_RECTIFIED);

				// The node's children (an their children, etc) must recalculate their pathCost
				// The frontier must be reordered. This is done by removing and reinserting any node that has been modified.
				recalculateChildrensPathCost(alreadyOpenedNode);
			}
		}
		
		else {
			// If the node hasn't been opened before it's put in the opened map and inserted in the frontier
			
			RectifiableNode rNode = RectifiableNode.cloneNode(node);
			frontier.insert(rNode);
			updateMetrics(frontier.size());
			explored.put(rNode.getState(), rNode);
			
			RectifiableNode parent = ((RectifiableNode) rNode.getParent());
			
			if(parent != null)
				parent.addChild(rNode);
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

	private void recalculateChildrensPathCost(RectifiableNode parent) {
		
		Iterator<RectifiableNode> children = parent.getChildrenIterator();
		
		// For each child of the parent...
		while (children.hasNext()) {
			RectifiableNode child = children.next();
			
			// Recalculate its pathCost [g(n)]
			child.recalculatePathCost();
			// Recalculate the pathCost of its children
			recalculateChildrensPathCost(child);
		}
		
		if(frontier.contains(parent)) {
			// This way we force the frontier to sort itself
			frontier.remove(parent);
			frontier.insert(parent);
		}
	}
	
	@Override
	public void clearInstrumentation() {
		super.clearInstrumentation();
		metrics.set(METRIC_NODES_RECTIFIED, 0);
	}
	
}
