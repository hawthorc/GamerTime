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
    private NetworkHandler networkHandler;

    private byte[] buf;

    public Client(String address, NetworkHandler networkHandler) throws SocketException, UnknownHostException {
        this.networkHandler = networkHandler;
    	this.address = InetAddress.getByName(address);
        socket = new DatagramSocket();
    }


    public void sendEcho(String msg) throws IOException {
        buf = msg.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 4445);
        socket.send(packet);
        packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        
        String received = new String(packet.getData(), 0, packet.getLength());
        networkHandler.handleReceivedMessage(received);
    }

    public void close() {
        socket.close();
    }
}
