package com.football.action;

import java.util.ArrayList;

public class Route extends Action{

	ArrayList<Node> path = new ArrayList<Node>();
	
	/**
	 * Action class for running routes or just setting a path
	 * for a player, path consists of individual nodes, or points
	 * that are in the order that they need to be reached
	 * this should probably be constructed with the path, but
	 * I don't want to limit it at this point
	 */
	public Route() {
		
	}
	
	public ArrayList<Node> getPath() {
		return this.path;
	}
	
	public void setPath(ArrayList<Node> path) {
		this.path = path;
	}
}
