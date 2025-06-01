package main;

import item.Door;
import item.Key;

public class ItemHandler {
	
	GamePanel gp;
	
	
	public ItemHandler(GamePanel gp) {
		this.gp = gp;
	}
	
	
	public void setItems() {
		
		gp.items[0] = new Key();
		gp.items[0].worldX = 20 * gp.tileSize;
		gp.items[0].worldY = 16 * gp.tileSize;	
		
		gp.items[1] = new Door();
		gp.items[1].worldX = 12 * gp.tileSize;
		gp.items[1].worldY = 11 * gp.tileSize;	
		
	}

}
