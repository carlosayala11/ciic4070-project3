package layers;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.io.IOException;
import java.net.SocketException;
import java.util.Random;

public class UnreliableCommunicationLayer extends DatagramSocket {
    public boolean isDuplicated = false;

    public UnreliableCommunicationLayer(int port) throws SocketException {
        super(port);
    }
    public UnreliableCommunicationLayer() throws SocketException {
        super();
    }



    public void sendPacket(DatagramPacket packet) throws IOException {
        int lostPacket = new Random().nextInt(5);
        int duplicatePacket = new Random().nextInt(10);
        if (lostPacket == 2)
            System.out.println("Packet was lost.");
        else {
            if(duplicatePacket == 1) {

                System.out.println("Packet was duplicated.");
                this.send(packet);
                this.send(packet);
                setDuplicatedTrue();
            }else{
                this.send(packet);
                setDuplicatedFalse();
            }
        }
    }
    public boolean getDuplicated(){
        return this.isDuplicated;
    }

    public void setDuplicatedTrue() {
        this.isDuplicated = true;
    }

    public void setDuplicatedFalse() {
        this.isDuplicated = false;
    }

    public void receivePacket(DatagramPacket packet) throws IOException {
        this.receive(packet);
    }
}
