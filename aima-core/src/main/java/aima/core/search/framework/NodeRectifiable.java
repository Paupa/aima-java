package aima.core.search.framework;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import aima.core.agent.Action;

/**
 * @author Paula Díaz Puertas
 */
public class NodeRectifiable extends Node {
	
	private double stepCost;
	
	private Set<NodeRectifiable> children = new HashSet<>();

	public NodeRectifiable(Object state) {
		super(state);
	}
	
	public NodeRectifiable(Object state, Node parent, Action action, double stepCost) {
		super(state, parent, action, stepCost);
		this.stepCost = stepCost;
	}
	
	/**
	 * Rectifies this node with the data of the node passed, meaning it changes
	 * its parent, action, pathCost and stepCost. It does not recalculate the
	 * path cost of its children.
	 * 
	 * @param node The node with the data we want to use to rectify this one.
	 * 		Its state must be equal to this one.
	 */
	public void rectify(Node node) {
		
		if(!node.getState().equals(getState()))
			throw new IllegalArgumentException("Cannot rectify the node "
					+ "with the data of a node with another state.");
		
		((NodeRectifiable) parent).removeChild(this);
		
		this.parent = node.getParent();
		((NodeRectifiable) parent).addChild(this);
		
		this.action = node.getAction();
		this.pathCost = node.getPathCost();
		stepCost = pathCost - parent.getPathCost();
	}
	
	public void recalculatePathCost() {
		pathCost = parent.getPathCost() + stepCost;
	}
	
	public void addChild(NodeRectifiable child) {
		children.add(child);
	}
	
	public void removeChild(NodeRectifiable child) {
		children.remove(child);
	}
	
	public Iterator<NodeRectifiable> getChildrenIterator() {
		return children.iterator();
	}
	
	public static NodeRectifiable cloneNode(Node node) {
		double stepCost = 0;
		
		if(node.getParent() != null)
			stepCost = node.getPathCost() - node.getParent().getPathCost();
		
		return new NodeRectifiable(node.getState(), node.getParent(), node.getAction(), stepCost);
	}

}
