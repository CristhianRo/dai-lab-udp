package dai;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SenderInfo implements Runnable {
    final static int PORT_TCP = 2205;
    @Override
    public void run() {
        System.out.println("Send musician activities!");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

        try(ServerSocket serverSocket = new ServerSocket(PORT_TCP)) {
            while (true){
                try(Socket socket = serverSocket.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                    BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                    String message = "";
                    AuditorListen.removeInactiveMusicians();

                    String json = gson.toJson((new HashMap<>(AuditorListen.activeMusicians)).values());
                    System.out.println("Send message: " + json);
                    out.write(json + "\n");
                    out.flush();

                    }catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
            }
        }catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
