package com.football.game;

import java.awt.Point;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.football.player.AStar;
import com.football.player.Player;
import com.football.action.Node;
import com.football.action.Path;
import com.football.action.Route;
import com.football.input.Input;

public class Main extends ApplicationAdapter {
	SpriteBatch batch;
	
    OrthographicCamera camera;
    int cameraScrollSpeed = 5;
    
    //utility stuff
    Input input;
    PlayBuilder playBuilder;
    AStar pathFinder;
    
    //game objects
    ArrayList<Player> players = new ArrayList<Player>();
	Texture fieldTexture;
	int fieldWidth;
	int fieldHeight;
	int nodeWidth;
	int nodeHeight;
	int fieldPosX;
	int fieldPosY;
	int playerWidth = 50;
	int playerHeight = 50;
	
	@Override
	public void create () {
		input = new Input();
		playBuilder = new PlayBuilder();
		initializeField();
		initializeCamera();
		initializePlayers();
		initializePathFinder();
		batch = new SpriteBatch();
	}

	@Override
	public void render () {
		gameLoop();
		
		Gdx.gl.glClearColor(135/255f, 206/255f, 235/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		batch.draw(fieldTexture, getDrawPosX(fieldPosX), getDrawPosY(fieldPosY));
		for(Player player : players) {
			batch.draw(player.getCurTexture(), getDrawPosX(player.getOffsetX()), getDrawPosY(player.getOffsetY()));
		}
		
		batch.end();
	}
	
	/**
	 * Main logic used to call all game logic methods
	 */
	public void gameLoop() {
		setCameraPosition();
		for(Player curPlayer : this.players){
			//update path
			//find player's current node position
			int startingNodeX = pathFinder.getNodeFromOffsetX(curPlayer.getOffsetX());
			int startingNodeY = pathFinder.getNodeFromOffsetY(curPlayer.getOffsetY());
			if(curPlayer.getCurRoute() != null && curPlayer.getCurRoute().getCurDestination() != null) {
				int endingNodeX = curPlayer.getCurRoute().getCurDestination().getX();
				int endingNodeY = curPlayer.getCurRoute().getCurDestination().getY();
				Path curPath = new Path(this.pathFinder.findPath(startingNodeX, startingNodeY, endingNodeX, endingNodeY));
				if(!(startingNodeX == endingNodeX && startingNodeY == endingNodeY)) {
					curPlayer.getCurRoute().setCurPath(curPath);
				}
			}
			curPlayer.update();
		}
	}
	
	/**
	 * This just looks at user input and moves the camera accordingly right now
	 * will probably follow the ball, or current player eventually
	 */
	public void setCameraPosition() {
		if(input.leftHeld && !input.rightHeld) {
			camera.position.x -= cameraScrollSpeed;
		} else if(input.rightHeld && !input.leftHeld) {
			camera.position.x += cameraScrollSpeed;
		}
		if(input.upHeld && !input.downHeld) {
			camera.position.y += cameraScrollSpeed;
		} else if(input.downHeld && !input.upHeld) {
			camera.position.y -= cameraScrollSpeed;
		}
	}
	
	/**
	 * Get draw position based off offset X - meaning x position within the actual map
	 * draw position = offset minus camera position (middle of screen for some terrible reason) minus width of screen / 2
	 * @param offsetX
	 * @return draw position
	 */
	public float getDrawPosX(float offsetX) {
		float returnVal;
		returnVal = offsetX - (this.camera.position.x - (Gdx.graphics.getWidth()/2));
		return returnVal;
	}
	
	/**
	 * Get draw position based off offset Y - meaning y position within the actual map
	 * draw position = offset minus camera position (middle of screen for some terrible reason) minus height of screen / 2
	 * @param offsetY
	 * @return draw position
	 */
	public float getDrawPosY(float offsetY) {
		float returnVal;
		returnVal = offsetY - (this.camera.position.y - (Gdx.graphics.getHeight()/2));
		return returnVal;
	}
	
	//set up field image and get sizes
	public void initializeField() {
		this.fieldTexture = new Texture(Gdx.files.internal("images/field_75.png"));
		this.fieldWidth = fieldTexture.getWidth();
		this.fieldHeight = fieldTexture.getHeight();
		//I think these will always be zero
		this.fieldPosX = 0;
		this.fieldPosY = 0;
	}
	
	/**
	 * Initialize camera
	 */
	public void initializeCamera() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.position.set(575, 250, 0);
        camera.update();
	}
	
	/**
	 * Initializes the pathFinder class - AStar
	 */
	public void initializePathFinder() {
		int fieldWidthInNodes = (int) Math.ceil(this.fieldWidth/this.playerWidth);
		int fieldHeightInNodes = (int) Math.ceil(this.fieldHeight/this.playerHeight);
		this.nodeWidth = this.playerWidth;
		this.nodeHeight = this.playerHeight;
		int[][] nodes = new int[fieldWidthInNodes][fieldHeightInNodes];
		for(int x = 0; x < nodes.length; x++) {
			for(int y = 0; y < nodes[0].length; y++) {
				
			}
		}
		//node width and node height are the same as player width and player height here
		this.pathFinder = new AStar(this.players, nodes, this.nodeWidth, this.nodeHeight, this.playerWidth, this.playerHeight);
	}
	
	/**
	 * Initialize players
	 * This is just hardcoded for now
	 */
	public void initializePlayers() {
		ArrayList<Player> curPlayers = playBuilder.loadPlay("test_formation", new Point(fieldWidth/2, 250));
		this.players = curPlayers;
		this.playerWidth = this.players.get(0).getCurTexture().getWidth();
		this.playerHeight = this.players.get(0).getCurTexture().getHeight();
	}
}
