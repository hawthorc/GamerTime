/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
	private Color color;
	private final double diagFactor = 1 / Math.sqrt(2);	// for vector normalization

	
	public Player(GamePanel gp, KeyHandler keyH, Color color) {
		this.gp = gp;
		this.keyH = keyH;
		this.color = color;
		setDefaultValues();
	}
	
	
	public void setDefaultValues() {
		x = 100;
		y = 100;
		speed = 4;
	}
	
	
	// update the player position
	public void update() {
		updateMovement(keyH.up, keyH.down, keyH.left, keyH.right);
	}
	
	
	// update the remote player position based on input data
	public void handleRemoteInput(boolean up, boolean down, boolean left, boolean right) {
	    updateMovement(up, down, left, right);
	}
	
	
	// apply the input information to move the players
	private void updateMovement(boolean up, boolean down, boolean left, boolean right) {
		int xVec = 0, yVec = 0;
		
		// 0,0 is in the top left corner, so up / down are swapped
		if (up == true)	yVec = -1;
		if (down == true) yVec = 1;
		if (left == true) xVec = -1;
		if (right == true) xVec = 1;
		
		// diagonal movement (avoid moving faster due to Pythagoras)
		if(xVec != 0 && yVec != 0) {
			x += xVec * speed * diagFactor;
			y += yVec * speed * diagFactor;
		}
		// non-diagonal
		else if(xVec != 0) x += xVec * speed;
		else if(yVec != 0) y += yVec * speed;
	}
	

	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}
}
