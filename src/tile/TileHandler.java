/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileHandler {
	
	GamePanel gp;
	Tile[] tile;
	int mapTileNum[][];
	
	
	public TileHandler(GamePanel gp) {
		
		this.gp = gp;
		tile = new Tile[10];										// num of different tile types, 10 is a placeholder for now
		mapTileNum = new int[gp.maxWorldRow][gp.maxWorldCol];
		getTileImage();
		//loadMap("/maps/map01.txt");
		loadMap("/maps/test_world.txt");								// ready the mapTileNum array so that the map can be drawn
	}
	
	
	public void getTileImage() {
		
		try {
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/stone.png"));
			
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public void loadMap(String mapFile) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(mapFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int row = 0;
			int col = 0;
			
			while (row < gp.maxWorldRow && col < gp.maxWorldCol) {
				
				String line = br.readLine();
				while (col < gp.maxWorldCol) {
					String nums[] = line.split(" ");							// numbers in the map text file are separated by spaces
					int num = Integer.parseInt(nums[col]);
					
					mapTileNum[row][col] = num;
					col++;
				}
				// end of the line, reset to next one
				if (col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void draw(Graphics2D g2) {
		
		int worldRow = 0;
		int worldCol = 0;
		
		while (worldRow < gp.maxWorldRow && worldCol < gp.maxWorldCol) {
			
			int tileNum = mapTileNum[worldRow][worldCol];
			
			// get the position on the world map
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			// find where to draw each tile on the screen (map moves while player stays in center)
			// use the player's central position to offset the tile drawing
			int screenX = worldX - gp.player1.worldX + gp.player1.screenX;
			int screenY = worldY - gp.player1.worldY + gp.player1.screenY;
			
			
			// only draw tiles we can see
			if (worldX + gp.tileSize > gp.player1.worldX - gp.player1.screenX &&
				worldX - gp.tileSize < gp.player1.worldX + gp.player1.screenX &&
				worldY + gp.tileSize > gp.player1.worldY - gp.player1.screenY &&
				worldY - gp.tileSize < gp.player1.worldY + gp.player1.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			}
			worldCol++;
			
			if (worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
				
		}
	}
}
