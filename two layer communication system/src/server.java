
import layers.StopAndWaitLayer;
import packets.StopAndWaitPacket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

    public class server
    {
        public static void main(String[] args) throws IOException
        {
            StopAndWaitLayer ds = new StopAndWaitLayer(888);
            byte[] receive = new byte[65535];
            StopAndWaitPacket DpReceive = null;
            while (true)
            {
                DpReceive = new StopAndWaitPacket(receive, receive.length);
                ds.receivePacket(DpReceive);
                System.out.println("Client:-" + data(receive));
                if (data(receive).toString().equals("end"))
                {
                    System.out.println("Client sent end.....EXITING");
                    break;
                }
                receive = new byte[65535];
            }
        }


        public static StringBuilder data(byte[] a)
        {
            if (a == null)
                return null;
            StringBuilder ret = new StringBuilder();
            int i = 0;
            while (a[i] != 0)
            {
                ret.append((char) a[i]);
                i++;
            }
            return ret;
        }
    }

