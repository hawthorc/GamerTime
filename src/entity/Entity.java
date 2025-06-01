/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Entity {
	
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	// direction for drawing, different from input direction
	// directional inputs include diagonal movement, which means 2 can be applied at the same time
	// but only one directional sprite can be displayed
	public String direction;
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	// create a rectangle within the sprite tile for collisions
	public Rectangle solidArea;
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean colliding = false;
}
