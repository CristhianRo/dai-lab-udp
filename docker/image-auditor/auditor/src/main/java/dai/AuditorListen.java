package dai;

import java.io.IOException;
import java.net.MulticastSocket;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.DatagramPacket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.google.gson.Gson;

import static java.nio.charset.StandardCharsets.*;
public class AuditorListen implements Runnable {
    final static String IPADDRESS_UDP = "239.255.22.5";
    final static int PORT_UDP = 9904;
    InetSocketAddress group_address = new InetSocketAddress(IPADDRESS_UDP, PORT_UDP);
    NetworkInterface netif;
    public static ConcurrentHashMap<String, Musician> activeMusicians = new ConcurrentHashMap<>();


    @Override
    public void run() {
        System.out.println("Auditor is listening!");

        try (MulticastSocket socket = new MulticastSocket(PORT_UDP)) {
            socket.joinGroup(group_address, netif);
            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String json = new String(packet.getData(), 0, packet.getLength(), UTF_8);
                System.out.println("Received message: " + json + " from " + packet.getAddress() + ", port " + packet.getPort());

                Gson gson = new Gson();
                Musician musician = gson.fromJson(json, Musician.class);

                if(!activeMusicians.contains(musician)) { // if musician is not in the list of active musicians
                    musician.lastActivity = System.currentTimeMillis();
                    musician.assigneIntrument();
                    activeMusicians.put(musician.uuid , musician);

                }else{
                    activeMusicians.get(musician.uuid).lastActivity = System.currentTimeMillis();
                }

            }
            //socket.leaveGroup(group_address, netif);
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }


    }

    public static void removeInactiveMusicians() {
        for (Map.Entry<String, Musician> musician : activeMusicians.entrySet()) {
            if (System.currentTimeMillis() - musician.getValue().lastActivity > 5000) {
                System.out.println("Inactive musician removed " + musician.getValue().instrument + " is inactive since 5 seconds");
                activeMusicians.remove(musician.getKey());
            }
        }
    }
}

