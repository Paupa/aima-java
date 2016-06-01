package aima.core.search.framework;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import aima.core.agent.Action;

public class RectifiableNode extends Node {
	
	private double stepCost;
	
	private Set<RectifiableNode> children = new HashSet<>();

	public RectifiableNode(Object state) {
		super(state);
	}
	
	public RectifiableNode(Object state, Node parent, Action action, double stepCost) {
		super(state, parent, action, stepCost);
		this.stepCost = stepCost;
	}
	
	public void rectify(Node node) {
		
		if(!node.getState().equals(getState()))
			throw new IllegalArgumentException("Cannot rectify the node "
					+ "with the data of a node with another state.");
		
		((RectifiableNode) parent).removeChild(this);
		
		this.parent = node.getParent();
		((RectifiableNode) parent).addChild(this);
		
		this.action = node.getAction();
		this.pathCost = node.getPathCost();
		stepCost = pathCost - parent.getPathCost();
	}
	
	public void recalculatePathCost() {
		pathCost = parent.getPathCost() + stepCost;
	}
	
	public void addChild(RectifiableNode child) {
		children.add(child);
	}
	
	public void removeChild(RectifiableNode child) {
		children.remove(child);
	}
	
	public Iterator<RectifiableNode> getChildrenIterator() {
		return children.iterator();
	}
	
	public static RectifiableNode cloneNode(Node node) {
		double stepCost = 0;
		
		if(node.getParent() != null)
			stepCost = node.getPathCost() - node.getParent().getPathCost();
		
		return new RectifiableNode(node.getState(), node.getParent(), node.getAction(), stepCost);
	}

}
