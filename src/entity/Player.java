/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
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
	// determine if we are dealing with the local or remote player
	public boolean local;
	
	public int hasKey = 0;
	
	private final double diagFactor = 1 / Math.sqrt(2);	// for vector normalization

	
	public Player(GamePanel gp, KeyHandler keyH, boolean local, boolean p1) {
		this.gp = gp;
		this.keyH = keyH;
		this.local = local;
		
		// lock the local player sprite to the center of the screen, let the remote player move around
		if (local) {
			screenX = gp.screenWidth/2 - (gp.tileSize/2);				// player position is based on top left corner, so subtract half a tile
			screenY = gp.screenHeight/2 - (gp.tileSize/2);
		} else {
			// default, don't use these
			screenX = 0;
			screenY = 0;
		}
		
		// set the collision area of the sprite
		// gp.tileSize = 48, so twoThirds = 32 (int)
		// using math value instead of hard number for scaling
		double tileSize = gp.tileSize;
		int twoThirds = (int) Math.round(tileSize * (2.0 / 3.0));
		solidArea = new Rectangle(gp.tileSize/6, gp.tileSize/3, twoThirds, twoThirds);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage(p1);
	}
	
	
	public void setDefaultValues() {
		worldX = gp.tileSize * 25;				// start in the middle of the map
		worldY = gp.tileSize * 25;
		speed = 4;
		direction = "down";
	}
	
	
	// set the player images based on if we are player 1 or 2
	public void getPlayerImage(boolean p1) {
		
		if (p1) {
			try {
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/p1_up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/p1_up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/p1_down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/p1_down2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/p1_left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/p1_left2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/p1_right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/p1_right2.png"));
				
			}catch(IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				up1 = ImageIO.read(getClass().getResourceAsStream("/player/p2_up1.png"));
				up2 = ImageIO.read(getClass().getResourceAsStream("/player/p2_up2.png"));
				down1 = ImageIO.read(getClass().getResourceAsStream("/player/p2_down1.png"));
				down2 = ImageIO.read(getClass().getResourceAsStream("/player/p2_down2.png"));
				left1 = ImageIO.read(getClass().getResourceAsStream("/player/p2_left1.png"));
				left2 = ImageIO.read(getClass().getResourceAsStream("/player/p2_left2.png"));
				right1 = ImageIO.read(getClass().getResourceAsStream("/player/p2_right1.png"));
				right2 = ImageIO.read(getClass().getResourceAsStream("/player/p2_right2.png"));
				
			}catch(IOException e) {
				e.printStackTrace();
			}
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
		if (up || down|| left || right) {
			int xVec = 0;
			int yVec = 0;
			
			// check tile collision
			// change to check for left and right collision?
			colliding = false;
			gp.collisionH.checkTile(this, up, down, left, right);
			
			// item collision
			int itemIndex = gp.collisionH.checkItem(this, up, down, left, right, true);
			interact(itemIndex);
			
			// check tile and check item will set collision to true if it detects solid tiles
			if (colliding == false) {
				// switch statement instead? keep it like this for now in case we need to extra account for diagonal
				if (up) {
					yVec = -1;
					direction = "up";					// change these to use the first direction input? i.e. if player is going up and right, the "up" sprite will be used
														// but if player goes right and up, "right" sprite will be used
				}		
				if (down) {
					yVec = 1;
					direction = "down";
				}
				if (left) {
					xVec = -1;
					direction = "left";
				}
				if (right) {
					xVec = 1;
					direction = "right";
				}
			}
			
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
	
	
	public void interact(int index) {
		
		// interact based on the item
		// new items / interactions can be added as necessary
		if (index >= 0) {
			String itemName = gp.items[index].name;
			
			switch (itemName) {
			case "Key":
				hasKey++;
				gp.items[index] = null;
				break;
			case "Door":
				// for now, 1 key opens 1 door
				// later add color coding?
				if (hasKey > 0) {
					gp.items[index] = null;
					hasKey--;
				}
				break;
			}
		}
	}
	

	public void draw(Graphics2D g2) {
		//g2.setColor(color);
		
		BufferedImage image = null;
		switch(direction) {
		case "up":
			image = (spriteNum == 1) ? up1 : up2;
			break;
		case "down":
			image = (spriteNum == 1) ? down1 : down2;
			break;
		case "left":
			image = (spriteNum == 1) ? left1 : left2;
			break;
		case "right":
			image = (spriteNum == 1) ? right1 : right2;
			break;
		}
		
		int drawX, drawY;
		
		// local player stays in center screen, remote player uses world coordinates
		if (local) {
			drawX = screenX;
			drawY = screenY;
		} else {
			drawX = worldX - gp.localPlayer.worldX + gp.screenWidth/2 - gp.tileSize/2;
			drawY = worldY - gp.localPlayer.worldY + gp.screenHeight/2 - gp.tileSize/2;
			
		}
		// player sprite
		g2.drawImage(image, drawX, drawY, gp.tileSize, gp.tileSize, null);
		
		// draw the collision rectangle
		/*int collisionX = worldX + solidArea.x;
	    int collisionY = worldY + solidArea.y;

	    // Convert world coordinates to screen coordinates
	    int collisionScreenX = screenX + (collisionX - worldX);
	    int collisionScreenY = screenY + (collisionY - worldY);

	    g2.setColor(Color.red);
	    g2.drawRect(collisionScreenX, collisionScreenY, solidArea.width, solidArea.height);*/
	}
}
