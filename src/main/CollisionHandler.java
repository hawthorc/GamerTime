/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package main;

import entity.Entity;

public class CollisionHandler {
	
	GamePanel gp;

	
	public CollisionHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	
	/* NOTE: in the following functions, the directional booleans are for movement / input, NOT the entity.direction string
	 * this string is for sprite displaying (only 1 at a time)
	 * the game allows for diagonal movement, which means entities can be approaching tiles / items from two directions at the same time
	 */
	
	public void checkTile(Entity entity, boolean up, boolean down, boolean left, boolean right) {
		// outside coordinates of entity collision area
		// remember that coordinates start in top left and grow down / right
		int solidWorldLeft = entity.worldX + entity.solidArea.x;
		int solidWorldRight = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int solidWorldTop = entity.worldY + entity.solidArea.y;
		int solidWorldBottom = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int solidLeftCol = solidWorldLeft/gp.tileSize;
		int solidRightCol = solidWorldRight/gp.tileSize;
		int solidTopRow = solidWorldTop/gp.tileSize;
		int solidBottomRow = solidWorldBottom/gp.tileSize;
		
		// check two tiles depending on direction of movement
		int tile1, tile2;
		
		// maybe don't make this a switch statement? since we are allowing diagonal movement, potentially need to check two directions at once
		if (up) {
			solidTopRow = (solidWorldTop - entity.speed)/gp.tileSize;
			tile1 = gp.tileH.mapTileNum[solidLeftCol][solidTopRow];
			tile2 = gp.tileH.mapTileNum[solidRightCol][solidTopRow];
			if (gp.tileH.tile[tile1].collision == true || gp.tileH.tile[tile2].collision == true) entity.colliding = true;
		} else if (down) {
			solidBottomRow = (solidWorldBottom + entity.speed)/gp.tileSize;
			tile1 = gp.tileH.mapTileNum[solidLeftCol][solidBottomRow];
			tile2 = gp.tileH.mapTileNum[solidRightCol][solidBottomRow];
			if (gp.tileH.tile[tile1].collision == true || gp.tileH.tile[tile2].collision == true) entity.colliding = true;
		}
		if (left) {
			solidLeftCol = (solidWorldLeft - entity.speed)/gp.tileSize;
			tile1 = gp.tileH.mapTileNum[solidLeftCol][solidTopRow];
			tile2 = gp.tileH.mapTileNum[solidLeftCol][solidBottomRow];
			if (gp.tileH.tile[tile1].collision == true || gp.tileH.tile[tile2].collision == true) entity.colliding = true;
		} else if (right) {
			solidRightCol = (solidWorldRight + entity.speed)/gp.tileSize;
			tile1 = gp.tileH.mapTileNum[solidRightCol][solidTopRow];
			tile2 = gp.tileH.mapTileNum[solidRightCol][solidBottomRow];
			if (gp.tileH.tile[tile1].collision == true || gp.tileH.tile[tile2].collision == true) entity.colliding = true;
		}
	}
	
	
	// instead of void, this function returns the index of the item being interacted with, along with changing the entity.colliding value if applicable
	// also utilizes Rectangle.intersects, as we are checking a limited amount of items
	// this would be computationally heavy for the tile system, but it is convenient here
	public int checkItem(Entity entity, boolean up, boolean down, boolean left, boolean right, boolean player) {
		
		int index = -1;
		
		for (int i = 0; i < gp.items.length; i++) {
			
			if (gp.items[i] != null) {
				
				// get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				// item's solid area position
				// second part (after +) doesn't do anything currently, as item solidArea x and y is set to 0
				// but leaving it in case of future items with specific hit boxes or movement
				gp.items[i].solidArea.x = gp.items[i].worldX + gp.items[i].solidArea.x;
				gp.items[i].solidArea.y = gp.items[i].worldY + gp.items[i].solidArea.y;
				
				if (up) {
					entity.solidArea.y -= entity.speed;
					if (entity.solidArea.intersects(gp.items[i].solidArea)) {
						if (gp.items[i].collision = true) entity.colliding = true;
						if (player == true) index = i;
					}
				}
				else if (down) {
					entity.solidArea.y += entity.speed;
					if (entity.solidArea.intersects(gp.items[i].solidArea)) {
						if (gp.items[i].collision = true) entity.colliding = true;
						if (player == true) index = i;
					}
				}
				if (left) {
					entity.solidArea.x -= entity.speed;
					if (entity.solidArea.intersects(gp.items[i].solidArea)) {
						if (gp.items[i].collision = true) entity.colliding = true;
						if (player == true) index = i;
					}
				}
				else if (right) {
					entity.solidArea.x += entity.speed;
					if (entity.solidArea.intersects(gp.items[i].solidArea)) {
						if (gp.items[i].collision = true) entity.colliding = true;
						if (player == true) index = i;
					}
				}
				
				// reset positions
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.items[i].solidArea.x = gp.items[i].solidAreaDefaultX;
				gp.items[i].solidArea.y = gp.items[i].solidAreaDefaultY;
				
			}
		}
		
		return index;
	}
}
