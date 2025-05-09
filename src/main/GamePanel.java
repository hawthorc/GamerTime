/* Basic game setup adapted from RyiSnow on YouTube:
 * https://www.youtube.com/playlist?list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import entity.Player;
import entity.NPC;
import network.InputPacket;
import network.NetworkHandler;
import tile.TileHandler;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable {
	
	public int port1 = 4445;
	public int port2 = 4446;

	//public boolean up, down, left, right;
	
	final int orgTileSize = 16;
	final int scale = 3;	// scaling for modern resolutions
	
	public final int tileSize = orgTileSize * scale;
	
	// CHANGE THESE TO CHANGE SCREEN SIZE
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	
	// 768 x 576 pixels
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// WORLD SETTINGS--------------------------------------------------------
	public final int maxWorldRow = 50;
	public final int maxWorldCol = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	final int FPS = 60;
	
	
	TileHandler tileH = new TileHandler(this);
	KeyHandler keyH = new KeyHandler(this);
	public UI ui = new UI(this);
	NetworkHandler network = new NetworkHandler(this);
	Thread gameThread;
	
	
	// entity + object ------------------------------------------------------
	public Player player1;
	Player player2;
	
	// Game state -----------------------------------------------------------
	// could be enum?
	public int gameState;
	public final int mainMenu = 0;
	public final int play = 1;
	public final int paused = 2;
	

	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);	// for rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}

	
	public void setup() {
		gameState = mainMenu;
	}
	
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	
	// start a non-network / multiplayer game
	// just for testing?
	// player1's color, etc could be changed for player 2
	// perhaps having a separate function will be good as features are added
	public void startSolo() {
		// set up player 1 for gameplay -- potentially just initialize player1 and do away with this function?
		player1 = new Player(this, keyH, Color.BLUE);
		gameState = play;
	}

	
	// start a server for the other player to join
	public void startServer(int port) {
	    String startMessage = "Server started on " + network.startServer(port);
	    if (port == port1) {
	    	JOptionPane.showMessageDialog(null, startMessage);
	    	// coordinate player colors based on who started the server first
		    if (port == port1) player1 = new Player(this, keyH, Color.BLUE);
	    }
	    //else player1 = new Player(this, keyH, Color.GREEN);
	    //gameState = play; // Change game state to start playing
	}

	
	// join the other player's server
	public void joinServer(String address, int port) {
	    if (network.startClient(address, port).equals("Client connected")) {
	    	if (port == port1) {
	    		// second player joining the first
	    		player1 = new Player(this, keyH, Color.GREEN);
	    		player2 = new Player(this, null, Color.BLUE);
	    		// special first contact packet to let the server know it should join p2 client
	    		network.sendUpdate(null, true);
	    	} else {
	    		// first player joining the second
	    		player2 = new Player(this, null, Color.GREEN);
	    	}
	    	gameState = play;
	    }
	    else {
	    	System.out.println("Something went wrong, didn't connect to server?");
	    }
	    //gameState = play; // Change game state to start playing
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
	

	// update the player position
	public void update() {
		if (gameState == play) {
			player1.update();
			// only send update if there is another player connected
			if (player2 != null) {
				InputPacket input = new InputPacket(keyH.up, keyH.down, keyH.left, keyH.right);
			    network.sendUpdate(input, false);
			}
			
		} else if (gameState == paused) {
			// no update
		}
	}
	

	// update the remote player's position
	public void updateRemotePlayer(boolean up, boolean down, boolean left, boolean right) {
		// TODO: add gameState checks?
		if (player2 != null) {
			player2.handleRemoteInput(up, down, left, right);
		}
	}

	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g;
		
		if (gameState == mainMenu) {
			ui.draw(g2);
		} else {
			// tile + object stuff goes here
			tileH.draw(g2);
			
			player1.draw(g2);
			// potentially change this so that the game can't be played solo?
			if (player2 != null) player2.draw(g2);

			
			// add stuff to HUD!!
			ui.draw(g2);
		}
		
		g2.dispose();
	}
}
