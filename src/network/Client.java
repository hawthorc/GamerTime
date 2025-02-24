package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Client {
	private DatagramSocket socket;
    private InetAddress address;
    private int port;
    //private NetworkHandler networkHandler;

    public Client(String address, int port) throws SocketException, UnknownHostException {
        //this.networkHandler = networkHandler;
    	this.address = InetAddress.getByName(address);
    	this.port = port;
        socket = new DatagramSocket();
    }


    public void sendUpdate(byte[] buf) throws IOException {
    	// create a byte array to send the integers
    	// TODO: create packet class that handles data in this way
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
        /*packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        
        String received = new String(packet.getData(), 0, packet.getLength());
        networkHandler.handleReceivedMessage(received);*/
    }

    public void close() {
        socket.close();
    }
}
