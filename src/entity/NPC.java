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
		x = 200;
		y = 200;
		speed = 2;  // unnecessary?
		left = false;
	}
	
	
	public void updatePosition() {
		if (left) {
			x -= 2;
			if (x <= 20) left = false;
		}
		else {
			x += 2;
			if (x >= 400) left = true;
		}
	}

	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fillOval(x, y, gp.tileSize, gp.tileSize);
	}
}
