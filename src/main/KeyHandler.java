/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	GamePanel gp;
	public boolean up, down, left, right;
	

	public KeyHandler(GamePanel gp) {
		this.gp = gp;
	}

	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	
	@Override
	public void keyPressed(KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (gp.gameState == gp.mainMenu) {
			
			// could potentially use the same commandNum, depends on the amount of options
			if (gp.ui.mainMenuState == 0) {
				switch (key) {
				
				case KeyEvent.VK_W:
				case KeyEvent.VK_UP:
					if (gp.ui.commandNum > 0) gp.ui.commandNum--;
					break;
					
				case KeyEvent.VK_S:
				case KeyEvent.VK_DOWN:
					if (gp.ui.commandNum < 2) gp.ui.commandNum++;
					break;
					
				case KeyEvent.VK_ENTER:
					if (gp.ui.commandNum == 0) {
						gp.gameState = gp.play;
						// load music, potential other tasks
					}
					else if (gp.ui.commandNum == 1) gp.ui.mainMenuState = 1;
					else if (gp.ui.commandNum == 2) gp.ui.mainMenuState = 2;
				}
			} else if (gp.ui.mainMenuState == 1) {
				
			} else if (gp.ui.mainMenuState == 2) {
				
			}

		} else if (gp.gameState == gp.play) {
			switch (key) {
			
			// allow both WASD and arrow keys (FOR NOW)
			case KeyEvent.VK_W:
			case KeyEvent.VK_UP:
				up = true;
				break;
				
			case KeyEvent.VK_S:
			case KeyEvent.VK_DOWN:
				down = true;
				break;
				
			case KeyEvent.VK_A:
			case KeyEvent.VK_LEFT:
				left = true;
				break;
				
			case KeyEvent.VK_D:
			case KeyEvent.VK_RIGHT:
				right = true;
				break;
				
			case KeyEvent.VK_P:
				if (gp.gameState == gp.play) gp.gameState = gp.paused;
				else if (gp.gameState == gp.paused) gp.gameState = gp.play;
				break;
			}
		}
		

		/*if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) up = true;
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) down = true;
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) left = true;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) right = true;*/
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int key = e.getKeyCode();
		switch (key) {
		
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			up = false;
			break;
			
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			down = false;
			break;
			
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			left = false;
			break;
			
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		}
			
		/*if(key == KeyEvent.VK_W || key == KeyEvent.VK_UP) up = false;
		if(key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) down = false;
		if(key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) left = false;
		if(key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) right = false;*/
	}
}
