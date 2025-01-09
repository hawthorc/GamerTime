/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	private final double diagFactor = 1 / Math.sqrt(2);	// for vector normalization

	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		setDefaultValues();
	}
	
	
	public void setDefaultValues() {
		
		x = 100;
		y = 100;
		speed = 4;
	}
	
	
	public void update() {
	
		int xVec = 0, yVec = 0;

		// 0,0 is in the top left corner, so up / down are swapped
		if(keyH.up == true)	yVec = -1;
		if(keyH.down == true) yVec = 1;
		if(keyH.left == true) xVec = -1;
		if(keyH.right == true) xVec = 1;
		
		// diagonal movement (avoid moving faster due to Pythagoras)
		if(xVec != 0 && yVec != 0) {
			x += xVec * speed * diagFactor;
			y += yVec * speed * diagFactor;
		}
		// non-diagonal
		else if(xVec != 0) x += xVec * speed;
		else if(yVec != 0) y += yVec * speed;
		
		/*if (keyH.up == true) {
			y -= speed;
		} else if (keyH.down == true) {
			y += speed;
		} else if (keyH.left == true) {
			x -= speed;
		} else if (keyH.right == true) {
			x += speed;
		}*/
	}
	
	
	public void draw(Graphics2D g2) {
		 
		g2.setColor(Color.white);
		g2.fillRect(x, y, gp.tileSize, gp.tileSize);
	}
}
