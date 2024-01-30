package dai;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.DatagramPacket;
import java.util.concurrent.ConcurrentHashMap;

import static java.nio.charset.StandardCharsets.*;
public class AuditorListen {
    final static String IPADDRESS_UDP = "239.255.22.5";
    final static int PORT_UDP = 9904;
    final static int PORT_TCP = 2205;
    private long timeStamp;
    private final ConcurrentHashMap<String, Long> musicians = new ConcurrentHashMap<>();

    public AuditorListen(long TimeStamp){
        this.timeStamp = TimeStamp;
    }

    public static void main(String[] args) {

        while(true) {
            try (MulticastSocket socket = new MulticastSocket(PORT_UDP)) {
                InetSocketAddress group_address =  new InetSocketAddress(IPADDRESS_UDP, PORT_UDP);
                NetworkInterface netif = NetworkInterface.getByName("eth0");
                socket.joinGroup(group_address, netif);

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength(), UTF_8);

                System.out.println("Received message: " + message + " from " + packet.getAddress() + ", port " + packet.getPort());
                socket.leaveGroup(group_address, netif);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}

