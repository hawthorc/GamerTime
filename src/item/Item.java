/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package item;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;

public class Item {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.localPlayer.worldX + gp.localPlayer.screenX;
		int screenY = worldY - gp.localPlayer.worldY + gp.localPlayer.screenY;
		
		
		// only draw items we can see
		if (worldX + gp.tileSize > gp.localPlayer.worldX - gp.localPlayer.screenX &&
			worldX - gp.tileSize < gp.localPlayer.worldX + gp.localPlayer.screenX &&
			worldY + gp.tileSize > gp.localPlayer.worldY - gp.localPlayer.screenY &&
			worldY - gp.tileSize < gp.localPlayer.worldY + gp.localPlayer.screenY) {
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
	}

}
