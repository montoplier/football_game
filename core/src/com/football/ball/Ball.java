package com.football.ball;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.football.player.Side;

public class Ball {//offsetX and Y are the player's position relative to the field, rather than the actual draw position
	int offsetX;
	int offsetY;
	Side side;
	
	//image of the unit
	Texture curTexture;
	
	public Ball(int offsetX, int offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
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
