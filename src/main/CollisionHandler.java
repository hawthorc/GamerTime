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
}
