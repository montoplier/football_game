package com.football.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

/**
 * user input, accessed from either Main.java
 * this basically just moves the camera right now
 * will need to be used for game input, probably will just have camera follow the ball eventually
 */
public class Input implements InputProcessor{
	
	//specific key-value variables to listen for elsewhere
	public boolean leftHeld = false;
	public boolean rightHeld = false;
	public boolean upHeld = false;
	public boolean downHeld = false;

	//initialize
	public Input() {
        Gdx.input.setInputProcessor(this);
	}

	//actual key down listeners
	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.LEFT) {
			leftHeld = true;
		} else if(keycode == Keys.RIGHT) {
			rightHeld = true;
		} else if(keycode == Keys.UP) {
			upHeld = true;
		} else if(keycode == Keys.DOWN) {
			downHeld = true;
		}
		return false;
	}

	//actual key up listeners
	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.LEFT) {
			leftHeld = false;
		} else if(keycode == Keys.RIGHT) {
			rightHeld = false;
		} else if(keycode == Keys.UP) {
			upHeld = false;
		} else if(keycode == Keys.DOWN) {
			downHeld = false;
		}
		return false;
	}

	//unused, possibly can be used?
	@Override
	public boolean keyTyped(char character) {
		
		return false;
	}
	
	//not using any of these below
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
