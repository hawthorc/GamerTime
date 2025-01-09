/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import entity.Player;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	
	public boolean up, down, left, right;
	
	final int orgTileSize = 16;
	final int scale = 3;	// scaling for modern resolutions
	
	public final int tileSize = orgTileSize * scale;
	
	// CHANGE THESE TO CHANGE SCREEN SIZE
	final int maxScreenCol = 16;
	final int maxScreenRow = 12;
	
	// 768 x 576 pixels
	final int screenWidth = tileSize * maxScreenCol;
	final int screenHeight = tileSize * maxScreenRow;
	
	final int FPS = 60;
	
	KeyHandler keyH = new KeyHandler();
	Thread gameThread;
	Player player = new Player(this,keyH);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);	// for rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	
	@Override
	public void run() {
		
		// setup tick variables
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long curTime;

		// update info (positions and such), then draw updated info
		while(gameThread != null) {
			
			curTime = System.nanoTime();
			delta += (curTime - lastTime) / drawInterval;
			lastTime = curTime;

			if(delta >= 1) {
				update();
				repaint();
				delta--;
			}
		}
	}
	

	public void update() {

		player.update();
	}

	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		player.draw(g2);
		g2.dispose();
	}
}
