
import layers.StopAndWaitLayer;
import packets.StopAndWaitPacket;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class client {
    public static void main(String args[]) throws Exception {
        Scanner sc = new Scanner(System.in);
        InetAddress iaddress = InetAddress.getLocalHost();
        StopAndWaitLayer ds = new StopAndWaitLayer();

        byte buffer[] = null;

        while (true) {
            String input = sc.nextLine();
            buffer = input.getBytes();
            StopAndWaitPacket DpSend = new StopAndWaitPacket(buffer, buffer.length, iaddress, 888);
            ds.sendPacket(DpSend);
            if (input.equals("end"))
                break;
        }
    }
}
