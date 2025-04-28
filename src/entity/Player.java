/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	private Color color;
	private final double diagFactor = 1 / Math.sqrt(2);	// for vector normalization

	
	public Player(GamePanel gp, KeyHandler keyH, Color color) {
		this.gp = gp;
		this.keyH = keyH;
		screenX = gp.screenWidth/2 - (gp.tileSize/2);				// player position is based on top left corner, so subtract half a tile
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
		this.color = color;
		setDefaultValues();
		// getPlayerImage;
	}
	
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 25;				// start in the middle of the map
		worldY = gp.tileSize * 25;
		speed = 4;
		direction = "down";
	}
	
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/up2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/down1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/down2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/left1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/left2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/right1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/right2.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
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
		
		// only update animation if the player is moving
		if (up == true || down == true || left == true || right == true) {
			int xVec = 0, yVec = 0;
			
			// 0,0 is in the top left corner, so up / down are swapped
			if (up == true)	{
				yVec = -1;
				direction = "up";					// change these to use the first direction inputted? i.e. if player is going up and right, the "up" sprite will be used
			}										// but if player goes right and up, "right" sprite will be used
			if (down == true) {
				yVec = 1;
				direction = "down";
			}
			if (left == true) {
				xVec = -1;
				direction = "left";
			}
			if (right == true) {
				xVec = 1;
				direction = "right";		}
			
			// diagonal movement (avoid moving faster due to Pythagoras)
			if(xVec != 0 && yVec != 0) {
				worldX += xVec * speed * diagFactor;
				worldY += yVec * speed * diagFactor;
			}
			// non-diagonal
			else if(xVec != 0) worldX += xVec * speed;
			else if(yVec != 0) worldY += yVec * speed;
			
			spriteCounter++;
			if(spriteCounter > 12) {
				spriteNum = spriteNum == 1 ? 2 : 1;
				spriteCounter = 0;
			}
		}
	}
	

	public void draw(Graphics2D g2) {
		g2.setColor(color);
		g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			if (spriteNum == 1) {
				image = up1;
			} else image = up2;
			break;
		case "down":
			if (spriteNum == 1) {
				image = down1;
			} else image = down2;
			break;
		case "left":
			if (spriteNum == 1) {
				image = left1;
			} else image = left2;
			break;
		case "right":
			if (spriteNum == 1) {
				image = right1;
			} else image = right2;
			break;
		}
		// TODO: add player sprites and use this line!!!
		//g2.drawImage(image, screenX, screemY, gp.tileSize, gp.tileSize, null);
	}
}
