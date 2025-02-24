/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UI {

	GamePanel gp;
	Graphics2D g2;
	// change the font, this works for now
	Font tempScript;
	public int commandNum = 0;
	public int mainMenuState = 0;	// 1 = start server, 2 = join
	
	public UI(GamePanel gp) {
		this.gp = gp;
		// random font from my system
		// size doesn't work?
		tempScript = new Font("Z003", Font.BOLD, 40);
	}

	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(tempScript);
		g2.setColor(Color.white);
		
		// could be switch statement?
		// main menu
		if (gp.gameState == gp.mainMenu) {
			drawMainMenu();
		}
		// gameplay
		if (gp.gameState == gp.play) {
			// play the game
		// pause screen
		} else if (gp.gameState == gp.paused) {
			drawPause();
		}
	}
	// add fun stuff to this (spring quarter likely)
	public void drawMainMenu() {

		if (mainMenuState == 0) {
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 80f));
			String text = "Gamer Time (not final)";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			// actual menu stuff
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 40f));
			
			text = "Start Game";
			x = getXforCenteredText(text);
			y += gp.tileSize*4;
			g2.drawString(text, x, y);
			if (commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Start Server";
			y += gp.tileSize;
			g2.drawString(text, getXforCenteredText(text), y);
			if (commandNum == 1) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			text = "Join Server";
			y += gp.tileSize;
			g2.drawString(text, getXforCenteredText(text), y);
			if (commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
		} /*else if (mainMenuState == 1) {
			// SERVER STUFF
		} else if (mainMenuState == 2) {
			// CLIENT STUFF
		}*/
	}
	public void drawPause() {
		String message = "PAUSED";
		int x = getXforCenteredText(message);
		int y = gp.screenHeight / 2;
		g2.drawString(message, x, y);
	}
	public int getXforCenteredText(String text) {
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,60));
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
	}
}
