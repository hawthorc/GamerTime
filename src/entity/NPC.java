/* Sample NPC class for entities that are controlled by the game, not the players
 * these could be friendly characters, enemies, possibly even objects?
 * 
 * Some of this logic is similar / equal to that of the Player class, possibly could move functions to Entity
 */

package entity;

import java.awt.Color;
import java.awt.Graphics2D;

import main.GamePanel;

public class NPC extends Entity{
	
	GamePanel gp;
	
	private Color color;
	private boolean left;
	
	
	public NPC(GamePanel gp, Color color) {
		this.gp = gp;
		this.color = color;
		setDefaultValues();
	}
	

	public void setDefaultValues() {
		worldX = 200;
		worldY = 200;
		speed = 2;  // unnecessary?
		left = false;
	}
	
	
	// NPC movement test method, causes the character to move back and forth on the screen
	public void updatePositionTest() {
		if (left) {
			worldX -= 2;
			if (worldX <= 20) left = false;
		}
		else {
			worldX += 2;
			if (worldX >= 400) left = true;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fillOval(worldX, worldY, gp.tileSize, gp.tileSize);
	}
}
