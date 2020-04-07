package layers;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.SocketException;
import java.net.InetAddress;
import java.util.Random;

public class UnreliableCommunicationLayer extends DatagramSocket {
    
    public UnreliableCommunicationLayer(int port, InetAddress iaddress) throws SocketException {
        super(port, iaddress);
    }


    public void send(DatagramPacket packet) throws IOException {
        int lostPacket = new Random().nextInt(5); 
        int duplicatePacket = new Random().nextInt(10);
        if (lostPacket == 2)
            System.out.println("Packet was lost");
        else { 
            if(duplicatePacket == 1) { 
                System.out.println("Packet was duplicated"); 
                this.send(packet);
                this.send(packet);
            }else{
                this.send(packet);
            }
        } 
        return;
    }

    public void receivePacket(DatagramPacket packet) throws IOException {
        this.receive(packet);
        return;
    }
}
