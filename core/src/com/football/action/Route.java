package com.football.action;

import java.util.ArrayList;

public class Route extends Action{

	ArrayList<Path> paths = new ArrayList<Path>();
	
	/**
	 * Action class for running routes or just setting a path
	 * for a player, path consists of individual nodes, or points
	 * that are in the order that they need to be reached
	 * this should probably be constructed with the path, but
	 * I don't want to limit it at this point
	 */
	public Route(ArrayList<Path> paths) {
		this.paths = paths;
	}
	
	public ArrayList<Path> getRoute() {
		return this.paths;
	}
	
	public void setRoute(ArrayList<Path> paths) {
		this.paths = paths;
	}
	
	public void setCurPath(Path path) {
		this.paths.set(0, path);
	}
	
	public Path getCurPath() {
		if(this.paths.size() > 0) {
			return this.paths.get(0);
		} else {
			return null;
		}
	}
	
	public Node getCurDestination() {
		if(this.paths.size() > 0) {
			return this.paths.get(0).getCurDestination();
		} else {
			return null;
		}
	}
}
