package dai;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.UUID;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import static java.nio.charset.StandardCharsets.UTF_8;

public class Musician {
    final static String IPADDRESS = "239.255.22.5";
    final static int PORT = 9904;
    @Expose
    final private String uuid; // Expose this field to Gson
    @Expose
    final private String sound; // Expose this field to Gson
    private final Instrument instrument;

    public Musician(Instrument inst) {
        this.uuid = UUID.randomUUID().toString();
        this.instrument = inst;
        this.sound = this.instrument.sound();
    }

    public static void main(String[] args) throws InterruptedException {

        if (args.length == 0) {
            System.err.println("Error: you need to give the instrument name as the first argument");
            System.exit(1);
        }

        Instrument instrument = null;

        try {
            instrument = switch (args[0]) {
                case "piano" -> Instrument.piano;
                case "trumpet" -> Instrument.trumpet;
                case "flute" -> Instrument.flute;
                case "violin" -> Instrument.violin;
                case "drum" -> Instrument.drum;
                default -> throw new IllegalArgumentException("Unknown instrument " + args[0]);
            };
        } catch (IllegalArgumentException e) {
            System.err.println("Error: you need to give a known instrument name as the first argument like piano, trumpet, flute, violin or drum");
            System.exit(1);
        }

        Musician musician = new Musician(instrument);

        // Only serialize fields with @Expose annotation
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        while (true) {
            try (DatagramSocket socket = new DatagramSocket()) {
                String json = gson.toJson(musician); // Sérialiser l'objet en JSON
                byte[] payload = json.getBytes(UTF_8);
                InetSocketAddress dest_address = new InetSocketAddress(IPADDRESS, PORT);
                var packet = new DatagramPacket(payload, payload.length, dest_address);
                socket.send(packet);

                System.out.println("Sent " + json); // debug !!!
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

            // Envoi toutes les secondes (comme demandé)
            try {
                // Pause d'une seconde
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}

