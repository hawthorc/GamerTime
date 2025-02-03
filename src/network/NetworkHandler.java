package network;

import java.io.IOException;
import java.net.InetAddress;

import main.GamePanel;


public class NetworkHandler {
	
	private GamePanel gp;
	private Server server;
	private Client client;

	

	public NetworkHandler(GamePanel gp) {
		this.gp = gp;
	}
	

	public String startServer() {
        try {
            server = new Server(this); // Pass reference for message handling
            server.start();
            return InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to start server.";
        }
    }
	

    public String startClient(String address) {
        try {
            client = new Client(address, this);
            return "Client connected to server at: " + address;
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to connect client.";
        }
    }

    
    public void sendMessage(String msg) {
        if (client != null) {
            try {
                client.sendEcho(msg); // Send message and wait for echo.
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Client is not connected.");
        }
    }

    
    public void handleReceivedMessage(String msg) {
        // Handle messages received from either server or client
        // This could involve updating game states, triggering UI updates, etc.
        //gp.updateGameWithReceivedMessage(msg);
    }

    public void closeClient() {
        // close client handling
    }

    public void stopServer() {
        // stop server handling
    }
}
