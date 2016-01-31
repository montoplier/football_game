package com.football.game;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.football.player.Halfback;
import com.football.player.Player;
import com.football.player.Quarterback;
import com.football.player.Side;

/**
 * Class to read in a play by name, parse out the corresponding XML file
 * and return an arraylist of players
 */
public class PlayBuilder {
    XmlReader formationReader;
    Texture offenseTexture = new Texture(Gdx.files.internal("images/player_o.png"));
    Texture defenseTexture = new Texture(Gdx.files.internal("images/player_x.png"));
    int playerWidth = offenseTexture.getWidth();
    int playerHeight = offenseTexture.getHeight();
    
	public PlayBuilder() {
		
	}
	
	/**
	 * Actual function to take in file name and current (y) field position 
	 * and parse out the XML to return an arraylist of players with 
	 * location attributes and potentially play-related attributes
	 * @return arraylist of players in this play
	 */
	public ArrayList<Player> loadPlay(String play, Point fieldPos) {
		//initialize return arraylist
		ArrayList<Player> retPlayers = new ArrayList<Player>();
		
		//load file based on input string
		if(!play.substring(play.length() - 4).equals(".xml")) play += ".xml";
		FileHandle xmlInputFile = Gdx.files.internal("files/" + play);
		XmlReader reader = new XmlReader();
		Element root = null;
		try {
			root = reader.parse(xmlInputFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Side curSide = getSide(root);
		Texture curTexture = getTexture(curSide);
		//loop through players in XML
		Array<Element> xmlPlayers = root.getChildrenByName("player");
		for (Element xmlPlayer : xmlPlayers) {
			Player curPlayer = buildPlayerFromXML(xmlPlayer, fieldPos, curSide, curTexture);
			retPlayers.add(curPlayer);
		}
		return retPlayers;
	}
	
	/**
	 * Get the current side enum from the XML doc
	 * @return Side - offense or defense, defaulting to offense
	 */
	public Side getSide(XmlReader.Element root) {
		//read side out of XML
		//default to offense
		Side curSide = Side.OFFENSE;
		String curSideText = root.getChildrenByName("side").get(0).getText();
		if(curSideText.equalsIgnoreCase("offense")) {
			curSide = Side.OFFENSE;
		} else if(curSideText.equalsIgnoreCase("defense")) {
			curSide = Side.DEFENSE;
		}
		return curSide;
	}
	
	/**
	 * This may not really need its own function
	 * @return texture based on current side
	 */
	public Texture getTexture(Side curSide) {
		Texture curTexture = null;
		if(curSide == Side.OFFENSE) {
			curTexture = this.offenseTexture;
		} else if(curSide == Side.DEFENSE) {
			curTexture = this.defenseTexture;
		}
		return curTexture;
	}
	
	/**
	 * Takes in player data from XML element and
	 * creates a corresponding player object
	 * @return player object
	 */
	public Player buildPlayerFromXML(Element xmlPlayer, Point fieldPos, Side curSide, Texture curTexture) {
		Player retPlayer;
		//making sure these numbers are only numbers and no characters
		int xPos = Integer.parseInt(xmlPlayer.get("x").replaceAll("[^-0-9]",""));
		int yPos = Integer.parseInt(xmlPlayer.get("y").replaceAll("[^-0-9]",""));
		//adjust values by player size
		xPos = getOffsetXPos(xPos, fieldPos.x);
		yPos = getOffsetYPos(yPos, fieldPos.y);
		String playerType = null;
		try {
			playerType = xmlPlayer.get("type");
		} catch(Exception e) {
			System.out.println("no player type found for player");
		}
		if(playerType != null && playerType.equalsIgnoreCase("quarterback")) {
			retPlayer = new Quarterback(xPos, yPos, curSide, curTexture);
		} else if(playerType != null && playerType.equalsIgnoreCase("halfback")) {
			retPlayer = new Halfback(xPos, yPos, curSide, curTexture);
		} else {
			retPlayer = new Player(xPos, yPos, curSide, curTexture);
		}
		return retPlayer;
	}
	
	/**
	 * XML positions should be in terms of players,
	 * so the center should be 0, a player directly to the left should be -1, etc
	 * this function is to get the actual field position based on the XML position
	 * @param fieldPos - x value of where the ball is on the field
	 * and the players' widths
	 * @param origXPos - XML position
	 * @return - actual field position
	 */
	public int getOffsetXPos(int origXPos, int fieldPos) {
		return (origXPos * this.playerWidth) + fieldPos;
	}
	
	/**
	 * Similar to getOffsetXPos, but with the field pos (y) value
	 * @param fieldPos - y value of where the ball is on the field
	 * @param origYPos - XML position
	 * @return - actual field position
	 */
	public int getOffsetYPos(int origYPos, int fieldPos) {
		return (origYPos * this.playerHeight) + fieldPos;
	}
	
}
