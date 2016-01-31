package com.football.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Player {

	//offsetX and Y are the player's position relative to the field, rather than the actual draw position
	int offsetX;
	int offsetY;
	Side side;
	
	//image of the unit
	Texture curTexture;
	
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
