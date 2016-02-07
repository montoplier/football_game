package com.football.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.football.action.Action;
import com.football.action.Node;
import com.football.action.Route;

public class Player {

	//offsetX and Y are the player's position relative to the field, rather than the actual draw position
	int offsetX;
	int offsetY;
	//change in x/y
	int dx;
	int dy;
	Side side;
	
	//image of the unit
	Texture curTexture;
	
	Action curAction;
	//current route the player is on
	Route curRoute;
	
	/**
	 * Base player objest
	 * @param offsetX - player's X position in relation to the field
	 * @param offsetY - player's Y position in relation to the field
	 * @param side - enum of offense or defense
	 * @param texture - player image Texture object
	 */
	public Player(int offsetX, int offsetY, Side side, Texture texture) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.side = side;
		this.curTexture = texture;
		if(this.side == Side.OFFENSE) {
			this.curTexture = new Texture(Gdx.files.internal("images/player_o.png"));
		} else if(this.side == Side.DEFENSE) {
			this.curTexture = new Texture(Gdx.files.internal("images/player_x.png"));
		}
	}
	
	/**
	 * Main loop functionality for the player
	 */
	public void update() {
		if(this.curRoute != null && this.curRoute.getPath().size() > 0) {
			updatePlayerRoute();
		}
		
		this.offsetX += dx;
		this.offsetY += dy;
	}
	
	/**
	 * Handle pathing/route logic, called from update
	 */
	public void updatePlayerRoute() {
		boolean xDestinationMet = false;
		boolean yDestinationMet = false;
		Node curDestination = curRoute.getPath().get(0);
		//evaluate current destination
		if(curDestination.getX() > this.offsetX) {
			this.dx = 1;
		} else if(curDestination.getX() < this.offsetX) {
			this.dx = -1;
		} else if(curDestination.getX() == this.offsetX) {
			xDestinationMet = true;
			this.dx = 0;
		}
		if(curDestination.getY() > this.offsetY) {
			this.dy = 1;
		} else if(curDestination.getY() < this.offsetY) {
			this.dy = -1;
		} else if(curDestination.getY() == this.offsetY) {
			yDestinationMet = true;
			this.dy = 0;
		}
		//if both destinations have been met, set the next destination from the path
		if(xDestinationMet && yDestinationMet) {
			curRoute.getPath().remove(curDestination);
		}
	}
	
	public void setAction(Action curAction) {
		this.curAction = curAction;
	}
	
	public void setRoute(Route curRoute) {
		this.curRoute = curRoute;
	}
	
	public int getOffsetX() {
		return this.offsetX;
	}
	
	public int getOffsetY() {
		return this.offsetY;
	}
	
	public Texture getCurTexture() {
		return this.curTexture;
	}
	
}
