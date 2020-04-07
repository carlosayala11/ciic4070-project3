package layers;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import packets.StopAndWaitPacket;

public class StopAndWaitLayer {

    private UnreliableCommunicationLayer socket;
    private int timeInMillis = 1000;
    private byte[] buffer = new byte[256];
    private int sequenceNumber = -1;
    
    public StopAndWaitLayer(int port) throws SocketException {
        this.socket = new UnreliableCommunicationLayer(port);
    }
    public StopAndWaitLayer() throws SocketException {
        this.socket = new UnreliableCommunicationLayer();

    }

    public void sendPacket(StopAndWaitPacket swp) throws IOException {
        this.sequenceNumber = swp.getLength();
        if (this.sequenceNumber < 0)
            System.out.println("The sequence number is not defined.");
        this.socket.setSoTimeout(timeInMillis);
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        DatagramPacket packet = swp.getPacket();
        boolean sendingPacket = true;
        while (sendingPacket) {
            socket.sendPacket(packet);
            try {
                socket.receive(receivePacket);
                this.sequenceNumber = this.sequenceNumber % 2 == 0 ? 1 : 0;
                sendingPacket = false;
            } catch (SocketTimeoutException e) {
                System.out.println("Re-transmit packet. Time limit was reached!");
            }
        }
        this.socket.setSoTimeout(0);
    }

    public void receivePacket(StopAndWaitPacket swp) throws IOException {
        socket.receivePacket(swp.getPacket());
        socket.sendPacket(swp.getPacket());
    }

}