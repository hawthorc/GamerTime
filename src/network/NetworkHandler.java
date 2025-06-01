package network;

import java.io.IOException;
import java.net.InetAddress;

import main.GamePanel;


public class NetworkHandler {
	
	private GamePanel gp;
	private Server server;
	private Client client;
	
	public InetAddress p2Address = null;


	public NetworkHandler(GamePanel gp) {
		this.gp = gp;
	}
	

	public String startServer(int port) {
        try {
            server = new Server(this, port); // Pass reference for message handling
            server.start();
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to start server.";
        }
    }
	

    public String startClient(String address, int port) {
        try {
            client = new Client(address, port);
            System.out.println("Client connected to server at: " + address + ", port " + port);
            return "Client connected";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to connect client.";
        }
    }

    // for now, data should just be position (x,y)
    // TODO: add a packet class for structuring data
    public void sendUpdate(InputPacket input, boolean first) {
        if (client != null) {
        	if (!first) {
	            try {
	            	byte[] buf = new byte[5];
	                buf[0] = (byte) (input.up ? 1 : 0);
	                buf[1] = (byte) (input.down ? 1 : 0);
	                buf[2] = (byte) (input.left ? 1 : 0);
	                buf[3] = (byte) (input.right ? 1 : 0);
	                buf[4] = (byte) (input.pause ? 1 : 0); 
	                client.sendUpdate(buf);
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
        	} else {
        		// first contact, let the server know that it should join
        		try {
        			byte[] buf = new byte[1];
	                buf[0] = (byte) 2;
	        		client.sendUpdate(buf);
        		} catch (IOException e) {
	                e.printStackTrace();
	            }
        	}
        } else {
            System.out.println("Client is not connected.");
        }
    }

    // TODO: modify to receive Packet class packets
    public void handleReceivedMessage(byte[] buf) {
    	// client connected, connect to their server
    	if (buf[0] == 2) {
            // join player 2's server to complete the 2-way connection
    		gp.joinServer(p2Address.getHostAddress(), gp.port2);
    	}
    	else {
    		// retrieve the input information
        	boolean up = buf[0] == 1;
            boolean down = buf[1] == 1;
            boolean left = buf[2] == 1;
            boolean right = buf[3] == 1;
            boolean pause = buf[4] == 1;
            
            System.out.println(up + ", " + down + ", " + left + ", " + right + ", " + pause);
            
            if (pause && gp.gameState == gp.play) gp.gameState = gp.pause;
            else if (pause && gp.gameState == gp.pause) gp.gameState = gp.play;
            else gp.updateRemotePlayer(up, down, left, right); 
    	}    
    }

    public void closeClient() {
        // close client handling
    	client.close();
    }

    public void stopServer() {
        // stop server handling
    	server.stopServer();
    }
} 
