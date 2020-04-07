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

    public DatagramPacket getPacket() {
        return this.packet;
    }
    public int getLength() {
        return this.packet.getLength() - 1;
    }

}