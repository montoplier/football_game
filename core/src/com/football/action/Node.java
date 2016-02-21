package com.football.action;

public class Node {

	public int x;
	public int y;
	public int offsetX;
	public int offsetY;
	//previous node in the path
	Node prevNode;
	Float fCost = null;
	Float gCost = null;
	Float hCost = null;
	
	/**
	 * This class basically just holds an x and y value
	 * to be used to define individual points along a Route
	 * Probably could have done this with Point rather than
	 * making a whole new class for this
	 * @param x
	 * @param y
	 */
	public Node(int x, int y, int offsetX, int offsetY) {
		this.x = x;
		this.y = y;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getOffsetX() {
		return this.offsetX;
	}
	
	public int getOffsetY() {
		return this.offsetY;
	}

	public Node getPrevNode() {
		return this.prevNode;
	}

	public Float getFCost() {
		return this.fCost;
	}

	public Float getGCost() {
		return this.gCost;
	}

	public Float getHCost() {
		return this.hCost;
	}
	
	public void setFCost(Float fCost) {
		this.fCost = fCost;
	}
	
	public void setGCost(Float gCost) {
		this.gCost = gCost;
	}
	
	public void setHCost(Float hCost) {
		this.hCost = hCost;
	}
	
	public void setPrevNode(Node prevNode) {
		this.prevNode = prevNode;
	}
	
}
