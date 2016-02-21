package com.football.action;

import java.util.ArrayList;

public class Path {
	
	ArrayList<Node> nodes;
	Node finalDestination;
	
	public Path(Node finalDestination) {
		this.nodes = new ArrayList<Node>();
		this.finalDestination = finalDestination;
		this.nodes.add(finalDestination);
	}
	
	public Path(ArrayList<Node> nodes) {
		this.nodes = nodes;
		if(this.nodes.size() > 0) {
			this.finalDestination = this.nodes.get(this.nodes.size() - 1);
		}
	}
	
	public void setPath(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<Node> getNodes() {
		return this.nodes;
	}
	
	public void setDestination(Node finalDestination) {
		this.finalDestination = finalDestination;
	}
	
	public Node getFinalDestination(){
		return this.finalDestination;
	}
	
	public Node getCurDestination() {
		if(this.nodes.size() > 0) {
			return this.nodes.get(0);
		} else {
			return null;
		}
	}
	
}
