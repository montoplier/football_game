package com.football.player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import com.football.action.Node;

/**
 * A* path finder
 */
public class AStar {

	//2D array or x/y position of nodes - should be height of map
	public Node nodes[][];
	ArrayList<Player> players;
	int playerWidth;
	int playerHeight;
	int nodeWidth;
	int nodeHeight;
	
	/**
	 * Should only need to declare this once in the main class to initialize these params
	 * @param nodes - basically every tile that makes up the field
	 * @param nodeWidth
	 * @param nodeHeight
	 */
	public AStar(ArrayList<Player> players, int[][] nodes, int nodeWidth, int nodeHeight, int playerWidth, int playerHeight) {
		this.players = players;
		this.nodeWidth = nodeWidth;
		this.nodeHeight = nodeHeight;
		this.nodes = new Node[nodes.length][nodes[0].length];
		this.playerWidth = playerWidth;
		this.playerHeight = playerHeight;
		initializeNodes();
	}
	
	/**
	 * build out node array and clear any previous values
	 */
	public void initializeNodes() {
		//build out nodes array
		for(int x = 0; x < nodes.length; x++) {
			for(int y = 0; y < nodes[0].length; y++) {
				this.nodes[x][y] = new Node(x, y, (x*nodeWidth), (y*nodeHeight));
			}
		}
	}
	
	/**
	 * Main logic to find the actual path
	 * @param startingX - in terms of nodes
	 * @param startingY - in terms of nodes
	 * @param endingX - in terms of nodes
	 * @param endingY - in terms of nodes
	 * @return
	 */
	public ArrayList<Node> findPath(int startingX, int startingY, int endingX, int endingY) {
		LinkedList<Node> openList = new LinkedList<Node>();
		LinkedList<Node> closedList = new LinkedList<Node>();
		initializeNodes();
		Node startingNode = nodes[startingX][startingY];
		Node endingNode = nodes[endingX][endingY];
		openList.add(startingNode);
		startingNode.setGCost(0f);
		boolean done = false;
		Node curNode = startingNode;
		while(!done) {
			curNode = lowestFCostOpenNode(openList);
			closedList.add(curNode);
			openList.remove(curNode);
			
			//check to see if the current node is the ending node
			if ((curNode.getX() == endingNode.getX()) && (curNode.getY() == endingNode.getY())) {
                return getPath(startingNode, curNode);
            }
			
			//find all adjacent nodes and evaluate
			LinkedList<Node> adjacentNodes = getAdjacentNodes(curNode);
			for(Node curAdj : adjacentNodes) {
				if (!openList.contains(curAdj) && !closedList.contains(curAdj)) {
					curAdj.setPrevNode(curNode);
					//set h costs of this node (estimated costs to goal)
					curAdj.setHCost(calculateHCost(curAdj, endingNode));
					//set g costs of this node (costs from start to this node), just add one to the cost of the current node
					curAdj.setGCost(curNode.getGCost() + 1);
					curAdj.setFCost(curAdj.getHCost() + curAdj.getGCost());
                    openList.add(curAdj);
                } else {
                	//costs from current node are cheaper than previous costs
                    if (curAdj.getGCost() > curNode.getGCost() + 1) {
                    	closedList.remove(curAdj);
                    	//set current node as previous for this node
                    	curAdj.setPrevNode(curNode);
                    	//set g costs of this node (costs from start to this node)
    					curAdj.setHCost(calculateHCost(curAdj, endingNode));
                    	curAdj.setGCost(curNode.getGCost() + 1); 
    					curAdj.setFCost(curAdj.getHCost() + curAdj.getGCost());
                    	if(!openList.contains(curAdj)) openList.add(curAdj);
                    }
                }
			}
			/*
			if(curNode.getPrevNode() != null && curNode.getPrevNode().getPrevNode() != null && curNode.getPrevNode().getPrevNode().equals(curNode)) {
				System.out.println("curNodeX: " + curNode.getX() + ", curNodeY: " + curNode.getY());
				System.out.println("prevNodeX: " + curNode.getPrevNode().getX() + ", prevNodeY: " + curNode.getPrevNode().getY());
				System.out.println("");
			}
			*/
			//no path exists, return empty list
			if(openList.isEmpty()) {
				return new ArrayList<Node>();
			}
		}
		//this isn't actually reachable
		return null;
	}
	
	public Node lowestFCostOpenNode(LinkedList<Node> openList) {
		Node returnNode = null;
		for(Node curNode : openList) {
			if(returnNode == null || (curNode.getFCost() != null && returnNode.getFCost() != null && curNode.getFCost() < returnNode.getFCost())) {
				returnNode = curNode;
			}
		}
		return returnNode;
	}
	
	/**
	 * Get all of the possible adjacent (not intersecting player) nodes given a node
	 * @return - list of adjacent nodes
	 */
	public LinkedList<Node> getAdjacentNodes(Node currentNode) {
		LinkedList<Node> returnList = new LinkedList<Node>();
		//north
		if(currentNode.y + 1 < nodes[0].length) {
			if(!interSectingPlayer(nodes[currentNode.x][currentNode.y+1])) {
				returnList.add(nodes[currentNode.x][currentNode.y+1]);
			}
		}
		//south
		if(currentNode.y-1 >= 0) {
			if(!interSectingPlayer(nodes[currentNode.x][currentNode.y-1])) {
				returnList.add(nodes[currentNode.x][currentNode.y-1]);
			}
		}
		//east
		if(currentNode.x + 1 < nodes.length) {
			if(!interSectingPlayer(nodes[currentNode.x+1][currentNode.y])) {
				returnList.add(nodes[currentNode.x+1][currentNode.y]);
			}
		}
		//west
		if(currentNode.x-1 >= 0) {
			if(!interSectingPlayer(nodes[currentNode.x-1][currentNode.y])) {
				returnList.add(nodes[currentNode.x-1][currentNode.y]);
			}
		}
		return returnList;
	}
	
	/**
	 * Calcualate H cost from a given node to the ending node
	 * H cost is basically the cost of from here to the goal
	 * @param curNode
	 * @param endingNode
	 * @return
	 */
	public float calculateHCost(Node curNode, Node endingNode) {
		int dx = endingNode.getX() - curNode.getX();
	    int dy = endingNode.getY() - curNode.getY();
	    return (float) Math.sqrt((dx*dx)+(dy*dy));
	    //less costly and simpler method
	    /* int dx = abs(targetX - currentX);
		   int dy = abs(targetY - currentY);
		   return (float) dx+dy;
	     */
	}
	
	/**
	 * Method to see if another player is currently intersecting
	 * a given node, so that the current player knows not to go there
	 * @param curNode - any possible node on the path
	 * @return - true if there is an intersection, else false
	 */
	public boolean interSectingPlayer(Node curNode) {
		int nodeLeft = curNode.x;
		int nodeRight = curNode.x + this.nodeWidth;
		int nodeBottom = curNode.y;
		int nodeTop = curNode.y + this.nodeHeight;
		//loop through all players and see if any intersect this node
		for(Player player : this.players) {
			boolean xIntersect = false;
			boolean yIntersect = false;
			if(player.getOffsetX() > nodeLeft && player.getOffsetX() + this.playerWidth < nodeRight) {
				xIntersect = true;
			}
			if(player.getOffsetY() > nodeBottom && player.getOffsetY() + this.playerHeight < nodeTop) {
				yIntersect = true;
			}
			//intersecting, return true
			if(xIntersect && yIntersect) {
				return true;
			}
		}
		//no intersection found, return false
		return false;
	}
	
	/**
	 * Build out arraylist with the resulting path
	 * @return - ordered list of nodes in the path
	 */
	public ArrayList<Node> getPath(Node startingNode, Node endingNode) {
		ArrayList<Node> returnPath = new ArrayList<Node>();
		Node curNode = endingNode;
		//work back through previous nodes and add them to the path
		while(curNode.getPrevNode() != null) {
			returnPath.add(curNode);
			curNode = curNode.getPrevNode();
		}
		//reverse order of path
		Collections.reverse(returnPath);
		return returnPath;
	}
	

	
	/**
	 * Get the node value for the player based off of their offset
	 * @param offsetX - position relative to the field
	 * @return - node X value
	 */
	public int getNodeFromOffsetX(float offsetX) {
		return (int) Math.floor(offsetX/this.nodeWidth);
	}
	
	/**
	 * Get the node value for the player based off of their offset
	 * @param offsetY - position relative to the field
	 * @return - node Y value
	 */
	public int getNodeFromOffsetY(float offsetY) {
		return (int) Math.floor(offsetY/this.nodeHeight);
	}
}
