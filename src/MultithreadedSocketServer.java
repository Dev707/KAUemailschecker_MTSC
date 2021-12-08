
import java.io.IOException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MultithreadedSocketServer {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("[dd/MM/yyyy HH:mm:ss]");
        try {
            // server socket with port 8888
            int port = 8888;
            ServerSocket server = new ServerSocket(port);

            int userID = 0;
            System.out.println("KAU student email cheacker app started ...");
            System.out.println("Server Log:");
            while (true) {
                userID++;
                Socket serverClient = server.accept();
                ServerClientThread sct = new ServerClientThread(serverClient, userID);
                sct.start();
                System.out.println(formatter.format(new Date())
                        + " User No: " + userID + " Established Connection! on port NO. " + serverClient.getPort());
            }
        } catch (IOException e) {
            System.out.println(formatter.format(new Date()) + " " + e.getMessage());
        }
    }
}
