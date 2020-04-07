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
    
    public StopAndWaitLayer(int port, InetAddress laddr) throws SocketException {
        this.socket = new UnreliableCommunicationLayer(port, laddr);
        
    }
    
    public void close() {
        this.socket.close();
    }

    public boolean isClosed() {
        return this.socket.isClosed();
    }

    public void send(StopAndWaitPacket swp) throws Exception {
        if (this.sequenceNumber < 0)
            throw new Exception("The sequence number is not defined");
        this.socket.setSoTimeout(timeInMillis);
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        DatagramPacket packet = swp.getPacket();
        this.addSequenceNumber(this.sequenceNumber, packet);
        boolean sendingPacket = true;
        while (sendingPacket) {
            socket.send(packet);
            try {
                socket.receive(receivePacket);
                String receivedPacket = new String(receivePacket.getData(), 0, receivePacket.getLength()-1);
                System.out.println(receivedPacket);
                this.sequenceNumber = this.sequenceNumber % 2 == 0 ? 1 : 0;
                sendingPacket = false;
            } catch (SocketTimeoutException e) {
                System.out.println("Re-transmit packet. Time limit was reached!");
            }
        }
        this.socket.setSoTimeout(0);
    }

    public void receive(StopAndWaitPacket swp) throws IOException {
        socket.receive(swp.getPacket());
        socket.send(swp.getPacket());
    }


    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    private void addSequenceNumber(int sequenceNumber, DatagramPacket packet) {
    	byte[] data = packet.getData();
    	int length = packet.getLength();
        byte[] newData = new byte[length + 1];
        for (int i=0; i < length; i++)
            newData[i] = data[i];
        newData[length] = (byte) sequenceNumber;
        packet.setData(newData);
        packet.setLength(length + 1);
    }
}