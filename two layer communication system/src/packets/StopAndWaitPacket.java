package packets;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;

public class StopAndWaitPacket {

    private int sequence;
    private DatagramPacket packet;

    public StopAndWaitPacket(byte[] buffer, int length) {
        this.packet = new DatagramPacket(buffer, length);
    }

    public StopAndWaitPacket(byte[] buffer, int length, InetAddress address, int port) {
        this.packet = new DatagramPacket(buffer, length, address, port);
    }

    public byte getSequenceNumber() {
        return this.packet.getData()[packet.getLength()-1];
    }

    public DatagramPacket getPacket() {
        return this.packet;
    }
    

    public byte[] getData() {
        return this.packet.getData();
    }

    public int getLength() {
        return this.packet.getLength() - 1;
    }

}