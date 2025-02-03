package network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Server extends Thread{
    
    private DatagramSocket socket;
    private NetworkHandler networkHandler;
    private boolean running;
    private byte[] buf = new byte[256];

    public Server(NetworkHandler networkHandler) throws SocketException {
        this.networkHandler = networkHandler;
        socket = new DatagramSocket(4445);
    }


    public void run() {
        running = true;

        while (running) {
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            String received = new String(packet.getData(), 0, packet.getLength());

            networkHandler.handleReceivedMessage(received);
            
           /*if (received.equals("end")) {
                running = false;
                continue;
            }
			try {
				socket.send(packet);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        }
        socket.close();
    }
    

    public void stopServer() {
        running = false;
    }
}
