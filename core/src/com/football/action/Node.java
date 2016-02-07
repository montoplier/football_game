package com.football.action;

public class Node {

	int x;
	int y;
	
	/**
	 * This class basically just holds an x and y value
	 * to be used to define individual points along a Route
	 * Probably could have done this with Point rather than
	 * making a whole new class for this
	 * @param x
	 * @param y
	 */
	public Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
